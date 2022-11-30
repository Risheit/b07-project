package com.presenters;

import com.models.CourseDatabaseInterface;

import java.util.ArrayList;
import java.util.List;

public class CourseManagement implements AddNewCourse, CheckCourse, RetrieveCourse, DeleteCourse{

    private final CourseDatabaseInterface listOfCourses;

    /**
     * Instantiates a new CourseManagement class.
     * @param listOfCourses     an adapter interface that contains database-accessing methods
     * @return  an instance of a CourseManagement class
     */

    public CourseManagement(CourseDatabaseInterface listOfCourses) {
        this.listOfCourses = listOfCourses;
    }

    /**
     * This method determines whether a course is in the master list of available courses.
     * @param code     the code of the course you want to search
     * @return  true if the course is in the database, false otherwise
     */

    //for scrum: I modified doesCourseExist to return a boolean instead of a Course to avoid
    //returning anything from the database

    @Override
    public boolean doesCourseExist(String code) {
        Course c = listOfCourses.getCourse(code);
        if(c == null)
            return false;
        return true;
    }

    /**
     * This method adds a course to the course database.
     * @param course    the course you want to add to the database
     * @return  true if the course was successfully added to the database, false otherwise
     */

    @Override
    public boolean addNewCourse(Course course) {
        if(!doesCourseExist(course.getCode())){
            listOfCourses.addCourse(course);
            return true;
        }
            return false;
    }

    /**
     * This method returns a course from the database, assuming its already in the database.
     * @param code   the code of the course you want to retrieve
     * @return  the course with the corresponding code, or null otherwise
     */

    @Override
    public Course retrieveCourse(String code) {
        if(doesCourseExist(code)){
            return listOfCourses.getCourse(code);
        }
        return null;
    }

    /***
     This function deletes a course from the master list of courses
     @param code   the  code of the course you want to delete
     @returns true if the course was successfully removed, false otherwise
     */

    @Override
    public boolean deleteCourse(String code) {
        if(doesCourseExist(code)){
            listOfCourses.removeCourse(code);
            return true;
        }
        return false;
    }

}
