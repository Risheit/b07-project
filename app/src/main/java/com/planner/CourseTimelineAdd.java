package com.planner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.models.course.CourseDatabase;
import com.planner.databinding.ActivityCourseListBinding;
import com.planner.databinding.ActivityCourseTimelineAddBinding;

import java.util.ArrayList;

public class CourseTimelineAdd extends AppCompatActivity implements ViewActions {

    private ActivityCourseTimelineAddBinding binding;
    Button backButton;
    Button generateButton;

    CourseDatabase courseDB = CourseDatabase.getInstance();

    // Listview stuff
    ListView listView;
    String[] noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_timeline_add);

        //binding = ActivityCourseTimelineAddBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());

        backButton = (Button) findViewById(R.id.button13);

        getSupportActionBar().hide();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseTimelineAdd.this, HomePageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        generateButton = (Button) findViewById(R.id.button16);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the checked entries in the list and add them all to selectedCodes
                SparseBooleanArray selected = listView.getCheckedItemPositions();
                ArrayList<String> selectedCodes = new ArrayList<>();


                for(int ind=0; ind<selected.size(); ind++) {
                    int pos = selected.keyAt(ind);
                    if(selected.valueAt(ind))
                        selectedCodes.add((String)listView.getItemAtPosition(pos));
                }

                // add all the selected codes to the courses the user wants to take
                MainActivity.currentUser.setCourseCodesPlanned(selectedCodes);

                Intent timeline_intent = new Intent(CourseTimelineAdd.this, CourseTimelineRActivity.class);
                startActivity(timeline_intent);
                finish();
            }
        });

        // fill the noteListArrayList with every course code that has not been taken by the current user
        ArrayList<String> noteListArrayList = new ArrayList<>();
        for(int i = 0; i < courseDB.courses.size(); i++) {
            if(!MainActivity.currentUser.getCourseCodesTaken().contains(courseDB.courses.get(i).getCode()))
                noteListArrayList.add(courseDB.courses.get(i).getCode());
        }
        // then turn that into an array so we can pass it into the adapter
        noteList = noteListArrayList.toArray(new String[0]);

        listView = findViewById(R.id.listviewx);

        // Array Adapter
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice,
                noteList);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setClickable(true);
        listView.setAdapter(adapter);
    }
}