package com.planner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.models.course.CourseDatabase;
import com.planner.databinding.ActivityAddCourseBinding;
import com.presenters.AddCoursePresenter;


public class AddCourseActivity extends AppCompatActivity implements ViewActions {

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