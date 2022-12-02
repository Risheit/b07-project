package com.models.course;

import com.models.course.Course;

public interface RetrieveCourse {

    /***
     This function retrieves a course from the list of courses
     @param code    the code of the course you want to retrieve
     @return the course with the matching code, or null otherwise
     */

    Course retrieveCourse(String code);
}
