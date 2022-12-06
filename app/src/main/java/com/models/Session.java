package com.models;

import androidx.annotation.NonNull;

import com.models.course.Course;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    /* old hardcoded implementation in case the dynamic one doesn't work
    public static Pair<String, Integer> getCurrentSession() {
        return new Pair<>(fall, 2023);
    }*/

    // dynamic implementation that updates with the actual date
    public static Pair<String, Integer> getCurrentSession() {
        Calendar today = new GregorianCalendar();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH);
        String season = null; // this will represent the section, not the actual season

        // if its between january or april
        if(month >= Calendar.JANUARY &&  month <= Calendar.APRIL)
            season = winter;
        else if(month >= Calendar.MAY && month <= Calendar.AUGUST)
            season = summer;
        else if(month >= Calendar.SEPTEMBER && month <= Calendar.DECEMBER)
            season = fall;

        return new Pair<>(season, year);
    }

    public static Pair<String, Integer> moveToNextSession(Pair<String, Integer> sessionDate){
        String newSeason = sessionDate.first;
        int newYear = sessionDate.second;

        switch (newSeason) {
            case winter:
                newSeason = summer;
                break;
            case summer:
                newSeason = fall;
                break;
            default:
                newSeason = winter;
                newYear++;
                break;
        }

        return new Pair<>(newSeason, newYear);
    }

    @Override
    public int compareTo(Session s) {
        int seasonNum1 = 0;
        int seasonNum2 = 0;

        if(season.equals(summer))   {seasonNum1 = 1;}
        if(season.equals(fall))     {seasonNum1 = 2;}
        if(s.season.equals(summer)) {seasonNum2 = 1;}
        if(s.season.equals(fall))   {seasonNum2 = 2;}

        int sessionNum1 = year * 10 + seasonNum1;
        int sessionNum2 = s.year * 10 + seasonNum2;

        return Integer.compare(sessionNum1, sessionNum2);
    }

    public boolean anyCoursesInSession(List<Course> listOfCourses){
        for(Course c: listOfCourses){
            if(sessionCourses.contains(c)){
                return true;
            }
        }
        return false;
    }

    public String presentCourses(){
        String s = "";

        if(sessionCourses.size() > 0) {
            s = sessionCourses.get(0).getCode();
            for (int i = 1; i < sessionCourses.size(); i++) {
                s = s.concat(", " + sessionCourses.get(i).getCode());
            }
        }
        return s;
    }

    public static List<String> getValidSessionDatesFromString(String sessionsOffered) {
        List<String> validSeasons = Arrays.asList(Session.summer.toLowerCase(),
                Session.winter.toLowerCase(), Session.fall.toLowerCase());

        List<String> sessions = Arrays.stream(sessionsOffered.split(","))
                .map(String::toLowerCase)
                .map(String::trim)
                .collect(Collectors.toList());

        return sessions.stream()
                .filter(validSeasons::contains)
                .map(season -> {
                    if (Session.fall.toLowerCase().equals(season))
                        return Session.fall;
                    if (Session.winter.toLowerCase().equals(season))
                        return Session.winter;
                    if (Session.summer.toLowerCase().equals(season))
                        return Session.summer;
                    return Session.fall;
                })
                .collect(Collectors.toList());
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + season.hashCode();
        result = 31 * result + Integer.hashCode(year);
        result = 31 * result + sessionCourses.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Session) || this.hashCode() != other.hashCode()) {
            return false;
        }

        Session session = (Session) other;
        return (Objects.equals(this.season, session.season)
                && this.year == session.year
                && Objects.equals(this.sessionCourses, session.sessionCourses)
                );
    }

    @NonNull
    @Override
    public String toString() {
        return season + " " + year + ": " + sessionCourses;
    }
}
