package com.planner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.models.users.UserDatabase;
import com.planner.databinding.ActivityCourseListBinding;

import java.util.ArrayList;

public class CourseList extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityCourseListBinding binding;
    Button backButton;
    Button nextButton;

    // Listview
    ListView listView;

    //Data that is to be displayed on the list
    String[] noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCourseListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        noteList = MainActivity.currentUser.getCourseCodesTaken().toArray(new String[0]);

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

        nextButton = (Button) findViewById(R.id.button2);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseList.this, CourseListAdd.class);
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
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String code = (String) listView.getItemAtPosition(i);

                // update the current user's courseCodesTaken by removing the course
                ArrayList<String> newTaken = (ArrayList) MainActivity.currentUser.getCourseCodesTaken();
                newTaken.remove(code);
                MainActivity.currentUser.setCourseCodesTaken(newTaken);

                // edit the user in the database
                UserDatabase u = new UserDatabase("https://b07-project-e5893-default-rtdb.firebaseio.com/");
                u.editUser(MainActivity.currentUser, MainActivity.currentUser.getEmail());

                // update the noteList to remove that value. this is not ideal but its the easiest
                // way to do this. next time the page is loaded it will be gone though so its ok
                noteList[i] = "";
                // notify the adapter of the change
                adapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_course_list);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}