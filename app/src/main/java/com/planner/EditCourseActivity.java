package com.planner;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.planner.databinding.ActivityEditCourseBinding;
import com.presenters.Course;

import java.util.ArrayList;
import java.util.Arrays;

public class EditCourseActivity extends AppCompatActivity {
    DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://b07-project-e5893-default-rtdb.firebaseio.com/");
    private ActivityEditCourseBinding binding;

    private String courseKey;
    private String newKey;
    private String newName;
    private ArrayList<String> newSession;
    private ArrayList<Course> newPrerequisite;
    private Button searchButton;
    private EditText courseKeyField;
    private EditText newKeyField;
    private EditText newNameField;
    private EditText newSessionField;
    private EditText newPrerequisiteField;
    private Course newCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setContentView(R.layout.content_edit_course);

        courseKeyField = (EditText) findViewById(R.id.oldIDField);
        searchButton = (Button) findViewById(R.id.editCourseButton);
        newKeyField = (EditText) findViewById(R.id.newIDField);
        newNameField = (EditText) findViewById(R.id.newNameField);
        newSessionField = (EditText) findViewById(R.id.newSessionField);
        newPrerequisiteField = (EditText) findViewById(R.id.newPrerequisiteField);

        // get course by key method
        searchButton.setOnClickListener(view -> {
            courseKey = courseKeyField.getText().toString();
            newKey = newKeyField.getText().toString();
            newName = newNameField.getText().toString();
            newSession = (ArrayList<String>)
                    Arrays.asList(newSessionField.getText().toString().split(" "));
            newPrerequisite = new ArrayList<>();
            String[] preRequisiteList = newPrerequisiteField.getText().toString().split(" ");
            for (String course : preRequisiteList) {
                newPrerequisite.add(MainActivity.courseDB.getCourse(course));
            }
            newCourse = new Course(newName, newKey, newSession, newPrerequisite);

            if (MainActivity.courseDB.getCourse(courseKey) == null) {
                Toast.makeText(EditCourseActivity.this, "Course Key Not Found", Toast.LENGTH_SHORT).show();
            } else {
                Course c = MainActivity.courseDB.getCourse(courseKey);
                c.copyCourseData(newCourse);
                MainActivity.courseDB.editCourse(c, courseKey);
            }
        });
    }
}