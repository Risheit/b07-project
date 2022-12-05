package com.planner;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.GenericTypeIndicator;
import com.models.users.UserDatabase;
import com.planner.databinding.ActivityCourseListBinding;

import java.util.ArrayList;
import java.util.List;

public class CourseList extends AppCompatActivity implements ViewActions {

    private ListView listView;
    private String[] noteList; // Data that is to be displayed on the list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCourseListBinding binding = ActivityCourseListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        Button backButton = (Button) findViewById(R.id.button6);
        Button nextButton = (Button) findViewById(R.id.button2);
        listView = findViewById(R.id.listviewy);
        noteList = MainActivity.currentUser.getCourseCodesTaken().toArray(new String[0]);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                noteList
        );

        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            String code = (String) listView.getItemAtPosition(i);

            // Update the current user's courseCodesTaken by removing the course
            MainActivity.currentUser.removeTakenCourse(code);

            // Edit the user in the database
            UserDatabase userDatabase = new UserDatabase();
            userDatabase.editUser(MainActivity.currentUser, MainActivity.currentUser.getEmail());

            // Update the noteList to remove that value. this is not ideal but its the easiest
            // way to do this. next time the page is loaded it will be gone though so its ok
            noteList[i] = "";

            adapter.notifyDataSetChanged();
        });

        // Setup Listeners
        backButton.setOnClickListener(view -> openStudentHomepage(CourseList.this));
        nextButton.setOnClickListener(view -> openCourseListAddPage(CourseList.this));
    }
}