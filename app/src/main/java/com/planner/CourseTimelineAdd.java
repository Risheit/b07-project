package com.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.models.course.CourseDatabase;
import com.models.users.UserDatabase;

import java.util.ArrayList;

public class CourseTimelineAdd extends AppCompatActivity {

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

        backButton = (Button) findViewById(R.id.button13);

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
                Log.e("before", MainActivity.currentUser.getCourseCodesPlanned().toString());
                // get the checked entries in the list and add them all to selectedCodes
                SparseBooleanArray selected = listView.getCheckedItemPositions();
                ArrayList<String> selectedCodes = new ArrayList<>();
                for(int i=0; i<selected.size(); i++) {
                    int pos = selected.keyAt(i);
                    if(selected.valueAt(i) == true)
                        selectedCodes.add((String)listView.getItemAtPosition(pos));
                }

                // add all the selected codes to the courses the user wants to take
                MainActivity.currentUser.setCourseCodesPlanned(selectedCodes);

                // update the database
                UserDatabase u = new UserDatabase("https://b07-project-e5893-default-rtdb.firebaseio.com/");
                u.editUser(MainActivity.currentUser, MainActivity.currentUser.getEmail());

                Log.e("after", MainActivity.currentUser.getCourseCodesPlanned().toString());

                Intent intent = new Intent(CourseTimelineAdd.this, CourseTimelineActivity.class);
                startActivity(intent);
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