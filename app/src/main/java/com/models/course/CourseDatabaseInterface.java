package com.models.course;

import java.util.ArrayList;
import java.util.List;

public interface CourseDatabaseInterface {
    /***
     * This method will add a course to the database
     * @param course is the course to be added
     */
    void addCourse(Course course);

    /***
     * This method will copy the values of the argument course to the target course,
     * does nothing if target course does not exist
     * @param course is the object to copy the values from
     * @param code is the key of the target course
     */
    void editCourse(Course course, String code);

    /***
     * This method returns a Course object matching the key, or null if object does not exist
     * @param key is the key of the target course
     * @return object of type Course corresponding to the key, null if does not exist
     */
    Course getCourse(String key);

    /***
     * This method removes a Course object from the database corresponding to key,
     * does nothing if target course does not exist
     * @param key is the key of the target course
     */
    void removeCourse(String key);


    /**
     * This method takes in a list of course codes and returns the applicable courses in the
     * database. Invalid course codes are replaced with null.
     * @param list The list of course codes
     * @return A list of corresponding courses.
     */
    List<Course> getCourseListFromString(List<String> list);

}