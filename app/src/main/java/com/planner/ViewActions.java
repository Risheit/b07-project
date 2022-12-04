package com.planner;

import android.content.Context;
import android.widget.Toast;

public class ViewActions {
    public static void displayErrorNotification(Context callingActivity, String errorName) {
        Toast.makeText(callingActivity, errorName,
                Toast.LENGTH_SHORT).show();
    }
}
