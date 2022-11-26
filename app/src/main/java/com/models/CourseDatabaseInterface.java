package com.models;

import com.presenters.Course;

public interface CourseDatabaseInterface {

    void addCourse(Course course);
    Course getCourse(String code);
    void removeCourse(String code);
}
