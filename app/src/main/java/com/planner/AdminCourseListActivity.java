package com.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.models.course.CourseDatabase;
import com.planner.databinding.ActivityCourseListAddBinding;

import java.util.ArrayList;

public class AdminCourseListActivity extends AppCompatActivity implements ViewActions {

    private final CourseDatabase courseDB = CourseDatabase.getInstance();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_course_list);

        getSupportActionBar().hide();

        Button backButton = (Button) findViewById(R.id.button14);
        backButton.setOnClickListener(view -> openAdminHomepage(AdminCourseListActivity.this));

        // Fill the noteList with every course code
        ArrayList<String> noteList = new ArrayList<>();
        courseDB.getCourses().stream()
                .forEach(course -> noteList.add(course.getCode()));

        // Turn it into a string array when we pass it into the adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                noteList.toArray(new String[0]));

        listView = findViewById(R.id.listviewk);
        listView.setAdapter(adapter);
    }
}