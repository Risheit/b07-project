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
import com.planner.databinding.ActivityEditCourseBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class EditCourseActivity extends AppCompatActivity implements ViewActions {

    private AppBarConfiguration appBarConfiguration;
    private EditText input1, input2, input3, input4, input5;
    private String courseInput, newCourseInput, titleInput, sessionInput, prereqInput;
    CourseDatabase courseDB = CourseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityEditCourseBinding binding = ActivityEditCourseBinding.inflate(getLayoutInflater());
        Button backButton = (Button) findViewById(R.id.button10);
        Button doneButton = (Button) findViewById(R.id.button11);

        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(
                this,
                R.id.nav_host_fragment_content_edit_course2
        );
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(
                this, navController,
                appBarConfiguration);

        input1 = (EditText) findViewById(R.id.editTextTextPersonName13);
        input2 = (EditText) findViewById(R.id.editTextTextPersonName18);
        input3 = (EditText) findViewById(R.id.editTextTextPersonName19);
        input4 = (EditText) findViewById(R.id.editTextTextPersonName20);
        input5 = (EditText) findViewById(R.id.editTextTextPersonName21);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditCourseActivity.this, AdminHomePageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        doneButton.setOnClickListener(view -> {
            courseInput = input1.getText().toString();
            newCourseInput = input2.getText().toString();
            titleInput = input3.getText().toString();
            sessionInput = input4.getText().toString();
            prereqInput = input5.getText().toString();

            if (courseInput.isEmpty() || newCourseInput.isEmpty()
                    || titleInput.isEmpty() || sessionInput.isEmpty()) {
                displayErrorNotification(EditCourseActivity.this,
                        "Please Enter All Fields");
            } else {
                ArrayList<String> listSessions = new ArrayList<>(
                        Arrays.asList(sessionInput.split(","))
                );
                String[] prereqArray = prereqInput.split(",");
                ArrayList<Course> prereqList = new ArrayList<>();
                boolean hasPrereq = true;
                boolean allCoursesExist = true;

                if(prereqArray[0].isEmpty()){
                    hasPrereq = false;
                }else{
                    for(String prereq : prereqArray){
                        if(null == courseDB.getCourse(prereq)){
                            displayErrorNotification(EditCourseActivity.this,
                                    "One or more of your prerequisite courses is not "
                                            + "registered in \" +\n"
                                            + "                                            \""
                                            + "the database. Addition cancelled");
                            allCoursesExist = false;
                            break;
                        }
                    }
                }

                if(allCoursesExist){
                    for(String prereq: prereqArray){
                        Course c = courseDB.getCourse(prereq);
                        prereqList.add(c);
                    }
                    //if course is not in database
                    if(null == courseDB.getCourse(courseInput)){
                        displayErrorNotification(EditCourseActivity.this,
                                "Course does not exist");
                    }else{
                        Course course = courseDB.getCourse(courseInput);
                        course.setCode(newCourseInput);
                        course.setName(titleInput);
                        course.setSessionalDates(listSessions);
                        courseDB.removeCourse(courseInput);

                        if(hasPrereq == false){
                            course.setPrerequisites(new ArrayList<>());
                        }else{
                            course.setPrerequisites(prereqList);
                        }
                        courseDB.addCourse(course);
                        displayErrorNotification(EditCourseActivity.this,
                                "Course edited");
                        Intent intent1 = new Intent(EditCourseActivity.this, AdminHomePageActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                }
            }


        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_edit_course2);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}