package com.planner;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.models.course.Course;
import com.models.course.CourseDatabase;
import com.planner.databinding.ActivityEditCourseBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        String session = sessionInput.getText().toString();
        String prerequisiteString = prereqInput.getText().toString();

        if (courseCode.isEmpty() || newCourseCode.isEmpty()
                || title.isEmpty() || session.isEmpty()) {
            displayErrorNotification(EditCourseActivity.this,
                    "Please Enter All Fields");
        } else {
            ArrayList<String> listSessions = new ArrayList<>(
                    Arrays.asList(session.split(","))
            );
            List<String> prerequisiteCodes = Arrays.asList(prerequisiteString.split(","));

            if (courseDB.getCourse(courseCode) == null) {
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

            Course course = courseDB.getCourse(courseCode);
            course.setCode(newCourseCode);
            course.setName(title);
            course.setSessionalDates(listSessions);
            courseDB.removeCourse(courseCode);

            List<Course> prerequisites = new ArrayList<>();
            if (hasPrereq) {
                prerequisiteCodes.forEach(code -> prerequisites.add(courseDB.getCourse(code)));
            }
            course.setPrerequisites(prerequisites);

            courseDB.addCourse(course);

            displayNotification(EditCourseActivity.this, "Course edited");
            openAdminHomepage(EditCourseActivity.this);
        }
    }

    private boolean invalidPrerequisiteGiven(List<String> prerequisiteCodes) {
        return prerequisiteCodes.stream().anyMatch(code -> courseDB.getCourse(code) == null);
    }
}