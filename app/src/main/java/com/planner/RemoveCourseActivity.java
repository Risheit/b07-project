package com.planner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.models.course.Course;
import com.models.course.CourseDatabase;
import com.planner.databinding.ActivityRemoveCourseBinding;

public class RemoveCourseActivity extends AppCompatActivity implements ViewActions {

    EditText courseToRemove;
    Button backButton;
    Button doneButton;
    CourseDatabase courseDB = CourseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRemoveCourseBinding binding
                = ActivityRemoveCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        backButton = (Button) findViewById(R.id.backButtonRemoveCourse);
        courseToRemove = (EditText) findViewById(R.id.editTextTextPersonName12);
        doneButton = (Button) findViewById(R.id.removeCourse2Button);

        // Setup Listeners
        backButton.setOnClickListener(view -> openAdminHomepage(RemoveCourseActivity.this));
        doneButton.setOnClickListener(view -> {
            String courseCode = courseToRemove.getText().toString();

            if (courseCode.isEmpty()){
                displayErrorNotification(RemoveCourseActivity.this,
                        "Please enter the course code");
            }


            Course course =  courseDB.getCourse(courseCode);
            if (course == null){
                displayErrorNotification(RemoveCourseActivity.this,
                        "Course not found in database");
            } else {
                course.getPrerequisites().forEach(c -> {
                    c.removeRequiredCourse(course);
                });
                course.obtainRequires().forEach(c -> {
                    c.getPrerequisites().remove(course);
                    courseDB.editCourse(c, c.getCode());
                });
                courseDB.removeCourse(courseCode);
                displayNotification(RemoveCourseActivity.this,
                        "Course removed");
                openAdminHomepage(RemoveCourseActivity.this);
            }
        });
    }
}