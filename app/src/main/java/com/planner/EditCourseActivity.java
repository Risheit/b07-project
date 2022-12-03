package com.planner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class EditCourseActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityEditCourseBinding binding;
    Button backButton;
    Button doneButton;
    EditText input1, input2, input3, input4, input5;
    String courseInput, newCourseInput, titleInput, sessionInput, prereqInput;
    CourseDatabase courseDB = CourseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityEditCourseBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_edit_course2);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        backButton = (Button) findViewById(R.id.button10);
        doneButton = (Button) findViewById(R.id.button11);
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

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseInput = input1.getText().toString();
                newCourseInput = input2.getText().toString();
                titleInput = input3.getText().toString();
                sessionInput = input4.getText().toString();
                prereqInput = input5.getText().toString();

                if(courseInput.isEmpty() || newCourseInput.isEmpty() || titleInput.isEmpty() || sessionInput.isEmpty()){
                    Toast.makeText(EditCourseActivity.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                }else{
                    String [] list1 = sessionInput.split(",");
                    ArrayList<String> listSessions = new ArrayList<>(Arrays.asList(list1));
                    String [] list2 = prereqInput.split(",");
                    ArrayList<Course> listPrereq = new ArrayList<>();

                    boolean hasPrereq = true;
                    boolean allCoursesExist = true;
                    if(list2[0].isEmpty()){
                        hasPrereq = false;
                    }else{
                        for(String prereq : list2){
                            if(null == courseDB.getCourse(prereq)){
                                Toast.makeText(EditCourseActivity.this, "One or more of your prerequisite courses is not registered in \" +\n" +
                                        "                                            \"the database. Addition cancelled", Toast.LENGTH_SHORT).show();
                                allCoursesExist = false;
                                break;
                            }
                        }
                    }

                    if(allCoursesExist){
                        for(String prereq: list2){
                            Course c = courseDB.getCourse(prereq);
                            listPrereq.add(c);
                        }
                        //if course is not in database
                        if(null == courseDB.getCourse(courseInput)){
                            Toast.makeText(EditCourseActivity.this, "Course does not exist", Toast.LENGTH_SHORT).show();
                        }else{
                            Course course = courseDB.getCourse(courseInput);
                            course.setCode(newCourseInput);
                            course.setName(titleInput);
                            course.setSessionalDates(listSessions);
                            courseDB.removeCourse(courseInput);

                            if(hasPrereq == false){
                                course.setPrerequisites(new ArrayList<>());
                            }else{
                                course.setPrerequisites(listPrereq);
                            }
                            courseDB.addCourse(course);
                            Toast.makeText(EditCourseActivity.this, "Course edited", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(EditCourseActivity.this, AdminHomePageActivity.class);
                            startActivity(intent1);
                            finish();
                        }
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