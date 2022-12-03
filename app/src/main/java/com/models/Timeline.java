package com.models;

import com.models.course.Course;

import java.util.ArrayList;
import java.util.List;

public class Timeline {
    public static List<Session> generateTimeline(List<Course> coursesTaken,
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

        List<Session> timeline = new ArrayList<>();


        return null;
    }

    private static void placeCourseOnTimeline(List<Course> coursesTaken, Course coursePlanned,
                                              List<Session> timeline) {
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
                    ENDWHILE
                ELSE:
                    i := 0
                    WHILE i < prereqs:
                        placeCourseOnTimeline(prereqs[i], coursesTaken, timeline)
                    ENDWHILE
                ENDIF
            ENDIF
         */
    }
}
