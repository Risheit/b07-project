package com.planner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public interface ViewActions {
    default void displayNotification(Context callingActivity, String errorName) {
        Toast.makeText(callingActivity, errorName,
                Toast.LENGTH_SHORT).show();
    }

    default void displayErrorNotification(Context callingActivity, String errorName) {
        displayNotification(callingActivity, errorName);
    }

    default void openStudentHomepage(Activity callingActivity, String studentName) {
        Intent intent = new Intent(callingActivity, HomePageActivity.class);
        intent.putExtra("name", studentName);
        callingActivity.startActivity(intent);
        callingActivity.finish();
    }

    default void openAdminHomepage(Activity callingActivity, String adminName) {
        Intent intent = new Intent(callingActivity, AdminHomePageActivity.class);
        intent.putExtra("name", adminName);
        callingActivity.startActivity(intent);
        callingActivity.finish();
    }

    default void openSignUpPage(Activity callingActivity) {
        callingActivity.startActivity(new Intent(callingActivity, SignUpActivity.class));
        callingActivity.finish();
    }

    default void openLoginPage(Activity callingActivity) {
        Intent intent1 = new Intent(callingActivity, MainActivity.class);
        callingActivity.startActivity(intent1);
        callingActivity.finish();
    }
}
