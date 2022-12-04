package com.models;

import android.util.Pair;

import com.models.course.Course;
import com.models.course.CourseDatabaseInterface;
import com.models.users.User;

import java.util.ArrayList;
import java.util.List;

public class Timeline {
    private final List<Session> timeline;

    public Timeline() {
        this.timeline = new ArrayList<>();
    }

    public List<Session> getTimeline() {
        timeline.sort(null);
        return timeline;
    }

    public static Timeline makeTimeline(User user, CourseDatabaseInterface cd) {
        User copy = new User(user);
        Timeline timeline = new Timeline();

        // Add all planned course prerequisites to the user's list of courses
        planAllNeededCoursePrerequisites(copy, cd);

        while (!copy.getCourseCodesPlanned().isEmpty()) {
            copy.getWantedCourseCodes(cd).forEach(course -> {
                timeline.placeCourseOnTimeline(course);
                copy.removePlannedCourse(course.getCode());
                copy.addTakenCourse(course.getCode());
            });
        }

        return timeline;
    }

    private static void planAllNeededCoursePrerequisites(User user, CourseDatabaseInterface cd) {
        user.getCourseCodesPlanned().forEach(courseCode -> {
            List<Course> prerequisiteList = getFullPrerequisiteList(cd.getCourse(courseCode));

            prerequisiteList.stream()
                    .filter(course ->!user.getCourseCodesTaken().contains(course.getCode()))
                    .forEach(course -> user.addPlannedCourse(course.getCode()));
        });
    }

    private static List<Course> getFullPrerequisiteList(Course course) {
        List<Course> prerequisiteList = new ArrayList<>(course.getPrerequisites());
        prerequisiteList.forEach(prerequisite ->
                prerequisiteList.addAll(getFullPrerequisiteList(prerequisite)));
        return prerequisiteList;
    }

    public void placeCourseOnTimeline(Course coursePlanned) {
        List<Course> prereqs = coursePlanned.getPrerequisites();
        List<String> seasonsAllowed = coursePlanned.getSessionalDates();

        Pair<String, Integer> i = Session.getCurrentSession();
        Session iSession = findSessionByName(i.first, i.second);

        if (this.containsCourse(coursePlanned)) {
            return;
        }

        do {
            if ((iSession != null && iSession.anyCoursesInSession(prereqs))
                || !seasonsAllowed.contains(i.first)) {
                i = Session.moveToNextSession(i);
                iSession = findSessionByName(i.first, i.second);
            } else {
                addToSession(i.first, i.second, coursePlanned);
                break;
            }
        } while (true);
    }

    /**
     * This method checks if the this timeline contains a given course.
     * @param course The course to be searched for
     * @return True iff the the timeline contains the course.
     */

    private boolean containsCourse(Course course) {
        return timeline.stream().anyMatch(session -> session.getSessionCourses().contains(course));
    }

    /**
     * Finds a session in this timeline by the "name" - the season and year of the session.
     * @param season The season to search for
     * @param year The year to search for
     * @return The found session if it exists, null otherwise.
     */

    private Session findSessionByName(String season, Integer year) {
        return timeline.stream()
                .filter(session -> season.equals(session.getSeason())
                        && year.equals(session.getYear()))
                .findFirst()
                .orElse(null);
    }

    private void addToSession(String season, Integer year, Course course) {
        // Create new session in timeline if it doesn't exist
        Session foundSession = findSessionByName(season, year);
        if (foundSession == null) {
            Session session = new Session(season, year, new ArrayList<>());
            session.addToCourseList(course);
            timeline.add(session);
        } else {
            foundSession.addToCourseList(course);
        }
    }
}
