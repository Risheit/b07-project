package com.planner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public interface ViewActions {
    default void displayNotification(Context caller, String errorName) {
        Toast.makeText(caller, errorName,
                Toast.LENGTH_SHORT).show();
    }

    default void displayErrorNotification(Context caller, String errorName) {
        displayNotification(caller, errorName);
    }

    default void openStudentHomepage(Activity caller) {
        Intent intent = new Intent(caller, HomePageActivity.class);
        caller.startActivity(intent);
        caller.finish();
    }

    default void openAdminHomepage(Activity caller) {
        Intent intent = new Intent(caller, AdminHomePageActivity.class);
        caller.startActivity(intent);
        caller.finish();
    }

    default void openSignUpPage(Activity caller) {
        caller.startActivity(new Intent(caller, SignUpActivity.class));
        caller.finish();
    }

    default void openLoginPage(Activity caller) {
        Intent intent1 = new Intent(caller, MainActivity.class);
        caller.startActivity(intent1);
        caller.finish();
    }

    default void openAddCoursePage(Activity caller) {
        Intent intent = new Intent(caller, AddCourseActivity.class);
        caller.startActivity(intent);
        caller.finish();
    }

    default void openRemoveCoursePage(Activity caller) {
        Intent intent = new Intent(caller, RemoveCourseActivity.class);
        caller.startActivity(intent);
        caller.finish();
    }

    default void openEditCoursePage(Activity caller) {
        Intent intent = new Intent(caller, EditCourseActivity.class);
        caller.startActivity(intent);
        caller.finish();
    }
}
