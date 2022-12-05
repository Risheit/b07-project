package com.planner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.planner.databinding.ActivityAdminHomePageBinding;

public class AdminHomePageActivity extends AppCompatActivity implements ViewActions {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityAdminHomePageBinding binding = ActivityAdminHomePageBinding.inflate(
                getLayoutInflater()
        );
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        Button addCourseButton = (Button) findViewById(R.id.AddCourseButton);
        Button deleteCourseButton = (Button) findViewById(R.id.DeleteCourseButton);
        Button editCourseButton = (Button) findViewById(R.id.button12);
        Button signOutButton = (Button) findViewById(R.id.button13);

        displayWelcomeText();

        // Setup Listeners
        addCourseButton.setOnClickListener(view -> openAddCoursePage(
                AdminHomePageActivity.this));
        deleteCourseButton.setOnClickListener(view -> openRemoveCoursePage(
                AdminHomePageActivity.this));
        signOutButton.setOnClickListener(view -> openLoginPage(AdminHomePageActivity.this));
        editCourseButton.setOnClickListener(view -> openEditCoursePage(AdminHomePageActivity.this));
    }

    private void displayWelcomeText() {
        TextView welcomeText = (TextView) findViewById(R.id.textView14);
        String name = MainActivity.currentUser.getName() == null ? ""
                : ": " + MainActivity.currentUser.getName();

        welcomeText.setText(String.format(getResources().getString(R.string.welcome_admin), name));
    }
}