package com.models;

import com.models.course.Course;

import java.util.List;

public class Timeline {
    public static List<Session> generateTimeline(List<Course> coursesTaken,
                                                 List<Course> coursesPlanned) {
        /*

            Pick coursesPlanned[i], 0 <= i <= coursesPlanned.length.
            IF course not on timeline: (*)
                IF prereqs is empty OR all prereqs of course checked:
                    Let [s] be the sessions the course is being held during.
                    Begin with [i] = Fall 2023 (Starting Session)
                        IF [i] contains prereq of course or [s] does not contain [i],
                            Increase [i] by one session.
                        ELSE
                            Add the course to the timeline during session [i].

                    NOTE: This allows for inf courses in one semester.
                    Do we want cap on courses per sem?

                ELSE:
                    Pick prereqs[i], 0 <= i <= prereqs.length.
                    Move to [line *] with the chosen prereq course.
                ENDIF
            ENDIF

         */


        return null;
    }
}
