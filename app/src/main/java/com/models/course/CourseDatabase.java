package com.models.course;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

final public class CourseDatabase implements CourseDatabaseInterface {
    private final DatabaseReference ref = FirebaseDatabase.getInstance("https://b07-project-e5893-default-rtdb.firebaseio.com/").getReference();
    private static CourseDatabase courseDB;
    public ArrayList<Course> courses;

    private CourseDatabase() {
        courses = new ArrayList<>();

        ref.child("courses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // if the courses object in the database changes in any way, clear and refill
                // the local storage
                courses.clear();
                snapshot.getChildren()
                        .forEach(s -> {
                            // parsing the name, code, sessionalDates, prerequisites
                            String name = s.child("name").getValue(String.class);
                            String code = s.child("code").getValue(String.class);
                            ArrayList<String> sessionalDates
                                    = (ArrayList<String>) s.child("sessionalDates").getValue();
                            ArrayList<Course> prerequisites
                                    = (ArrayList<Course>) s.child("prerequisites").getValue();
                            Course course = new Course(name, code, sessionalDates, prerequisites);
                            courses.add(course);
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("CourseDatabase", "Error updating the local arraylist");
            }
        });
    }

    public static CourseDatabase getInstance() {
        if(courseDB == null)
            courseDB = new CourseDatabase();
        return courseDB;
    }

    @Override
    public void addCourse(Course course) {
        /*
        addCourse() adds a course with the given properties to the database with a unique key

        the unique key is read from the database's "uniqueVal" value that is then incremented to
        ensure that it will always have a different value each time it is read
         */

        ref.child("uniqueVal").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("CourseDatabase", "Error reading uniqueVal", task.getException());
            } else {
                // if we get here, then there has been no error reading "uniqueVal" (ie the task
                // was successful)
                // first we read the "uniqueVal" value in the database
                int un = task.getResult().getValue(Integer.class);

                // increment the uniqueVal value in the database so that it will be unique the
                // next time we call it
                ref.child("uniqueVal").setValue(un + 1);

                // now we can add the course with our new unique key
                ref.child("courses").child(String.valueOf(un)).setValue(course);
                /*
                note: the above line MUST be inside this else{} statement so that we can add
                the course to the database AFTER we read the uniqueVal value into un.

                i.e., if the line is outside of the onComplete() method, the course will likely
                be added BEFORE the task is completed and before it is assigned a value,
                resulting in unwanted behaviour. this is because the OnCompleteListener runs
                asynchronously to the rest of the code (the rest of the addCourse() method does
                not wait for the task to finish before running)
                 */
            }
        });
    }

    @Override
    public void editCourse(Course course, String code) {
        Query courseQuery = ref.child("courses").orderByChild("code").equalTo(code);

        courseQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildren()
                        .forEach(s -> {
                            String name = course.getName();
                            String code = course.getCode();
                            ArrayList<String> sessionalDates
                                    = (ArrayList<String>) course.getSessionalDates();
                            ArrayList<Course> prerequisites
                                    = (ArrayList<Course>) course.getPrerequisites();
                            s.getRef().setValue(new Course(name, code, sessionalDates, prerequisites));
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("CourseDatabase", "Error editing " + code);
            }
        });
    }

    @Override
    public Course getCourse(String code) {
        for(int index=0; index<courses.size(); index++) {
            // we return the first instance of the course in courses
            // although there should only be one course with that code so its all good hopefully :)
            if(courses.get(index).getCode().equals(code))
                return courses.get(index);
        }
        return null;
    }

    @Override
    public void removeCourse(String code) {
        Query courseQuery = ref.child("courses").orderByChild("code").equalTo(code);

        courseQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildren().forEach(s -> s.getRef().removeValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("CourseDatabase", "Error deleting " + code);
            }
        });
    }
}
