package com.planner;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.models.users.UserDatabase;
import com.planner.databinding.ActivityCourseListBinding;

import java.util.ArrayList;

public class CourseList extends AppCompatActivity implements ViewActions {

    Button backButton;
    Button nextButton;

    // Listview
    ListView listView;

    //Data that is to be displayed on the list
    String[] noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCourseListBinding binding = ActivityCourseListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);


        backButton = (Button) findViewById(R.id.button6);
        nextButton = (Button) findViewById(R.id.button2);
        listView = findViewById(R.id.listviewy);

        noteList = MainActivity.currentUser.getCourseCodesTaken().toArray(new String[0]);

        // Array Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                noteList);

        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            String code = (String) listView.getItemAtPosition(i);

            // update the current user's courseCodesTaken by removing the course
            ArrayList<String> newTaken = (ArrayList) MainActivity.currentUser.getCourseCodesTaken();
            newTaken.remove(code);
            MainActivity.currentUser.setCourseCodesTaken(newTaken);

            // edit the user in the database
            UserDatabase u = new UserDatabase();
            u.editUser(MainActivity.currentUser, MainActivity.currentUser.getEmail());

            // update the noteList to remove that value. this is not ideal but its the easiest
            // way to do this. next time the page is loaded it will be gone though so its ok
            noteList[i] = "";
            // notify the adapter of the change
            adapter.notifyDataSetChanged();
        });

        // Setup Listeners
        backButton.setOnClickListener(view -> openStudentHomepage(CourseList.this));
        nextButton.setOnClickListener(view -> openCourseListAddPage(CourseList.this));
    }
}