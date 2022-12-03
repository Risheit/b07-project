package com.models;

import android.util.Pair;

import com.models.course.Course;

import java.util.ArrayList;
import java.util.List;

// TODO: Ask for Session to implement Comparable -- I want to sort the list/keep it sorted for ease of display
// TODO: Ask for method that takes takes a session (Pair<String, int>) and increases it to the next session
// TODO: A containsAnyOf method that returns true if any course in a list of courses is in a Session
// TODO: SessionalDates is the possible seasons right?

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


        generateTimeline(coursesTaken, coursesPlanned):
            i := 0
            timeline t;
            WHILE i < coursesPlanned.length:
                placeCourseOnTimeline(coursesPlanned[i], coursesTaken, t)
            ENDWHILE

         */

        Timeline timeline = new Timeline();

        for (Course planned: coursesPlanned) {
            timeline.placeCourseOnTimeline(coursesTaken, planned);
        }

        return timeline;
    }

    public void placeCourseOnTimeline(List<Course> coursesTaken, Course coursePlanned) {
        /*
         placeCourseOnTimeline(course, coursesTaken, timeline):
            prereqs := the list of prereqs of course
            sessions := the list of sessions that the course is available in

            IF course not on timeline:
                IF prereqs is empty OR all prereqs of course checked:
                    i := Fall 2023 (Starting Session)
                    WHILE true:
                        IF i contains prereq OR sessions does not contain i,
                            Increase i by the length of one session.
                        ELSE
                            Add the course to the timeline during session i.
                            BREAK
                    ENDWHILE
                ELSE:
                    i := 0
                    WHILE i < prereqs:
                        placeCourseOnTimeline(prereqs[i], coursesTaken, timeline)
                    ENDWHILE
                ENDIF
            ENDIF
         */

        List<Course> prereqs = coursePlanned.getPrerequisites();
        List<String> sessions = coursePlanned.getSessionalDates();


        if (containsCourse(coursePlanned)) {
            return;
        }

        for (Course prereq : coursePlanned.getPrerequisites()) {
            placeCourseOnTimeline(coursesTaken, prereq);
        }

        Pair<String, Integer> i = Session.getCurrentSession();
        do {
            if (/*findSessionByName(i.first, i.second).containsAnyOf(prereqs)
                ||*/ sessions.contains(i.first)) {
                // Increase i to next session
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
    }
}
