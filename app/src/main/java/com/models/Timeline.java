package com.models;

import android.util.Pair;

import com.models.course.Course;

import java.util.ArrayList;
import java.util.List;

public class Timeline {
    List<Session> timeline;

    public Timeline() {
        this.timeline = new ArrayList<>();
    }

    public List<Session> getTimeline() {
        return timeline;
    }

    public static Timeline makeTimeline(List<Course> coursesTaken,
                                        List<Course> coursesPlanned) {
        /*

        NOTE: This method currently allows for inf courses in one semester.
                    Do we want cap on courses per sem?

         */

        Timeline timeline = new Timeline();

        for (Course planned: coursesPlanned) {
            timeline.placeCourseOnTimeline(coursesTaken, planned);
        }

        return timeline;
    }

    public void placeCourseOnTimeline(List<Course> coursesTaken, Course coursePlanned) {
        List<Course> prereqs = coursePlanned.getPrerequisites();
        List<String> seasonsAllowed = coursePlanned.getSessionalDates();

        Pair<String, Integer> i = Session.getCurrentSession();
        Session iSession = findSessionByName(i.first, i.second);

        if (containsCourse(coursePlanned)) {
            return;
        }

        for (Course prereq : coursePlanned.getPrerequisites()) {
            placeCourseOnTimeline(coursesTaken, prereq);
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
        for (Session session : timeline) {
            if (session.getSessionCourses().contains(course)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Finds a session in this timeline by the "name" - the season and year of the session.
     * @param season The season to search for
     * @param year The year to search for
     * @return The found session if it exists, null otherwise.
     */

    private Session findSessionByName(String season, Integer year) {
        for (Session session : timeline) {
            if (season.equals(session.getSeason()) && year.equals(session.getYear())) {
                return session;
            }
        }

        return null;
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

        timeline.sort(null);
    }
}
