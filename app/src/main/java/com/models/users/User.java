package com.models.users;

import androidx.annotation.NonNull;

import com.models.course.Course;
import com.models.course.CourseDatabaseInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private String type;
    private String name;
    private String email;
    private String password;
    private List<String> courseCodesTaken;
    private List<String> courseCodesPlanned;


    public User() {
        type = "Student";
        name = "";
        email = "";
        password = "";
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
     */

    public void addTakenCourse(String courseCode) {
        if (!courseCodesTaken.contains(courseCode)) {
            courseCodesTaken.add(courseCode);
        }
    }

    /**
     * This method removes a course from the list containing the courses this user has taken.
     * @param courseCode  The code of the course to be added
     */

    public void removeTakenCourse(String courseCode) {
        courseCodesTaken.remove(courseCode);
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
     */

    public void addPlannedCourse(String courseCode) {
        if (courseCodesPlanned.contains(courseCode)) {
            courseCodesPlanned.add(courseCode);
        }
    }

    /**
     * This method removes a course from the set containing the courses this user has planned to
     * take.
     * @param courseCode  The code of the course to be added
     */

    public void removePlannedCourse(String courseCode) {
        courseCodesPlanned.remove(courseCode);
    }

    /**
     * Returns a List of Strings that contains the course codes of courses that a user
     * has the necessary prerequisites to take.
     * @param cd is the CourseDatabase
     * @param toCheck is the List of courses that will be checked against to check user eligibility
     * @return List of String that a user is eligible to take and has not already taken
     */

    public List<Course> getTakeableCourses (CourseDatabaseInterface cd, List<Course> toCheck) {
        ArrayList<Course> takeableCourses = new ArrayList<>();

        toCheck.forEach(course -> {
            boolean hasTakenPrerequisites = course.getPrerequisites().stream()
                    .allMatch(prerequisite ->
                            getCourseCodesTaken().contains(prerequisite.getCode()));
            boolean hasTakenCourse = getCourseCodesTaken().contains(course.getCode());

            if (hasTakenPrerequisites && !hasTakenCourse) {
                takeableCourses.add(course);
            }

        });
        return takeableCourses;
    }

    /**
     * Returns a List of course code Strings corresponding to the user's list of courses
     * planned to take.
     * @param cd is the CourseDatabase
     * @return a List of Strings of course codes the user is eligible to take
     */

    public List<Course> getWantedCourseCodes (CourseDatabaseInterface cd) {
        List<Course> courseList = cd.getCourseListFromString(getCourseCodesPlanned());
        return getTakeableCourses(cd, courseList);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + type.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + courseCodesTaken.hashCode();
        result = 31 * result + courseCodesPlanned.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof User) || this.hashCode() != other.hashCode()) {
            return false;
        }

        User user = (User) other;
        return (Objects.equals(this.type, user.type)
                && Objects.equals(this.name, user.name)
                && Objects.equals(this.email, user.email)
                && Objects.equals(this.password, user.password)
                && Objects.equals(this.courseCodesTaken, user.courseCodesTaken)
                && Objects.equals(this.courseCodesPlanned, user.courseCodesPlanned)
                );
    }

    @NonNull
    @Override
    public String toString() {
        return name + ", " + email;
    }
}
