package com.models;

import android.util.Pair;

import com.models.course.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Session implements Comparable<Session>{
    private String season;
    private int year;
    private List<Course> sessionCourses;

    public static final String fall = "Fall";
    public static final String winter = "Winter";
    public static final String summer = "Summer";

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

    public boolean editCourseInList(Course toEdit, String name, String code,
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

    public static Pair<String, Integer> getCurrentSession() {
        return new Pair<>(fall, 2023);
    }

    /*
    public void moveToNextSession(){
        if(season.equals(winter))   {setSeason(summer);}
        if(season.equals(summer))   {setSeason(fall);}
        else{
            setSeason(winter);
            setYear(year+1);
        }
    }
     */

    public static Pair<String, Integer> moveToNextSession(Pair<String, Integer> sessionDate){
        String newSeason = sessionDate.first;
        int newYear = sessionDate.second;

        if(newSeason.equals(winter))   {newSeason = summer;}
        if(newSeason.equals(summer))   {newSeason = fall;}
        else{
            newSeason = winter;
            newYear++;
        }

        Pair<String, Integer> newSession = new Pair<>(newSeason, newYear);
        return newSession;
    }

    @Override
    public int compareTo(Session s) {
        int seasonNum1 = 0;
        int seasonNum2 = 0;

        if(season.equals(summer))   {seasonNum1 = 1;}
        if(season.equals(fall))     {seasonNum1 = 2;}
        if(s.season.equals(summer)) {seasonNum2 = 1;}
        if(s.season.equals(fall))   {seasonNum2 = 2;}

        int sessionNum1 = year + seasonNum1;
        int sessionNum2 = year + seasonNum2;

        if(sessionNum1 < sessionNum2)   {return -1;}
        if(sessionNum1 == sessionNum2)   {return 0;}
        else    {return 1;}
    }

    public boolean anyCoursesInSession(List<Course> listOfCourses){
        for(Course c: listOfCourses){
            if(sessionCourses.contains(c)){
                return true;
            }
        }
        return false;
    }
}
