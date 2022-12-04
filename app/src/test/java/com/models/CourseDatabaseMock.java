package com.models;

import com.models.course.Course;
import com.models.course.CourseDatabaseInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseDatabaseMock implements CourseDatabaseInterface {
    private List<Course> courses;

    @Override
    public void addCourse(Course course) {
        if (!courses.contains(course)){
            courses.add(course);
        }
    }

    @Override
    public void editCourse(Course course, String code) {
        removeCourse(code);
        addCourse(course);
    }

    @Override
    public Course getCourse(String key) {
        return courses.stream()
                .filter(course -> course.getCode().equals(key))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void removeCourse(String key) {
        courses = courses.stream()
                .filter(c -> !c.getCode().equals(key))
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> getCourseListFromString(List<String> list) {
        List<Course> returnList = new ArrayList<>();
        list.forEach(code -> returnList.add(getCourse(code)));
        return returnList;
    }
}
