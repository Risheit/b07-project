package com.models;

import com.models.course.Course;
import com.models.course.CourseDatabaseInterface;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CourseTests {
    Course course1, course2, course3, course4;

    @Before
    public void setUp() {

        course1 = new Course("No Prereqs", "NOPREQ", null);
        course2 = new Course("Prereq on NOPREQ", "1PREQ", null);
        course3 = new Course("Prereq on 1PREQ", "1PREQ2", null);
        course4 = new Course("Prereq on 1PREQ and 1PREQ2", "2PREQ", null);
    }

    @Test
    public void testNotAbleToHaveCircularPrerequisites() {
        course1.setPrerequisites(Collections.singletonList(course4));
        course1.setPrerequisites(Collections.singletonList(course3));
        course1.setPrerequisites(Collections.singletonList(course2));
        Assert.assertEquals(new ArrayList<>(), course1.getPrerequisites());
    }

    @Test
    public void testNotAbleToHaveSelfAsPrerequisite() {
        List<Course> expectedPrereqs = new ArrayList<>(course4.getPrerequisites());
        course4.setPrerequisites(Collections.singletonList(course4));
        Assert.assertEquals(expectedPrereqs, course4.getPrerequisites());
    }
}
