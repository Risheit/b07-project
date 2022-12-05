package com.presenters;

import com.models.course.Course;
import com.models.course.CourseDatabase;
import com.planner.AddCourseActivity;
import com.planner.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddCoursePresenter {
    CourseDatabase courseDB;
    AddCourseActivity view;

    public AddCoursePresenter(AddCourseActivity view, CourseDatabase courseDB) {
        this.view = view;
        this.courseDB = courseDB;
    }

    public void onBackButtonClicked() {
        view.openAdminHomepage(view);
    }

    public void onDoneButtonClicked() {
        String course_code = view.getCourseCodeInput().getText().toString();
        String name = view.getNameInput().getText().toString();
        String sessionsOffered = view.getSessionsOfferedInput().getText().toString();

        if (course_code.isEmpty() || sessionsOffered.isEmpty() || name.isEmpty()) {
            view.displayErrorNotification(view, "Please Enter All Possible Fields");
            return;
        }

        /* steps
        1. parse the course codes and place them in an array
        2. Create an ArrayList of Course object
        3. for each code in the array:
          - check if the course is in the database (make a boolean function that ce)
          - if yes, retrieve the course and add it to the list
          - if no, don't add it and send a toast message (idk how to do that lol) that one
          or more courses are not created
         */

        String prerequisiteString = view.getPrereqInput().getText().toString();
        List<String> prerequisiteCodes = Arrays.asList(prerequisiteString.split(","));

        boolean allPrereqsExistInDatabase = prerequisiteCodes.stream()
                .noneMatch(code -> courseDB.getCourse(code) == null && !code.equals(""));

        if (!allPrereqsExistInDatabase) {
            view.displayErrorNotification(view,
                    "One or more of your prerequisite courses is not " +
                            "registered in the database. Addition cancelled");
            return;
        }

        // The course is already in the database
        if (courseDB.getCourse(course_code) != null) {
            view.displayErrorNotification(view, "Course already in database");
            return;
        }

        courseDB.addCourse(new Course(
                course_code,
                name,
                getSessionDatesFromString(sessionsOffered),
                getCoursesFromCodes(prerequisiteCodes)
        ));
        view.displayNotification(view, "Course Added");
        view.openAdminHomepage(view);
    }

    private List<String> getSessionDatesFromString(String sessionsOffered) {
        return Arrays.asList(sessionsOffered.split(","));
    }

    private List<Course> getCoursesFromCodes(List<String> codes) {
        List<Course> courses = new ArrayList<>();
        codes.forEach(code -> {
            Course course = courseDB.getCourse(code);
            if (course != null)
                courses.add(course);
        });

        return courses;
    }
}
