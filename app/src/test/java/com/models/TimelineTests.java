package com.models;

import com.models.course.Course;
import com.models.course.CourseDatabaseInterface;
import com.models.users.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TimelineTests {
    private CourseDatabaseInterface db;
    private List<Session> expectedTimeline;
    private Pair<String, Integer> currentSession;
    private User user;

    private Course course1, course2, course3, course4, course5;

    @Before
    public void setUp() {
        db = new CourseDatabaseMock();
        expectedTimeline = new ArrayList<>();
        currentSession = Session.getCurrentSession();
        user = new User("Student", "Test McTesterface", "luvtests@testmail.ca",
                "testmebaby");

        course1 = new Course("Introduction To Basic Testing", "TST101",
                Collections.singletonList(Session.fall));
        course2 = new Course("Introduction to Model Theory", "MTT101",
                Collections.singletonList(Session.summer));
        course3 = new Course("Advanced Basic Testing", "TST102",
                Arrays.asList(Session.fall, Session.winter),
                Collections.singletonList(course1));
        course4 = new Course("Introduction To Database Design", "DBD101",
                Collections.singletonList(Session.winter));
        course5 = new Course("Building Custom Mockers for Databases", "TST221",
                Arrays.asList(Session.winter, Session.summer),
                Arrays.asList(course2, course3, course4));

        db.addCourse(course1);
        db.addCourse(course2);
        db.addCourse(course3);
        db.addCourse(course4);
        db.addCourse(course5);
    }

    private void addCoursesToCurrentSession(List<Course> courses) {
        expectedTimeline.add(new Session(
                currentSession.first,
                currentSession.second,
                courses
        ));
    }

    private void incrementCurrentSession() {
        currentSession = Session.moveToNextSession(currentSession);
    }

    @Test
    public void testMakeTimelineWithNoTakenCourses() {
        user.addPlannedCourse(course5.getCode());

        // Generate expected timeline
        addCoursesToCurrentSession(Collections.singletonList(course1));

        incrementCurrentSession();
        addCoursesToCurrentSession(Arrays.asList(course3, course4));

        incrementCurrentSession();
        addCoursesToCurrentSession(Collections.singletonList(course2));

        incrementCurrentSession();
        incrementCurrentSession();
        addCoursesToCurrentSession(Collections.singletonList(course5));

        List<Session> timeline = Timeline.makeTimeline(user, db).getTimeline();
        Assert.assertEquals(expectedTimeline, timeline);
    }

    @Test
    public void testMakeTimelineWithTakenCourses() {
        user.addPlannedCourse(course5.getCode());
        user.addTakenCourse(course2.getCode());
        user.addTakenCourse(course1.getCode());

        // Generate expected timeline
        addCoursesToCurrentSession(Collections.singletonList(course3));

        incrementCurrentSession();
        addCoursesToCurrentSession(Collections.singletonList(course4));

        incrementCurrentSession();
        addCoursesToCurrentSession(Collections.singletonList(course5));

        Assert.assertEquals(expectedTimeline, Timeline.makeTimeline(user, db).getTimeline());
    }

    @Test
    public void testMakeTimelineWithOnlyNoPlannedCourses() {
        Assert.assertEquals(expectedTimeline, Timeline.makeTimeline(user, db).getTimeline());
    }

    @Test
    public void testMakeTimelineWithTakenPlannedCourses() {
        user.addPlannedCourse(course1.getCode());
        user.addTakenCourse(course1.getCode());

        Assert.assertEquals(expectedTimeline, Timeline.makeTimeline(user,db).getTimeline());
    }
}
