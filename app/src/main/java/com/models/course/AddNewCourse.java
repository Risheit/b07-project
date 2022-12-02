package com.models.course;

import com.models.course.Course;

public interface AddNewCourse {

    /***
     This function retrieves a course from the list of courses
     @param course    the  course you want to add to the database
     @return true if the course was successfully added, false otherwise
     */

    boolean addNewCourse(Course course);
}
