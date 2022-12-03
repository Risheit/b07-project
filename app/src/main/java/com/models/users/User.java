package com.models.users;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class User {
    private String type;
    private String name;
    private String email;
    private String password;
    private Map<String, String> courseCodesTaken;  // Key and value are identical
    private Map<String, String> courseCodesPlanned; // Key and value are identical

    private final String dummyCourseTaken = "dTaken";
    private final String dummyCoursePlanned = "dPlan";

    public User() {
        this.courseCodesTaken = new HashMap<>();
        courseCodesTaken.put(dummyCourseTaken, dummyCourseTaken);

        this.courseCodesPlanned = new HashMap<>();
        courseCodesPlanned.put(dummyCourseTaken, dummyCoursePlanned);
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

        this.courseCodesTaken = new HashMap<>();
        courseCodesTaken.put(dummyCourseTaken, dummyCourseTaken);

        this.courseCodesPlanned = new HashMap<>();
        courseCodesPlanned.put(dummyCourseTaken, dummyCoursePlanned);
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

    public User(String type, String name, String email, String password, Set<String> courseCodesTaken,
                Set<String> courseCodesPlanned) {
        this.type = type;
        this.name = name;
        this.email = email;
        this.password = password;
        this.setCourseCodesTaken(courseCodesTaken);
        this.setCourseCodesPlanned(courseCodesPlanned);
    }

    private static Set<String> mapToSet(Map<String, String> map) {
        return new HashSet<>(map.keySet());
    }

    private static Map<String, String> setToMap(Set<String> set) {
        Map<String, String> map = new HashMap<>();
        for (String entry : set) {
            map.put(entry, entry);
        }

        return map;
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
     * taken courses. Prefer the use of {@code getCourseCodesTakenAsSet} instead, as the
     * retrieved data from this method is unfiltered and contains data intended for internal use
     * that may cause issues in other code.
     *
     * This method exists to allow compatibility with Firebase.
     * @return A set of codes representing the courses the user has taken.
     */

    public Map<String, String> getCourseCodesTaken() {
        return courseCodesTaken;
    }

    /**
     * This method retrieves the calling object's (an instance of User)
     * taken courses as a set.
     * @return A set of codes representing the courses the user has taken.
     */

    public Set<String> getCourseCodesTakenAsSet() {
        Set<String> set = mapToSet(courseCodesTaken);
        set.remove(dummyCourseTaken);
        return set;
    }

    public void setCourseCodesTaken(Map<String, String> courseCodesTaken) {
        this.courseCodesTaken = courseCodesTaken;
        this.courseCodesTaken.put(dummyCourseTaken, dummyCourseTaken);
    }

    /**
     *  This method modifies the calling object's (an instance of User) set of courses taken.
     * @param courseCodesTaken  The new courses taken for the user
     */

    public void setCourseCodesTaken(Set<String> courseCodesTaken) {
        Map<String, String> map = setToMap(courseCodesTaken);
        map.put(dummyCourseTaken, dummyCourseTaken);
        this.courseCodesTaken = map;
    }

    /**
     * This method adds a course to the set containing the courses this user has taken.
     * @param courseCode  The code of the course to be added
     * @return True if the course is added, false if the user has already taken this course.
     */

    public boolean addTakenCourse(String courseCode) {
        Set<String> courseCodesTaken = this.getCourseCodesTakenAsSet();
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
        Set<String> courseCodesTaken = this.getCourseCodesTakenAsSet();
        boolean result = courseCodesTaken.remove(courseCode);
        if (result) {
            setCourseCodesTaken(courseCodesTaken);
        }

        return result;
    }

    /**
     * This method retrieves the calling object's (an instance of User)
     * planned courses. Prefer the use of {@code getCourseCodesPlannedAsSet} instead, as the
     * retrieved data from this method is unfiltered and contains data intended for internal use
     * that may cause issues in other code.
     *
     * This method exists to allow compatibility with Firebase.
     * @return A set of codes representing the courses the user has planned.
     */


    public Map<String, String> getCourseCodesPlanned() {
        return courseCodesPlanned;
    }

    /**
     * This method retrieves the calling object's (an instance of User)
     * planned courses.
     * @return A set of codes representing the courses the user has planned.
     */

    public Set<String> getCourseCodesPlannedAsSet() {
        Set<String> set = mapToSet(courseCodesPlanned);
        set.remove(dummyCoursePlanned);
        return set;
    }

    public void setCourseCodesPlanned(Map<String, String> courseCodesPlanned) {
        this.courseCodesPlanned = courseCodesPlanned;
        this.courseCodesPlanned.put(dummyCoursePlanned, dummyCoursePlanned);
    }

    /**
     *  This method modifies the calling object's (an instance of User) set of courses planned.
     * @param courseCodesPlanned  The new courses planned for the user
     */

    public void setCourseCodesPlanned(Set<String> courseCodesPlanned) {
        Map<String, String> map = setToMap(courseCodesPlanned);
        map.put(dummyCoursePlanned, dummyCoursePlanned);
        this.courseCodesPlanned = map;
    }

    /**
     * This method adds a course to the set containing the courses this user has planned to take.
     * @param courseCode  The code of the course to be added
     * @return True if the course is added, false if the user has already planned to take
     *         this course.
     */

    public boolean addPlannedCourse(String courseCode) {
        Set<String> courseCodesPlanned = this.getCourseCodesPlannedAsSet();
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
        Set<String> courseCodesPlanned = this.getCourseCodesPlannedAsSet();
        boolean result = courseCodesPlanned.remove(courseCode);
        if (result) {
            setCourseCodesPlanned(courseCodesPlanned);
        }

        return result;
    }
}
