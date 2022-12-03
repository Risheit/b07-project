package com.models.course;

/***
 * Observer class only to be initiated after the prerequisite course has been already
 * and class that needs the prerequisite is also
 */
public class PreRequisiteObserver extends Observer {
    private final Course baseCourse; // no idea what to call this it's what requires the prerequisite
    private final int courseIndx;

    public PreRequisiteObserver(Course prereqCourse, Course baseCourse) {
        course = prereqCourse;
        course.registerObserver(this);
        this.baseCourse = baseCourse;
        courseIndx = baseCourse.getPrerequisites().indexOf(prereqCourse);
    }

    @Override
    public void update() {
        baseCourse.getPrerequisites().set(courseIndx, course);
        // this seems kind of redundant but i dont know
        // i guess the idea is that when the prerequisite course is edited
        // it keeps its reference (and so this observer knows what it is)
        // and then it just replaces the old course with the new one
        // but arraylist should already store a reference?????
    }
}
