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
import com.planner.databinding.ActivityAddCourse2Binding;

import java.util.ArrayList;
import java.util.Arrays;


public class AddCourse2Activity extends AppCompatActivity {

    EditText nameInput;
    EditText course_codeInput;
    EditText ses_offerInput;
    EditText prereqInput;
    Button BackButton;
    Button DoneButton;
    String name, ses_offer, prerequisite, course_code;

    private AppBarConfiguration appBarConfiguration;
    private ActivityAddCourse2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CourseDatabase courseDB = CourseDatabase.getInstance();

        super.onCreate(savedInstanceState);

        binding = ActivityAddCourse2Binding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_add_course2);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        nameInput = (EditText) findViewById(R.id.editTextTextPersonName17);
        course_codeInput = (EditText) findViewById(R.id.editTextTextPersonName14);
        ses_offerInput = (EditText) findViewById(R.id.editTextTextPersonName15);
        prereqInput = (EditText) findViewById(R.id.editTextTextPersonName16);
        BackButton = (Button) findViewById(R.id.button7);
        DoneButton = (Button) findViewById(R.id.button8);

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCourse2Activity.this, AdminHomePageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        DoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                course_code = course_codeInput.getText().toString();
                name = nameInput.getText().toString();

                //have the different session offerings be split by a comma
                ses_offer = ses_offerInput.getText().toString();
                //creates an array of strings separated by comma
                //ses_offer.split(",") returns an array of strings, which is what the Course constructor needs?

                //uses split to create an array of session dates
                String[] listOfSessions = ses_offer.split(",");

                //creates an array list of strings directly from the array's contents
                ArrayList<String> sessionDates = new ArrayList<>(Arrays.asList(listOfSessions));

                //the below lines don't work as the input are Strings, as the Course constructor
                //only takes in ArrayList of Course

                //prerequisite = prereqInput.getText().toString();

                prerequisite = prereqInput.getText().toString();
                if(course_code.isEmpty() || ses_offer.isEmpty() || name.isEmpty()) {
                    ViewActions.displayErrorNotification(
                            AddCourse2Activity.this,
                            "Please Enter All Possible Fields");
                }else{
                    /* steps
                1. parse the course codes and place them in an array
                2. Create an ArrayList of Course object
                3. for each code in the array:
                  - check if the course is in the database (make a boolean function that ce)
                  - if yes, retrieve the course and add it to the list
                  - if no, don't add it and send a toast message (idk how to do that lol) that one
                  or more courses are not created
                */

                    String[] listOfPrerequisites = prerequisite.split(",");
                    ArrayList<Course> prerequisites = new ArrayList<>();
                    boolean allCoursesExist = true;

                    for(String code: listOfPrerequisites){
                        if(null == courseDB.getCourse(code) && !code.equals("")){
                            ViewActions.displayErrorNotification(AddCourse2Activity.this,
                                    "One or more of your prerequisite courses is not " +
                                            "registered in the database. Addition cancelled");
                            allCoursesExist = false;
                            break;
                        }
                    }

                    if(allCoursesExist){
                        for(String code: listOfPrerequisites){
                            Course c = courseDB.getCourse(code);
                            prerequisites.add(c);
                        }

                        Course new_course = new Course(course_code, name, sessionDates, prerequisites);
                        //have the different session offerings be split by a comma
                        ses_offer = ses_offerInput.getText().toString();
                        //ses_offer.split(",") returns an array of strings, which is what the Course constructor needs?

                        //the below lines don't work as the input are Strings, as the Course constructor
                        //only takes in ArrayList of Course

                        //prerequisite = prereqInput.getText().toString();

                        //Course new_course = new Course(course_code, name, ses_offer.split(","), prerequisites);


                        //the course is already in the database
                        if(courseDB.getCourse(course_code) != null){
                            ViewActions.displayErrorNotification(AddCourse2Activity.this,
                                    "Course already in database");
                        }

                        else if(courseDB.getCourse(course_code) == null) {
                            //add the course to the database
                            courseDB.addCourse(new_course);
                            ViewActions.displayErrorNotification(AddCourse2Activity.this,
                                    "Course Added");

                        }
                        Intent intent = new Intent(AddCourse2Activity.this, AdminHomePageActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }




            }

        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_add_course2);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}