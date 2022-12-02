package com.planner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.planner.databinding.ActivityHomePageBinding;

public class HomePageActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityHomePageBinding binding;
    TextView welcomeTxt;
    String name, outputName;
    Button courseListButton;
    Button courseTimelineButton;
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

        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_page);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        welcomeTxt = (TextView) findViewById(R.id.textView4);
        courseListButton = (Button) findViewById(R.id.button5);
        courseTimelineButton = (Button) findViewById(R.id.button4);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        if(null != name){
            outputName = name;
            welcomeTxt.setText("Welcome " + outputName);
        }else{
            welcomeTxt.setText("Welcome");
        }

        courseListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(HomePageActivity.this, CourseList.class);
                startActivity(intent1);
                finish();
            }
        });

        courseTimelineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(HomePageActivity.this, CourseTimelineActivity.class);
                startActivity(intent2);
                finish();
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_page);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}