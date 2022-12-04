package com.models.course;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String name;
    private String code;
    private List<String> sessionalDates;
    private List<Course> prerequisites;

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
        this.sessionalDates = sessionalDates;
        this.prerequisites = prerequisites;
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
    }

    /***
     * Constructor for courses with no parameters
     */
    public Course() {
        this.name = "";
        this.code = "";
        this.sessionalDates = new ArrayList<>();
        this.prerequisites = new ArrayList<>();
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

    public void setSessionalDates(ArrayList<String> sessionalDates) {
        this.sessionalDates = sessionalDates;
    }

    public List<Course> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(ArrayList<Course> prerequisites) {
        this.prerequisites = prerequisites;
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
