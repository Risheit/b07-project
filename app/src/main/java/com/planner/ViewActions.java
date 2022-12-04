package com.planner;

import android.content.Context;
import android.widget.Toast;

public abstract class ViewActions {
    public static void displayErrorNotification(Context callingActivity, String errorName) {
        Toast.makeText(callingActivity, errorName,
                Toast.LENGTH_SHORT).show();
    }
}
