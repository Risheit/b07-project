package com.presenters;

import com.models.CourseDatabaseInterface;

public class CourseManagement implements AddNewCourse, CheckCourse{

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
        Course c = listOfCourses.getCourse(code);

        if(!doesCourseExist(course.code)){
            listOfCourses.addCourse(c);
            return true;
        }
            return false;
    }

    //need to implement get course list and getCourse by key
}
