package com.models.course;

import com.models.users.User;
import com.planner.MainActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CourseTimelineMethods {

    /***
     * Returns a List of Strings that contains the course codes of courses that a user
     * has the necessary prerequisites to take
     * @param cd is the CourseDatabase
     * @param currentUser is the currentUser
     * @param toCheck is the List of courses to check user eligibility
     * @return List of String that a user is eligible to take and has not already taken
     */
    public static List<String> getTakeableCourses (CourseDatabase cd, User currentUser, List<Course> toCheck) {
        ArrayList<String> takeableCourses = new ArrayList<>();
        boolean takeable;
        for (int i = 0; i < cd.courses.size(); i++) {
            takeable = true;
            ArrayList<Course> currentPreRequisites = new ArrayList<>(toCheck.get(i).getPrerequisites());
            for (int j = 0; j < currentPreRequisites.size(); j++) {
                if (!currentUser.getCourseCodesTaken().contains(currentPreRequisites.get(j).getCode())
                || currentUser.getCourseCodesTaken().contains(toCheck.get(i).getCode())) {
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

    /***
     * Returns a List of course code Strings corresponding to the currentUser's list of courses planning to take
     * @param cd is the CourseDatabase
     * @param currentUser is the user who's courses planning to take are to be used
     * @return a List of Strings of course codes the user is eligible to take
     */
    public static List<String> getWantedCourses (CourseDatabase cd, User currentUser) {
        ArrayList<Course> courseList = new ArrayList<>(getListFromString(cd, currentUser.getCourseCodesPlanned()));
        return getTakeableCourses(cd, currentUser, courseList);
    }
}
