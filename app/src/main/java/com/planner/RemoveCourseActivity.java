package com.planner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.models.course.CourseDatabase;
import com.planner.databinding.ActivityRemoveCourseBinding;
import com.models.course.Course;

public class RemoveCourseActivity extends AppCompatActivity implements ViewActions {

    CourseDatabase courseDB = CourseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_remove_course);

        com.planner.databinding.ActivityRemoveCourseBinding binding = ActivityRemoveCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_course_list);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        EditText courseToRemove = findViewById(R.id.CourseToRemove);
        Button back = findViewById(R.id.BackButtonRemove);

        back.setOnClickListener(view -> {
            Intent intent = new Intent(RemoveCourseActivity.this, AdminHomePageActivity.class);
            startActivity(intent);
            finish();
        });
        Button DoneButton = findViewById(R.id.RemoveCourseButton);
        DoneButton.setOnClickListener(view -> {
            String courseCode = courseToRemove.getText().toString();

            if(courseCode.isEmpty()){
                displayErrorNotification(RemoveCourseActivity.this,
                        "Please enter the course code");
            }

            Course c =  courseDB.getCourse(courseCode);

            if(c == null){
                displayErrorNotification(RemoveCourseActivity.this,
                        "Course not found in database");
            }

            else {
                courseDB.removeCourse(courseCode);
            }

            Intent intent1 = new Intent(RemoveCourseActivity.this, AdminHomePageActivity.class);
            startActivity(intent1);
        });
    }
}
