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
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.models.users.UserDatabase;
import com.planner.databinding.ActivityCourseTimelineBinding;

public class CourseTimelineActivity extends AppCompatActivity implements ViewActions {

    private AppBarConfiguration appBarConfiguration;
    private ActivityCourseTimelineBinding binding;
    Button backButton;

    // Listview for Sessions
    ListView listViewSes;
    // Listview for Course codes
    ListView listViewCode;

    // Data to be displayed in the list
    String[] sesList = {"Winter 2022", "Summer 2023"};
    String[] codeList = {"CSCB07", "MATA31"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCourseTimelineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // syncing to the xml file
        listViewSes = findViewById(R.id.listview_ses);
        listViewCode = findViewById(R.id.listview_code);

        // Adapters
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                sesList
        );

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                codeList
        );

        listViewSes.setAdapter(adapter1);
        listViewCode.setAdapter(adapter2);

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_course_timeline);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        backButton = (Button) findViewById(R.id.button9);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update the database
                /*
                note: its updated here instead of when the page is generated because it would cause
                crashes when the checklist caused the user to actually update in the database
                (i.e. the list of planned courses actually changed). i couldnt figure out why
                but this solution works
                 */
                UserDatabase u = new UserDatabase();
                u.editUser(MainActivity.currentUser, MainActivity.currentUser.getEmail());

                Intent intent = new Intent(CourseTimelineActivity.this, HomePageActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_course_timeline);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}