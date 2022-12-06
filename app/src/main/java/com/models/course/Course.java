package com.models.course;

import android.util.Log;

import androidx.annotation.NonNull;

import com.models.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Course {
    private String name;
    private String code;
    private List<String> sessionalDates;
    private List<Course> prerequisites;
    private final List<Course> requiresThisCourse;

    /***
     * Default constructor for Course object
     * @param name is the name of the course
     * @param code is the course code
     * @param sessionalDates is an ArrayList of sessional dates for the course
     * @param prerequisites is an ArrayList of courses that are the prerequisites
     */
    public Course(String name, String code, List<String> sessionalDates, List<Course> prerequisites) {
        this.name = name;
        this.code = code;
        this.prerequisites = prerequisites;
        this.sessionalDates = sessionalDates;
        requiresThisCourse = new ArrayList<>();
        Log.i("FixingRequired", "requiresThisCourse made Empty in Line 36.");
        setPrerequisites(prerequisites);
    }

    /***
     * Constructor for courses with no prerequisites
     * @param name is the name of the course
     * @param code is the course code
     * @param sessionalDates is an ArrayList of sessional dates for the course
     */
    public Course(String name, String code, List<String> sessionalDates) {
        this.name = name;
        this.code = code;
        this.sessionalDates = sessionalDates;
        this.prerequisites = new ArrayList<>();
        requiresThisCourse = new ArrayList<>();
        Log.i("FixingRequired", "requiresThisCourse made Empty in Line 52.");
    }

    /***
     * Constructor for courses with no parameters
     */
    public Course() {
        this.name = "";
        this.code = "";
        this.sessionalDates = new ArrayList<>();
        this.prerequisites = new ArrayList<>();
        requiresThisCourse = new ArrayList<>();
        Log.i("FixingRequired", "requiresThisCourse made Empty in Line 64.");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getSessionalDates() {
        return sessionalDates;
    }

    /**
     * Sets the this courses sessional dates given a list of seasons that it'll be available in.
     *
     * The method replaces any invalid seasons in the given list with {@code Session.fall} and
     * only stores distinct seasons.
     * @param seasons The list of new seasons that the course will be occur on.
     */
    public void setSessionalDates(List<String> seasons) {
        this.sessionalDates = seasons.stream()
                .map(season -> isValidSeason(season) ? season : Session.fall)
                .distinct()
                .collect(Collectors.toList());
    }

    private boolean isValidSeason(String season) {
        List<String> validSessions = Arrays.asList(
                Session.fall.toLowerCase(),
                Session.winter.toLowerCase(),
                Session.summer.toLowerCase()
        );

        return season != null && validSessions.contains(season.toLowerCase());
    }

    public List<Course> getPrerequisites() {
        return prerequisites;
    }

    private void addCoursesThatRequire(Course c, List<Course> courseList) {
        courseList.forEach(course -> course.addRequiredCourse(c));
    }

    /***
     * Recursive function to check that a course doesn't circularly add a prerequisite
     * @param toCheckCourse the prerequisite to check which shouldn't already exist
     * @param index should be 0 when this method is called
     * @return true if the prerequisite is not circular, false if it is
     */
    private boolean validPrerequisite(Course toCheckCourse, int index) {
        if (this.code.equals(toCheckCourse.code)) {
            return false;
        }

        if (!requiresThisCourse.isEmpty()) {
            return requiresThisCourse.get(index).validPrerequisite(toCheckCourse, index++);
        }

        return true;
    }

    public void setPrerequisites(List<Course> prerequisites) {
        ArrayList<Course> validPrerequisites = new ArrayList<>();
        if (prerequisites != null) {
            prerequisites.forEach(course -> {
                if (validPrerequisite(course, 0)) {
                    validPrerequisites.add(course);
                }
            });
        }
        addCoursesThatRequire(this, validPrerequisites);
        this.prerequisites = validPrerequisites;
    }

    /**
     * Getter for {@code requiresThisCourse}. Named like it is to avoid Firebase from attempting
     * to store it.
     * @return A list of courses who have this course in their prerequisites.
     */
    public List<Course> obtainRequires() {
        return requiresThisCourse;
    }

    /***
     * Copies all of the course data from one course to another.
     * @param course is the course to copy data from
     */
    public void copyCourseData(Course course) {
        name = course.name;
        code = course.code;
        sessionalDates = course.getSessionalDates();
        prerequisites = course.getPrerequisites();
    }

    /**
     * Adds a new required course, which is a course that contains this course as a prerequisite.
     * @param course The required course to be added
     */
    public void addRequiredCourse(Course course) {
        if (!requiresThisCourse.contains(course)) {
            Log.i("FixingRequired", "adding " + course + " to requiresThisCourse in "
                    + this + ". \n The required course list is currently" + requiresThisCourse);
            requiresThisCourse.add(course);
        }
    }

    /**
     * Removes a required course, which is a course that contains this course as a prerequisite.
     * @param course The required course to be removed
     */
    public void removeRequiredCourse(Course course) {
        Log.i("FixingRequired", "removing " + course + " from requiresThisCourse");
        requiresThisCourse.remove(course);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Course) || this.hashCode() != other.hashCode()) {
            return false;
        }

        return this.code.equals(((Course) other).code);
    }

    @NonNull
    @Override
    public String toString() {
        return code;
    }
}
