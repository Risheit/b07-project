package com.planner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.planner.databinding.ActivityCourseListBinding;

import java.util.ArrayList;
import java.util.HashSet;

public class CourseList extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityCourseListBinding binding;
    Button backButton;

    // Listview
    ListView listView;

    ArrayList<String> a = new ArrayList<String>(MainActivity.currentUser.getCourseCodesTaken());

    //Data that is to be displayed on the list
    //modify so that it displays the list of courses that the student has already taken
    String[] noteList = a.toArray(new String[0]);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCourseListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        final NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_course_list);
        final NavController navController = navHostFragment.getNavController();
/*
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_course_list);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

 */
        backButton = (Button) findViewById(R.id.button6);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseList.this, HomePageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        listView = findViewById(R.id.listviewy);

        // Array Adapter
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                noteList);

        listView.setAdapter(adapter);



    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_course_list);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}