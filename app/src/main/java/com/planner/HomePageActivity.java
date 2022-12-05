package com.planner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.planner.databinding.ActivityHomePageBinding;

public class HomePageActivity extends AppCompatActivity implements ViewActions {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // some example uses are commented here
        
//        MainActivity.courseDB.addCourse(new Course(
//                "Techniques of the Calculus of Several Variables I",
//                "MATB41",
//                new ArrayList<String>()));
//
//        MainActivity.courseDB.addCourse(new Course(
//                "Introduction to Computer Science II",
//                "CSCA48",
//                new ArrayList<String>()));
//
//        MainActivity.courseDB.addCourse(new Course(
//                "The Politics of Equality and Inequality in Canada",
//                "POLD55",
//                new ArrayList<String>()));
//
//        MainActivity.courseDB.editCourse(new Course(
//                "Introduction to Computer Science II",
//                "CSCA48",
//                new ArrayList<String>()), "MATB41");

//        String o = "";
//        for(int i = 0; i<MainActivity.courseDB.courses.size(); i++) {
//            if(i < MainActivity.courseDB.courses.size() - 1)
//                o = o + String.valueOf(MainActivity.courseDB.courses.get(i).getCode()) + ", ";
//            else
//                o = o + String.valueOf(MainActivity.courseDB.courses.get(i).getCode());
//        }
//        Log.e("courses in the database", o);

        ActivityHomePageBinding binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        Button courseListButton = (Button) findViewById(R.id.button5);
        Button courseTimelineButton = (Button) findViewById(R.id.button4);
        Button loginBackButton = (Button) findViewById(R.id.backbutton23);

        displayWelcomeText();

        // Setup Listeners
        courseListButton.setOnClickListener(view -> openCourseListPage(HomePageActivity.this));
        courseTimelineButton.setOnClickListener(view ->
                openCourseTimelineAddPage(HomePageActivity.this));
        loginBackButton.setOnClickListener(view -> openLoginPage(HomePageActivity.this));
    }

    private void displayWelcomeText() {
        TextView welcomeText = (TextView) findViewById(R.id.textView4);
        String name = MainActivity.currentUser.getName() == null ? ""
                : ": " + MainActivity.currentUser.getName();

        welcomeText.setText(String.format(getResources().getString(R.string.welcome_student), name));
    }
}