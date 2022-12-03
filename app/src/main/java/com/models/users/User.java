package com.models.users;

import java.sql.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class User {
    private String type;
    private String name;
    private String email;
    private String password;
    private String[] courseCodesTaken;
    private String[] courseCodesPlanned;

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
        this.courseCodesTaken = new String[]{};
        this.courseCodesPlanned = new String[]{};
    }

    /**
     * This constructor returns an instance of a User
     * @param type  Indicates whether a User is a student or admin
     * @param name  The full name of the user
     * @param email  The email that the user's account is linked to
     * @param password  The password of the user's account
     * @param courseCodesTaken  The courses that the user has already represented by its code
     * @param courseCodesPlanned The courses that the user plans to take represented by its code
     */

    public User(String type, String name, String email, String password, String[] courseCodesTaken,
                String[] courseCodesPlanned) {
        this.type = type;
        this.name = name;
        this.email = email;
        this.password = password;

        setCourseCodesTaken(new HashSet<>(Arrays.asList(courseCodesTaken)));
        setCourseCodesPlanned(new HashSet<>(Arrays.asList(courseCodesPlanned)));
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
     * @return A set of codes representing the courses the user has taken.
     */

    public Set<String> getCourseCodesTaken() {
        return new HashSet<>(Arrays.asList(courseCodesTaken));
    }

    /**
     *  This method modifies the calling object's (an instance of User) set of courses taken.
     * @param courseCodesTaken  The new courses taken for the user
     */

    public void setCourseCodesTaken(Set<String> courseCodesTaken) {
        this.courseCodesTaken = courseCodesTaken.toArray(new String[0]);
    }

    /**
     * This method adds a course to the set containing the courses this user has taken.
     * @param courseCode  The code of the course to be added
     * @return True if the course is added, false if the user has already taken this course.
     */

    public boolean addTakenCourse(String courseCode) {
        Set<String> courseCodesTaken = this.getCourseCodesTaken();
        boolean result = courseCodesTaken.add(courseCode);
        if (result) {
            setCourseCodesTaken(courseCodesTaken);
        }

        return result;
    }

    /**
     * This method removes a course from the set containing the courses this user has taken.
     * @param courseCode  The code of the course to be added
     * @return True if the course is removed successfully, false otherwise.
     */

    public boolean removeTakenCourse(String courseCode) {
        Set<String> courseCodesTaken = this.getCourseCodesTaken();
        boolean result = courseCodesTaken.remove(courseCode);
        if (result) {
            setCourseCodesTaken(courseCodesTaken);
        }

        return result;
    }

    /**
     * This method retrieves the calling object's (an instance of User)
     * planned courses.
     * @return A set of codes representing the courses the user has planned.
     */

    public Set<String> getCourseCodesPlanned() {
        return new HashSet<>(Arrays.asList(courseCodesPlanned));
    }

    /**
     *  This method modifies the calling object's (an instance of User) set of courses planned.
     * @param courseCodesPlanned  The new courses planned for the user
     */

    public void setCourseCodesPlanned(Set<String> courseCodesPlanned) {
        this.courseCodesPlanned = courseCodesPlanned.toArray(new String[0]);
    }

    /**
     * This method adds a course to the set containing the courses this user has planned to take.
     * @param courseCode  The code of the course to be added
     * @return True if the course is added, false if the user has already planned to take
     *         this course.
     */

    public boolean addPlannedCourse(String courseCode) {
        Set<String> courseCodesPlanned = this.getCourseCodesPlanned();
        boolean result = courseCodesPlanned.add(courseCode);
        if (result) {
            setCourseCodesPlanned(courseCodesPlanned);
        }

        return result;
    }

    /**
     * This method removes a course from the set containing the courses this user has planned to
     * take.
     * @param courseCode  The code of the course to be added
     * @return True if the course is removed successfully, false otherwise.
     */

    public boolean removePlannedCourse(String courseCode) {
        Set<String> courseCodesPlanned = this.getCourseCodesPlanned();
        boolean result = courseCodesPlanned.remove(courseCode);
        if (result) {
            setCourseCodesPlanned(courseCodesPlanned);
        }

        return result;
    }
}
