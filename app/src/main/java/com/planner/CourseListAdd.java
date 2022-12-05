package com.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.models.course.CourseDatabase;
import com.models.users.UserDatabase;
import com.planner.databinding.ActivityCourseListAddBinding;

import java.util.ArrayList;
import java.util.List;

public class CourseListAdd extends AppCompatActivity implements ViewActions {

    private final CourseDatabase courseDB = CourseDatabase.getInstance();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCourseListAddBinding binding =
                ActivityCourseListAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Fill the noteListArrayList with every course code that has not
        // been taken by the current user
        ArrayList<String> noteList = new ArrayList<>();
        courseDB.getCourses().stream()
                .filter(course ->
                        !MainActivity.currentUser.getCourseCodesTaken().contains(course.getCode()))
                .forEach(course -> noteList.add(course.getCode()));

        // Then turn that into an array so we can pass it into the adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                noteList.toArray(new String[0]));

        listView = findViewById(R.id.listviewz);
        listView.setAdapter(adapter);
        listView.setClickable(true);

        Button backButton = (Button) findViewById(R.id.button);

        // Setup Listeners
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            String code = (String) listView.getItemAtPosition(i);

            // Update the current user's courseCodesTaken with the new course code
            List<String> newTaken = MainActivity.currentUser.getCourseCodesTaken();
            newTaken.add(code);
            MainActivity.currentUser.setCourseCodesTaken(newTaken);

            // Edit the user in the database
            UserDatabase u = new UserDatabase();
            u.editUser(MainActivity.currentUser, MainActivity.currentUser.getEmail());

            // Go back to CourseList.java having now updated the current user
            openCourseListPage(CourseListAdd.this);
        });
        backButton.setOnClickListener(view -> openCourseListPage(CourseListAdd.this));
    }
}