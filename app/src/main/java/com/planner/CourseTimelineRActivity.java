package com.planner;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.models.Timeline;
import com.models.course.CourseDatabase;
import com.models.users.UserDatabase;
import com.planner.databinding.ActivityCourseTimelineRBinding;


public class CourseTimelineRActivity extends AppCompatActivity implements ViewActions{

    private RecyclerView recyclerView;
    private final UserDatabase userDatabase = new UserDatabase();
    private final CourseDatabase c = CourseDatabase.getInstance();

    // Made the timeline
    Timeline t = Timeline.makeTimeline(MainActivity.currentUser, c);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCourseTimelineRBinding binding
                = ActivityCourseTimelineRBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Recycler view helps store the data in a tablelayout
        recyclerView = findViewById(R.id.recycler_view);

        setRecyclerView();
        setSupportActionBar(binding.toolbar);

//        backButton = (Button) findViewById(R.id.);
        // I can't seem to implement a back button from the table view screen
//        backButton.setOnClickListener(view -> {
//            userDatabase.editUser(MainActivity.currentUser, MainActivity.currentUser.getEmail());
//
//            openStudentHomepage(CourseTimelineRActivity.this);
//        });
    }

    // This connects the activity to the adapter that fills the table
    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SessionAdapter adapter = new SessionAdapter(this, t.getTimeline());
        recyclerView.setAdapter(adapter);
    }
}
