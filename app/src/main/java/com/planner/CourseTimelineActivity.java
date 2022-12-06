package com.planner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.models.users.UserDatabase;
import com.planner.databinding.ActivityCourseTimelineBinding;

public class CourseTimelineActivity extends AppCompatActivity implements ViewActions {

    // Data to be displayed in the list
    String[] sesList = {"Winter 2022", "Summer 2023"};
    String[] codeList = {"CSCB07", "MATA31"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCourseTimelineBinding binding
                = ActivityCourseTimelineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        // Syncing to the xml file
        ListView sessions = findViewById(R.id.listview_ses);
        ListView codes = findViewById(R.id.listview_code);

        // Adapters
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                sesList
        );

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                codeList
        );

        sessions.setAdapter(adapter1);
        codes.setAdapter(adapter2);
        Button backButton = (Button) findViewById(R.id.button9);

        // Setup Listeners
        backButton.setOnClickListener(view -> {
            /*
            Note: It's updated here instead of when the page is generated because it would cause
            crashes when the checklist caused the user to actually update in the database
            (i.e. the list of planned courses actually changed). I couldn't figure out why
            but this solution works
             */
            UserDatabase userDatabase = new UserDatabase();
            userDatabase.editUser(MainActivity.currentUser, MainActivity.currentUser.getEmail());
            openStudentHomepage(CourseTimelineActivity.this);
        });
    }
}