package com.presenters;

import com.models.Session;
import com.models.course.Course;
import com.models.course.CourseDatabase;
import com.planner.AddCourseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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
        String course_code = view.getNameInput().getText().toString();
        String name = view.getCourseCodeInput().getText().toString();
        String sessionsOffered = view.getSessionsOfferedInput().getText().toString();

        if (course_code.isEmpty() || sessionsOffered.isEmpty() || name.isEmpty()) {
            view.displayErrorNotification(view, "Please Enter All Possible Fields");
            return;
        }

        if (Session.getValidSessionDatesFromString(sessionsOffered).isEmpty()) {
            view.displayErrorNotification(view, "Invalid course has been offered");
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
                name,
                course_code,
                Session.getValidSessionDatesFromString(sessionsOffered),
                courseDB.getCourseListFromString(prerequisiteCodes)
        ));
        view.displayNotification(view, "Course Added");
        view.openAdminHomepage(view);
    }
}
