package com.planner;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.models.course.CourseDatabase;
import com.planner.databinding.ActivityCourseTimelineAddBinding;

import java.util.ArrayList;

public class CourseTimelineAddActivity extends AppCompatActivity implements ViewActions {

    private final CourseDatabase courseDB = CourseDatabase.getInstance();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_timeline_add);

        ActivityCourseTimelineAddBinding binding
                = ActivityCourseTimelineAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Button backButton = (Button) findViewById(R.id.button13);
        Button generateButton = (Button) findViewById(R.id.button16);

        // Setup Listeners
        backButton.setOnClickListener(view -> openStudentHomepage(CourseTimelineAddActivity.this));
        generateButton.setOnClickListener(view -> {
            // Get the checked entries in the list and add them all to selectedCodes
            SparseBooleanArray selected = listView.getCheckedItemPositions();
            ArrayList<String> selectedCodes = new ArrayList<>();

            for (int i = 0; i < selected.size(); i++) {
                int position = selected.keyAt(i);
                if (selected.valueAt(i)) {
                    selectedCodes.add((String)listView.getItemAtPosition(position));
                }
            }

            // Add all the selected codes to the courses the user wants to take
            MainActivity.currentUser.setCourseCodesPlanned(selectedCodes);
            openCourseTimelineRActivity(CourseTimelineAddActivity.this);
        });

        // Fill the noteListArrayList with every course code that has not been taken by the
        // current user
        ArrayList<String> noteListArrayList = new ArrayList<>();
        courseDB.getCourses().stream()
                .filter(course ->
                        !MainActivity.currentUser.getCourseCodesTaken().contains(course.getCode()))
                .forEach(course -> noteListArrayList.add(course.getCode()));

        String[] noteList = noteListArrayList.toArray(new String[0]);
        listView = findViewById(R.id.listviewx);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setClickable(true);

        // Turn noteList into an array so we can pass it into the adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice,
                noteList);
        listView.setAdapter(adapter);
    }
}