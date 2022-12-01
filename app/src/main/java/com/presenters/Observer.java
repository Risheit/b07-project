package com.presenters;

import com.presenters.Course;

public abstract class Observer {
    Course course;
    public abstract void update();
}
