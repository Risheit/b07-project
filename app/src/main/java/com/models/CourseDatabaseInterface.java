package com.models;

public interface CourseDatabaseInterface {

    void addCourse(Course course);
    void getCourse(String code);
    void removeCourse(String code);
}
