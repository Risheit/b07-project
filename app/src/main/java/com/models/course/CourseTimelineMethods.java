package com.models.course;

import com.models.users.User;
import com.planner.MainActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CourseTimelineMethods {

    public static List<String> getTakeableCourses (CourseDatabase cd, User currentUser, List<Course> toCheck) {
        ArrayList<String> takeableCourses = new ArrayList<>();
        boolean takeable;
        for (int i = 0; i < cd.courses.size(); i++) {
            takeable = true;
            ArrayList<Course> currentPreRequisites = new ArrayList<>(toCheck.get(i).getPrerequisites());
            for (int j = 0; j < currentPreRequisites.size(); j++) {
                if (!currentUser.getCourseCodesTaken().contains(currentPreRequisites.get(j).getCode())) {
                    // user does is not eligible to take this course
                    takeable = false;
                }
            }
            if (takeable) {
                takeableCourses.add(cd.courses.get(i).getCode());
            }
        }
        return takeableCourses;
    }

    private static List<Course> getListFromString(CourseDatabase cd, List<String> list) {
        ArrayList<Course> returnList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            returnList.add(cd.getCourse(list.get(i)));
        }
        return returnList;
    }

    public static List<String> getWantedCourses (CourseDatabase cd, User currentUser) {
        ArrayList<Course> courseList = new ArrayList<>(getListFromString(cd, currentUser.getCourseCodesPlanned()));
        return getTakeableCourses(cd, currentUser, courseList);
    }
}
