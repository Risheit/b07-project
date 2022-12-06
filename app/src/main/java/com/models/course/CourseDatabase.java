package com.models.course;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

final public class CourseDatabase implements CourseDatabaseInterface {
    private final DatabaseReference ref;
    private static CourseDatabase courseDB;
    private final List<Course> courses;

    private CourseDatabase() {
        courses = new ArrayList<>();
        ref = FirebaseDatabase.getInstance("https://b07-project-e5893-default-rtdb.firebaseio.com/")
                .getReference();

        ref.child("courses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // If the courses object in the database changes in any way, clear and refill
                // the local storage
                courses.clear();
                resetDatabase(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("CourseDatabase", "Error updating the local course list");
            }
        });
    }

    private void resetDatabase(@NonNull DataSnapshot snapshot) {
        Log.i("FixingRequired", "Database Reset.");

        GenericTypeIndicator<ArrayList<String>> toStringList
                = new GenericTypeIndicator<ArrayList<String>>(){};
        GenericTypeIndicator<ArrayList<Course>> toCourseList
                = new GenericTypeIndicator<ArrayList<Course>>(){};

        snapshot.getChildren()
                .forEach(s -> {
                    // Parsing the name, code, sessionalDates, prerequisites
                    String name = s.child("name").getValue(String.class);
                    String code = s.child("code").getValue(String.class);
                    ArrayList<String> sessionalDates = (ArrayList<String>) s
                            .child("sessionalDates")
                            .getValue(toStringList);
                    Course course = new Course(name, code, sessionalDates, null);
                    courses.add(course);
                });

        // Prerequisites end up being copies of courses and not references.
        // The fix to that is to go through the snapshot after all the courses have been
        // instantiated and find each prerequisite's references.
        snapshot.getChildren()
                .forEach(s -> {
                    String code = s.child("code").getValue(String.class);
                    Course course = getCourse(code);
                    ArrayList<Course> prerequisiteCopies = (ArrayList<Course>) s
                            .child("prerequisites")
                            .getValue(toCourseList);

                    // Load up array of references from array of copies.
                    ArrayList<Course> prerequisiteReferences = new ArrayList<>();
                    if (prerequisiteCopies != null) {
                        prerequisiteCopies.forEach(copy -> {
                            Course referencedCourse = getCourse(copy.getCode());
                            if (referencedCourse != null) {
                                prerequisiteReferences.add(referencedCourse);
                            }
                        });
                    }

                    // Add this as a required course for all prereqs
                    prerequisiteReferences.forEach(prerequisite ->
                            prerequisite.addRequiredCourse(course));

                    course.setPrerequisites(prerequisiteReferences);
                });
    }

    public static CourseDatabase getInstance() {
        if(courseDB == null)
            courseDB = new CourseDatabase();
        return courseDB;
    }

    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Adds a course with the given properties to the database with a unique key.
     *
     * The unique key is read from the database's "uniqueVal" value that is then incremented to
     * ensure that it will always have a different value each time it is read.
     * @param course is the course to be added
     */

    @Override
    public void addCourse(Course course) {
        ref.child("uniqueVal").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("CourseDatabase", "Error reading uniqueVal", task.getException());
            } else {
                // If we get here, then there has been no error reading "uniqueVal" (ie the task
                // was successful)
                // First we read the "uniqueVal" value in the database
                Integer uniqueVal = task.getResult().getValue(Integer.class);

                // Increment the uniqueVal value in the database so that it will be unique the
                // next time we call it
                if (uniqueVal != null) {
                    ref.child("uniqueVal").setValue(uniqueVal + 1);
                }

                // Now we can add the course with our new unique key
                ref.child("courses").child(String.valueOf(uniqueVal)).setValue(course);
                /*
                Note: The above line MUST be inside this else{} statement so that we can add
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
                Log.e("CourseDatabase", "Error editing " + course);
            }
        });
    }

    @Override
    public Course getCourse(String code) {
        // We return the first instance of the course in courses
        // although there should only be one course with that code so its all good hopefully :)
        return courses.stream()
                .filter(course -> course.getCode().equals(code))
                .findFirst()
                .orElse(null);
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

    @Override
    public List<Course> getCourseListFromString(List<String> list) {
        List<Course> returnList = new ArrayList<>();
        list.forEach(code -> returnList.add(getCourse(code)));
        return returnList;
    }
}
