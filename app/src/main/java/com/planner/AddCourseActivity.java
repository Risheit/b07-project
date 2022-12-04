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
import com.planner.databinding.ActivityAddCourseBinding;

import java.util.ArrayList;
import java.util.Arrays;


public class AddCourseActivity extends AppCompatActivity implements ViewActions {
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;

    private EditText nameInput;
    private EditText course_codeInput;
    private EditText ses_offerInput;
    private EditText prereqInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CourseDatabase courseDB = CourseDatabase.getInstance();

        ActivityAddCourseBinding binding = ActivityAddCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);


        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_add_course2);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        nameInput = (EditText) findViewById(R.id.editTextTextPersonName17);
        course_codeInput = (EditText) findViewById(R.id.editTextTextPersonName14);
        ses_offerInput = (EditText) findViewById(R.id.editTextTextPersonName15);
        prereqInput = (EditText) findViewById(R.id.editTextTextPersonName16);
        Button backButton = (Button) findViewById(R.id.button7);
        Button doneButton = (Button) findViewById(R.id.button8);

        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(AddCourseActivity.this, AdminHomePageActivity.class);
            startActivity(intent);
            finish();
        });

        doneButton.setOnClickListener(view -> {
            String course_code = course_codeInput.getText().toString();
            String name = nameInput.getText().toString();

            //have the different session offerings be split by a comma
            String ses_offer = ses_offerInput.getText().toString();
            //creates an array of strings separated by comma
            //ses_offer.split(",") returns an array of strings, which is what the Course constructor needs?

            //uses split to create an array of session dates
            String[] listOfSessions = ses_offer.split(",");

            //creates an array list of strings directly from the array's contents
            ArrayList<String> sessionDates = new ArrayList<>(Arrays.asList(listOfSessions));

            //the below lines don't work as the input are Strings, as the Course constructor
            //only takes in ArrayList of Course

            //prerequisite = prereqInput.getText().toString();

            String prerequisite = prereqInput.getText().toString();
            if(course_code.isEmpty() || ses_offer.isEmpty() || name.isEmpty()) {
                displayErrorNotification(
                        AddCourseActivity.this,
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
                        displayErrorNotification(AddCourseActivity.this,
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
                        displayErrorNotification(AddCourseActivity.this,
                                "Course already in database");
                    }

                    else if(courseDB.getCourse(course_code) == null) {
                        //add the course to the database
                        courseDB.addCourse(new_course);
                        displayErrorNotification(AddCourseActivity.this,
                                "Course Added");

                    }
                    Intent intent = new Intent(AddCourseActivity.this, AdminHomePageActivity.class);
                    startActivity(intent);
                    finish();
                }
            }




        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void openAdminHomepage() {

    }
}