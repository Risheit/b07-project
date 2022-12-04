package com.models.course;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Course {
    private String name;
    private String code;
    private List<String> sessionalDates;
    private List<Course> prerequisites;
    private final List<Course> requiresThisCourse;
    private final List<Observer> observers;

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
        requiresThisCourse = new ArrayList<>();
        observers = new ArrayList<>();
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
        observers = new ArrayList<>();
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
        observers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyAllObservers();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
        notifyAllObservers();
    }

    public List<String> getSessionalDates() {
        return sessionalDates;
    }

    public void setSessionalDates(ArrayList<String> sessionalDates) {
        this.sessionalDates = sessionalDates;
        notifyAllObservers();
    }

    public List<Course> getPrerequisites() {
        return prerequisites;
    }

    private void addCoursesThatRequire(List<Course> courseList) {
        for (int i = 0; i < courseList.size(); i++) {
            courseList.get(i).requiresThisCourse.add(this);
        }
    }

    /***
     * Recursive function to check that a course doesn't circularly add a prerequisite
     * @param toCheckCourse the prerequisite to check which shouldn't already exist
     * @param indx should be 0 when this method is called
     * @return true if the prerequisite is not circular, false if it is
     */
    private boolean validPrerequisite(Course toCheckCourse, int indx) {
        if (this.code.equals(toCheckCourse.code)) {
            return false;
        }
        if (requiresThisCourse.size() > 0) {
            return requiresThisCourse.get(indx).validPrerequisite(toCheckCourse, indx++);
        }


        return true;
    }

    public void setPrerequisites(List<Course> prerequisites) {
        ArrayList<Course> validPrerequisites = new ArrayList<>();
        prerequisites.forEach(course -> {
            if (validPrerequisite(course, 0)) {
                validPrerequisites.add(course);
            }
        });

        addCoursesThatRequire(validPrerequisites);
        this.prerequisites = validPrerequisites;
        notifyAllObservers();
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    /***
     * Copies all of the course data from one course to another, not including observers
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
