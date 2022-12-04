package com.planner;

import android.content.Context;
import android.widget.Toast;

public interface ViewActions {
    default void displayErrorNotification(Context callingActivity, String errorName) {
        Toast.makeText(callingActivity, errorName,
                Toast.LENGTH_SHORT).show();
    }
}
