package com.planner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.models.course.CourseDatabase;
import com.planner.databinding.ActivityAddCourseBinding;
import com.presenters.AddCoursePresenter;


public class AddCourseActivity extends AppCompatActivity implements ViewActions {

    private AppBarConfiguration appBarConfiguration;
    private NavController navController;

    private EditText nameInput;
    private EditText courseCodeInput;
    private EditText sessionsOfferedInput;
    private EditText prereqInput;

    private AddCoursePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new AddCoursePresenter(this, CourseDatabase.getInstance());

        ActivityAddCourseBinding binding = ActivityAddCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_content_add_course2);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController,
                appBarConfiguration);

        nameInput = (EditText) findViewById(R.id.editTextTextPersonName17);
        courseCodeInput = (EditText) findViewById(R.id.editTextTextPersonName14);
        sessionsOfferedInput = (EditText) findViewById(R.id.editTextTextPersonName15);
        prereqInput = (EditText) findViewById(R.id.editTextTextPersonName16);
        Button backButton = (Button) findViewById(R.id.button7);
        Button doneButton = (Button) findViewById(R.id.button8);

        // Setup Listeners
        backButton.setOnClickListener(view -> presenter.onBackButtonClicked());
        doneButton.setOnClickListener(view -> presenter.onDoneButtonClicked());
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public EditText getNameInput() {
        return nameInput;
    }

    public EditText getCourseCodeInput() {
        return courseCodeInput;
    }

    public EditText getSessionsOfferedInput() {
        return sessionsOfferedInput;
    }

    public EditText getPrereqInput() {
        return prereqInput;
    }
}