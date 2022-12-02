package com.models;

import com.models.course.Course;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private String season;
    private int year;
    private List<Course> sessionCourses;

    public Session(String season, int year, List<Course> sessionCourses){
        this.season = season;
        this.year = year;
        this.sessionCourses = sessionCourses;
    }

    public String getSeason() {
        return season;
    }

    public int getYear() {
        return year;
    }

    public List<Course> getSessionCourses() {
        return sessionCourses;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setSessionCourses(List<Course> sessionCourses) {
        this.sessionCourses = sessionCourses;
    }

    public boolean addToCourseList(Course c){
        if(!(sessionCourses.contains(c))){
            sessionCourses.add(c);
            return true;
        }
        return false;
    }

    public boolean removeFromList(Course c){
        if(sessionCourses.contains(c)){
            sessionCourses.remove(c);
            return true;
        }
        return false;
    }

    public boolean replaceCourse(Course toReplace, Course replacement){
        if(sessionCourses.contains(toReplace)){
            sessionCourses.set(sessionCourses.indexOf(toReplace), replacement);
            return true;
        }
        return false;
    }

    public boolean editCourseinList(Course toEdit, String name, String code,
                                    ArrayList<String> sessionalDates,
                                    ArrayList<Course> prerequisites){

        if(sessionCourses.contains(toEdit)){
            sessionCourses.get(sessionCourses.indexOf(toEdit)).setCode(code);
            sessionCourses.get(sessionCourses.indexOf(toEdit)).setName(name);
            sessionCourses.get(sessionCourses.indexOf(toEdit)).setSessionalDates(sessionalDates);
            sessionCourses.get(sessionCourses.indexOf(toEdit)).setPrerequisites(prerequisites);
            return true;
        }
        return false;
    }

    public void clearSessionList(){
        sessionCourses.clear();
    }

    //public static (String, String) getCurrentSession(){}
}
