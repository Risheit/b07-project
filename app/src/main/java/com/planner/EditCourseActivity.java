package com.planner;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.models.course.Course;
import com.models.course.CourseDatabase;
import com.planner.databinding.ActivityEditCourseBinding;

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
    private Button editButton;
    private EditText courseKeyField;
    private EditText newKeyField;
    private EditText newNameField;
    private EditText newSessionField;
    private EditText newPrerequisiteField;
    private Course newCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CourseDatabase cd = CourseDatabase.getInstance();
        super.onCreate(savedInstanceState);
        binding = ActivityEditCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setContentView(R.layout.content_edit_course);

        courseKeyField = (EditText) findViewById(R.id.oldCodeField);
        editButton = (Button) findViewById(R.id.editCourseButton);
        newKeyField = (EditText) findViewById(R.id.newCodeField);
        newNameField = (EditText) findViewById(R.id.newNameField);
        newSessionField = (EditText) findViewById(R.id.newSessionField);
        newPrerequisiteField = (EditText) findViewById(R.id.newPrerequisiteField);

        // get course by key method
        editButton.setOnClickListener(view -> {
            System.out.println("edit button pressed!");
            courseKey = courseKeyField.getText().toString();
            newKey = newKeyField.getText().toString();
            newName = newNameField.getText().toString();
            newSession = new ArrayList<String>(
                    Arrays.asList(newSessionField.getText().toString().split(",")));
            newPrerequisite = new ArrayList<>();
            String[] preRequisiteList = newPrerequisiteField.getText().toString().split(" ");
            for (String course : preRequisiteList) {
                newPrerequisite.add(cd.getCourse(course));
            }
            newCourse = new Course(newName, newKey, newSession, newPrerequisite);

            if (cd.getCourse(courseKey) == null) {
                Toast.makeText(EditCourseActivity.this, "Course Key Not Found", Toast.LENGTH_SHORT).show();
            } else {
                Course c = cd.getCourse(courseKey);
                c.copyCourseData(newCourse);
                cd.editCourse(c, courseKey);
            }
        });
    }
}