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

import com.planner.databinding.ActivityAdminHomePageBinding;

public class AdminHomePageActivity extends AppCompatActivity implements ViewActions {

    private AppBarConfiguration appBarConfiguration;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityAdminHomePageBinding binding = ActivityAdminHomePageBinding.inflate(
                getLayoutInflater()
        );
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_admin_home_page);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        Button addCourseButton = (Button) findViewById(R.id.AddCourseButton);
        Button deleteCourseButton = (Button) findViewById(R.id.DeleteCourseButton);
        Button editCourseButton = (Button) findViewById(R.id.button12);
        Button signOutButton = (Button) findViewById(R.id.button13);

        displayWelcomeText();

        // Setup Listeners
        addCourseButton.setOnClickListener(view -> openAddCoursePage(
                AdminHomePageActivity.this));
        deleteCourseButton.setOnClickListener(view -> openRemoveCoursePage(
                AdminHomePageActivity.this));
        //editCourseButton.setOnClickListener(view -> openEditCoursePage(
         //       AdminHomePageActivity.this));
        signOutButton.setOnClickListener(view -> openLoginPage(AdminHomePageActivity.this));

        editCourseButton.setOnClickListener(view -> openEditCoursePage(AdminHomePageActivity.this));
    }


    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void displayWelcomeText() {
        TextView welcomeText = (TextView) findViewById(R.id.textView14);
        String name = MainActivity.currentUser.getName() == null ? ""
                : ": " + MainActivity.currentUser.getName();

        welcomeText.setText(String.format(getResources().getString(R.string.welcome_admin), name));
    }
}