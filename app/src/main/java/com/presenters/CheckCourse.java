package com.presenters;

public interface CheckCourse {

    /***
     This function checks if a course exists in the database
     @param code    the code of the course you want to check
     @returns true if the course is within the database, false otherwise
     */

    boolean doesCourseExist(String code);
}
