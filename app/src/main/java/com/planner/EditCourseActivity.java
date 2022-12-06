package com.planner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.models.Session;
import com.models.course.Course;
import com.models.course.CourseDatabase;
import com.planner.databinding.ActivityEditCourseBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EditCourseActivity extends AppCompatActivity implements ViewActions {

    private final CourseDatabase courseDB = CourseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityEditCourseBinding binding = ActivityEditCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        Button backButton = (Button) findViewById(R.id.button10);
        Button doneButton = (Button) findViewById(R.id.button11);

        // Setup Listeners
        backButton.setOnClickListener(view -> openAdminHomepage(EditCourseActivity.this));
        doneButton.setOnClickListener(view -> onDoneButtonClicked());
    }

    private void onDoneButtonClicked() {
        EditText courseCodeInput = (EditText) findViewById(R.id.editTextTextPersonName13);
        EditText newCourseCodeInput = (EditText) findViewById(R.id.editTextTextPersonName18);
        EditText titleInput = (EditText) findViewById(R.id.editTextTextPersonName19);
        EditText sessionInput = (EditText) findViewById(R.id.editTextTextPersonName20);
        EditText prereqInput = (EditText) findViewById(R.id.editTextTextPersonName21);

        String courseCode = courseCodeInput.getText().toString();
        String newCourseCode = newCourseCodeInput.getText().toString();
        String title = titleInput.getText().toString();
        String sessionString = sessionInput.getText().toString();
        String prerequisiteString = prereqInput.getText().toString();

        if (courseCode.isEmpty()) {
            displayErrorNotification(EditCourseActivity.this,
                    "Please Enter a course code.");
            return;
        }

        List<String> prerequisiteCodes = Arrays.asList(prerequisiteString.split(","));

        Course course = courseDB.getCourse(courseCode);
        if (course == null) {
            displayErrorNotification(EditCourseActivity.this,
                    "Course does not exist");
            return;
        }

        boolean hasPrereq = !prerequisiteCodes.get(0).isEmpty();
        if (hasPrereq && invalidPrerequisiteGiven(prerequisiteCodes)) {
            displayErrorNotification(EditCourseActivity.this,
                    "One or more of your prerequisite courses is not "
                            + "registered in the database. Addition cancelled");
            return;
        }

        List<String> unValidatedSessions = Arrays.stream(sessionString.split(","))
                .map(String::toLowerCase)
                .map(String::trim)
                .collect(Collectors.toList());
        List<String> sessions = Session.getValidSessionDatesFromString(sessionString);
        // Invalid sessions given
        if (!sessionString.isEmpty() && (unValidatedSessions.size() > sessions.size())) {
            displayErrorNotification(EditCourseActivity.this,
                    "Please enter valid session names separated by commas");
            return;
        }

        List<Course> prerequisites = courseDB.getCourseListFromString(prerequisiteCodes);

        course.setCode(newCourseCode.isEmpty() ? course.getCode() : newCourseCode);
        course.setName(title.isEmpty() ? course.getName() : title);
        course.setSessionalDates(sessions.isEmpty() ? course.getSessionalDates() : sessions);
        course.setPrerequisites(!hasPrereq ? course.getPrerequisites() : prerequisites);

        courseDB.removeCourse(courseCode);
        courseDB.addCourse(course);

        displayNotification(EditCourseActivity.this, "Course edited");
        openAdminHomepage(EditCourseActivity.this);
    }

    private boolean invalidPrerequisiteGiven(List<String> prerequisiteCodes) {
        return prerequisiteCodes.stream().anyMatch(code -> courseDB.getCourse(code) == null);
    }
}