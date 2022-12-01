package com.presenters;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String name;
    private String code;
    private List<String> sessionalDates;
    private List<Course> prerequisites;
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
        observers = new ArrayList<>();
    }

    /***
     * Alternate constructor for courses with no prerequisites
     * @param name is the name of the course
     * @param code is the course code
     * @param sessionalDates is an ArrayList of sessional dates for the course
     */
    public Course(String name, String code, ArrayList<String> sessionalDates) {
        this.name = name;
        this.code = code;
        this.sessionalDates = sessionalDates;
        this.prerequisites = new ArrayList<>();
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

    public void setPrerequisites(ArrayList<Course> prerequisites) {
        this.prerequisites = prerequisites;
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
}
