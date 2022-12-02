package com.models.course;

public interface DeleteCourse {

    /***
     This function deletes a course from the master list of courses
     @param code   the  code of the course you want to delete
     @return true if the course was successfully removed, false otherwise
     */

    boolean deleteCourse(String code);
}
