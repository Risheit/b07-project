package com.presenters;

import com.models.CourseDatabaseInterface;

public class CourseManagement implements AddNewCourse, CheckCourse, RetrieveCourse{

    private final CourseDatabaseInterface listOfCourses;

    public CourseManagement(CourseDatabaseInterface listOfCourses) {
        this.listOfCourses = listOfCourses;
    }

    @Override
    public boolean doesCourseExist(String code) {
        Course c = listOfCourses.getCourse(code);

        if(c == null)
            return false;
        return true;
    }

    @Override
    public boolean addNewCourse(Course course) {
        Course c = listOfCourses.getCourse(course.getCode());

        if(!doesCourseExist(course.getCode())){
            listOfCourses.addCourse(c);
            return true;
        }
            return false;
    }

    @Override
    public Course retrieveCourse(String code) {
        if(doesCourseExist(code)){
            return listOfCourses.getCourse(code);
        }
        return null;
    }

    //need to implement get course list
}
