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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.models.course.CourseDatabase;
import com.planner.databinding.ActivitySignUpBinding;
import com.models.course.Course;

import java.util.ArrayList;
import java.util.Arrays;

public class AddCourseActivity extends AppCompatActivity implements ViewActions {
    DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://b07-project-e5893-default-rtdb.firebaseio.com/");
    private AppBarConfiguration appBarConfiguration;
    private ActivitySignUpBinding binding;

    CourseDatabase courseDB = CourseDatabase.getInstance();

    String course_code, name, ses_offer, prerequisite;

    EditText nameInput;
    EditText course_codeInput;
    EditText ses_offerInput;
    EditText prereqInput;
    Button BackButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add_course);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.SignUpHeader);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_add_course);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        course_codeInput = findViewById(R.id.editTextTextPersonName2);
        ses_offerInput = findViewById(R.id.editTextTextPersonName3);
        nameInput = findViewById(R.id.editTextTextPersonName4);

        //need to implement way to read the prerequisites from the user input
        //the below line will only work if the course has ONE prerequisite

        prereqInput = findViewById(R.id.editTextTextPersonName5);


        BackButton = (Button) findViewById(R.id.AddCourseBackButton);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCourseActivity.this, AdminHomePageActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Button DoneButton = findViewById(R.id.DoneButton);
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
                String[] listOfSessions = ses_offer.split(", ");

                //creates an array list of strings directly from the array's contents
                ArrayList<String> sessionDates = new ArrayList<>(Arrays.asList(listOfSessions));

                //the below lines don't work as the input are Strings, as the Course constructor
                //only takes in ArrayList of Course

                //prerequisite = prereqInput.getText().toString();

                prerequisite = prereqInput.getText().toString();

                /* steps
                1. parse the course codes and place them in an array
                2. Create an ArrayList of Course object
                3. for each code in the array:
                  - check if the course is in the database (make a boolean function that ce)
                  - if yes, retrieve the course and add it to the list
                  - if no, don't add it and send a toast message (idk how to do that lol) that one
                  or more courses are not created
                */

                String[] listOfPrerequisites = prerequisite.split(", ");
                ArrayList<Course> prerequisites = new ArrayList<>();
                boolean allCoursesExist = true;

                for(String code: listOfPrerequisites){
                    if(courseDB.getCourse(code) == null){
                        displayErrorNotification(AddCourseActivity.this,
                                "One or more of your prerequisite courses is not "
                                        + "registered in the database. Addition cancelled");
                        allCoursesExist = false;
                        break;
                    }
                }

                if(allCoursesExist){
                    for(String code: listOfPrerequisites){
                        Course c = courseDB.getCourse(code);
                        prerequisites.add(c);
                    }
                }

                Course new_course = new Course(course_code, name, sessionDates, prerequisites);

                //have the different session offerings be split by a comma
                ses_offer = ses_offerInput.getText().toString();
                //ses_offer.split(",") returns an array of strings, which is what the Course constructor needs?

                //the below lines don't work as the input are Strings, as the Course constructor
                //only takes in ArrayList of Course

                //prerequisite = prereqInput.getText().toString();

                //Course new_course = new Course(course_code, name, ses_offer.split(","), prerequisites);
                if(course_code.isEmpty() || ses_offer.isEmpty() || name.isEmpty()) {
                    displayErrorNotification(AddCourseActivity.this,
                            "Please Enter All Possible Fields");
                }

                //the course is already in the database
                if(courseDB.getCourse(course_code) != null){
                    displayErrorNotification(AddCourseActivity.this,
                            "Course already in database");
                }

                else if(courseDB.getCourse(course_code) == null) {
                    //add the course to the database
                    courseDB.addCourse(new_course);
                }

                    //navigates back to the Home Page
                    Intent intent = new Intent(AddCourseActivity.this, AdminHomePageActivity.class);
                    startActivity(intent);
                }
        });
    }
}
