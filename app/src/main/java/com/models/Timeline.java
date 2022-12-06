package com.models;

import com.models.course.Course;
import com.models.course.CourseDatabaseInterface;
import com.models.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        Timeline timeline = new Timeline();
        List<Course> takenCourses = cd.getCourseListFromString(user.getCourseCodesTaken());
        user.getCourseCodesPlanned().forEach(code -> {
                if (!user.getCourseCodesTaken().contains(code)) {
                    timeline.placeCourseOnTimeline(takenCourses, cd.getCourse(code));
                }
        });

        return timeline;
    }

    public void placeCourseOnTimeline(List<Course> takenCourses, Course coursePlanned) {
        List<Course> prereqs = coursePlanned.getPrerequisites();
        List<String> seasonsAllowed = coursePlanned.getSessionalDates()
                .stream().map(String::toLowerCase)
                .collect(Collectors.toList());

        if (this.containsCourse(coursePlanned) || takenCourses.contains(coursePlanned)) {
            return;
        }

        coursePlanned.getPrerequisites()
                .forEach(prerequisite -> placeCourseOnTimeline(takenCourses, prerequisite));

        Pair<String, Integer> i = Session.getCurrentSession();
        while (true) {
            Session iSession = findSessionByName(i.first, i.second);
            if ((iSession != null && iSession.anyCoursesInSession(prereqs))
                || !seasonsAllowed.contains(i.first.toLowerCase())) {
                i = Session.moveToNextSession(i);
            } else {
                addToSession(i.first, i.second, coursePlanned);
                break;
            }
        }
    }

    /**
     * This method checks if the this timeline contains a given course.
     * @param course The course to be searched for
     * @return True iff the the timeline contains the course.
     */

    public boolean containsCourse(Course course) {
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
