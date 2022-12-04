package com.models.users;

import com.models.course.Course;
import com.models.course.CourseDatabase;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String type;
    private String name;
    private String email;
    private String password;
    private List<String> courseCodesTaken;
    private List<String> courseCodesPlanned;


    public User() {
        courseCodesTaken = new ArrayList<>();
        courseCodesPlanned = new ArrayList<>();
    }

    /**
     * This constructor returns an instance of a User
     * @param type  Indicates whether a User is a student or admin
     * @param name  The full name of the user
     * @param email  The email that the user's account is linked to
     * @param password  The password of the user's account
     */

    public User(String type, String name, String email, String password) {
        this.type = type;
        this.name = name;
        this.email = email;
        this.password = password;

        this.courseCodesTaken = new ArrayList<>();
        this.courseCodesPlanned = new ArrayList<>();
    }

    /**
     * This method retrieves the calling object's (an instance of User) type.
     * A user's type could either be Student or Admin.
     * @return The type of the calling object
     */

    public String getType() { return type; }

    /**
     * This method modifies the calling object's (an instance of User) type.
     * @param type     The desired type of the user (Student or Admin)
     */

    public void setType(String type) { this.type = type; }

    /**
     * This method retrieves the calling object's (an instance of User) name.
     * @return A string representing the calling object's name
     */

    public String getName() {
        return name;
    }

    /**
     * This method modifies the calling object's (an instance of User) name.
     * @param name     The new name of the user
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method retrieves the email that is linked
     * to the calling object's (an instance of User) account.
     * @return A string representing the email that the user's account uses
     */

    public String getEmail() {
        return email;
    }

    /**
     * This method modifies the calling object's (an instance of User) account email.
     * @param email     The new email of the user's account
     */

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method retrieves the calling object's (an instance of User)
     * password for their account.
     * @return A string representing their password for the account.
     */


    public String getPassword() {
        return password;
    }

    /**
     * This method modifies the calling object's (an instance of User) name.
     * @param password     The new password of the user's account
     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method retrieves the calling object's (an instance of User)
     * taken courses.
     * @return A list of codes representing the courses the user has taken.
     */

    public List<String> getCourseCodesTaken() {
        return courseCodesTaken;
    }

    /**
     * This method modifies the calling object's (an instance of User) planned courses.
     * @param courseCodesTaken  The codes of the courses the user plans to take
     */

    public void setCourseCodesTaken(ArrayList<String> courseCodesTaken) {
        this.courseCodesTaken = courseCodesTaken;
    }

    /**
     * This method adds a course to the list containing the courses this user has taken if that
     * course is not already in the list.
     * @param courseCode  The code of the course to be added
     * @return True iff the course is added to the list.
     */

    public boolean addTakenCourse(String courseCode) {
        return !courseCodesTaken.contains(courseCode) && courseCodesTaken.add(courseCode);
    }

    /**
     * This method removes a course from the list containing the courses this user has taken.
     * @param courseCode  The code of the course to be added
     * @return True if the course is removed successfully, false otherwise.
     */

    public boolean removeTakenCourse(String courseCode) {
        return courseCodesTaken.remove(courseCode);
    }

    /**
     * This method retrieves the calling object's (an instance of User)
     * planned courses.
     * @return A list of codes representing the courses the user has planned.
     */

    public List<String> getCourseCodesPlanned() {
        return courseCodesPlanned;
    }

    /**
     *  This method modifies the calling object's (an instance of User) set of courses planned.
     * @param courseCodesPlanned  The course codes representing new courses planned for the user
     */

    public void setCourseCodesPlanned(ArrayList<String> courseCodesPlanned) {
        this.courseCodesPlanned = courseCodesPlanned;
    }


    /**
     * This method adds a course to the set containing the courses this user has planned to take.
     * @param courseCode  The code of the course to be added
     * @return True if the course is added, false if the user has already planned to take
     *         this course.
     */

    public boolean addPlannedCourse(String courseCode) {
        return !courseCodesPlanned.contains(courseCode) && courseCodesPlanned.add(courseCode);
    }

    /**
     * This method removes a course from the set containing the courses this user has planned to
     * take.
     * @param courseCode  The code of the course to be added
     * @return True if the course is removed successfully, false otherwise.
     */

    public boolean removePlannedCourse(String courseCode) {
        return courseCodesPlanned.remove(courseCode);
    }

    /**
     * Returns a List of Strings that contains the course codes of courses that a user
     * has the necessary prerequisites to take.
     * @param cd is the CourseDatabase
     * @param toCheck is the List of courses that will be checked against to check user eligibility
     * @return List of String that a user is eligible to take and has not already taken
     */

    public List<String> getTakeableCourses (CourseDatabase cd, List<Course> toCheck) {
        ArrayList<String> takeableCourses = new ArrayList<>();

        toCheck.forEach(course -> {
            boolean hasTakenPrerequisites = course.getPrerequisites().stream()
                    .allMatch(prerequisite ->
                            getCourseCodesTaken().contains(prerequisite.getCode()));
            boolean hasTakenCourse = getCourseCodesTaken().contains(course.getCode());

            if (hasTakenPrerequisites && !hasTakenCourse) {
                takeableCourses.add(course.getCode());
            }

        });
        return takeableCourses;
    }

    private static List<Course> getCourseListFromString(CourseDatabase cd, List<String> list) {
        List<Course> returnList = new ArrayList<>();
        list.forEach(code -> returnList.add(cd.getCourse(code)));
        return returnList;
    }

    /**
     * Returns a List of course code Strings corresponding to the user's list of courses
     * planned to take.
     * @param cd is the CourseDatabase
     * @return a List of Strings of course codes the user is eligible to take
     */

    public List<String> getWantedCourseCodes (CourseDatabase cd) {
        List<Course> courseList = getCourseListFromString(cd, getCourseCodesPlanned());
        return getTakeableCourses(cd, courseList);
    }
}
