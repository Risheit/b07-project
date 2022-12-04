package com.planner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.models.course.Course;
import com.models.course.CourseDatabase;
import com.planner.databinding.ActivityRemoveCourseBinding;

public class RemoveCourseActivity extends AppCompatActivity implements ViewActions {

    private AppBarConfiguration appBarConfiguration;
    private ActivityRemoveCourseBinding binding;
    EditText courseToRemove;
    Button backButton;
    Button doneButton;
    CourseDatabase courseDB = CourseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRemoveCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_remove_course2);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        backButton = (Button) findViewById(R.id.backButtonRemoveCourse);
        courseToRemove = (EditText) findViewById(R.id.editTextTextPersonName12);
        doneButton = (Button) findViewById(R.id.removeCourse2Button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RemoveCourseActivity.this, AdminHomePageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseCode = courseToRemove.getText().toString();

                if(courseCode.isEmpty()){
                    displayErrorNotification(RemoveCourseActivity.this,
                            "Please enter the course code");
                }

                Course c =  courseDB.getCourse(courseCode);

                if(null == c){
                    displayErrorNotification(RemoveCourseActivity.this,
                            "Course not found in database");
                }

                else {
                    courseDB.removeCourse(courseCode);
                    displayErrorNotification(RemoveCourseActivity.this,
                            "Course removed");
                    Intent intent1 = new Intent(RemoveCourseActivity.this, AdminHomePageActivity.class);
                    startActivity(intent1);
                    finish();
                }

            }
        });



    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_remove_course2);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}