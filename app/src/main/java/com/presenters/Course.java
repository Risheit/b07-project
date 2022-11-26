package com.presenters;

import java.util.ArrayList;

public class Course {
    private String name;
    private String code;
    private ArrayList<String> sessionalDates;
    private ArrayList<Course> prerequisites;

    /***
     * Default constructor for Course object
     * @param name is the name of the course
     * @param code is the course code
     * @param sessionalDates is an ArrayList of sessional dates for the course
     * @param prerequisites is an ArrayList of courses that are the prerequisites
     */
    public Course(String name, String code, ArrayList<String> sessionalDates, ArrayList<Course> prerequisites) {
        this.name = name;
        this.code = code;
        this.sessionalDates = sessionalDates;
        this.prerequisites = prerequisites;
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

    public ArrayList<String> getSessionalDates() {
        return sessionalDates;
    }

    public void setSessionalDates(ArrayList<String> sessionalDates) {
        this.sessionalDates = sessionalDates;
    }

    public ArrayList<Course> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(ArrayList<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }
}
