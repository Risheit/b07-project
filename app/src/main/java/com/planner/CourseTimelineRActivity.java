package com.planner;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.models.Session;
import com.models.Timeline;
import com.models.course.CourseDatabase;
import com.models.users.UserDatabase;
import com.planner.databinding.ActivityCourseTimelineRBinding;

import java.util.List;


public class CourseTimelineRActivity extends AppCompatActivity implements ViewActions{

    private AppBarConfiguration appBarConfiguration;
    private ActivityCourseTimelineRBinding binding;
    Button backButton;
    RecyclerView recyclerView;
    SessionAdapter adapter;
    UserDatabase u = new UserDatabase();
    CourseDatabase c = CourseDatabase.getInstance();

    //made the timeline
    Timeline t = Timeline.makeTimeline(MainActivity.currentUser, c);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourseTimelineRBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //recycler view helps store the data in a tablelayout
        recyclerView = findViewById(R.id.recycler_view);

        setRecyclerView();
        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_course_timeline);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        //backButton = (Button) findViewById(R.id.);
        //I can't seem to implement a back button from the table view screen 
        backButton.setOnClickListener(view -> {
            u.editUser(MainActivity.currentUser, MainActivity.currentUser.getEmail());

            Intent intent = new Intent(CourseTimelineRActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });

    }

    //this connects the activity to the adapter that fills the table
    private void setRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SessionAdapter(this, t.getTimeline());
        recyclerView.setAdapter(adapter);
    }

    //I'm kinda lost on the navigation part so if anyone can fix... thanks!
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_course_timeline);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
