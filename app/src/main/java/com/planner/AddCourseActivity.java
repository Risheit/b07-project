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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.planner.databinding.ActivitySignUpBinding;
import com.presenters.Course;

import java.util.ArrayList;

public class AddCourseActivity extends AppCompatActivity {
    DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://b07-project-e5893-default-rtdb.firebaseio.com/");
    private AppBarConfiguration appBarConfiguration;
    private ActivitySignUpBinding binding;

    String course_code, name, ses_offer, prerequisite;

    EditText nameInput;
    EditText course_codeInput;
    EditText ses_offerInput;
    EditText prereqInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add_course);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.SignUpHeader);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_sign_up);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        course_codeInput = (EditText) findViewById(R.id.editTextTextPersonName2);
        ses_offerInput = (EditText) findViewById(R.id.editTextTextPersonName3);
        nameInput = (EditText) findViewById(R.id.editTextTextPersonName4);

        //need to implement way to read the prerequisites from the user input
        //the below line will only work if the course has ONE prerequisite

        prereqInput = (EditText) findViewById(R.id.editTextTextPersonName5);


        Button BackButton = findViewById(R.id.BackButton);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCourseActivity.this, HomePageActivity.class);
                startActivity(intent);
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
                //ses_offer.split(",") returns an array of strings, which is what the Course constructor needs?

                //the below lines don't work as the input are Strings, as the Course constructor
                //only takes in ArrayList of Course

                //prerequisite = prereqInput.getText().toString();

                //Course new_course = new Course(course_code, name, ses_offer.split(","), prerequisites);
                if(course_code.isEmpty() || ses_offer.isEmpty() || name.isEmpty()) {
                    Toast.makeText(AddCourseActivity.this, "Please Enter All Possible Fields", Toast.LENGTH_SHORT).show();
                } else {
                    //add the course to the database
                    //addCourse(new_course)

                    //navigates back to the Home Page
                    Intent intent = new Intent(AddCourseActivity.this, HomePageActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
