package com.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.models.course.CourseDatabase;
import com.models.users.User;
import com.models.users.UserDatabase;

import java.util.ArrayList;

public class CourseListAdd extends AppCompatActivity implements ViewActions {
    Button backButton;

    CourseDatabase courseDB = CourseDatabase.getInstance();

    // Listview
    ListView listView;
    String[] noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list_add);

        backButton = (Button) findViewById(R.id.button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseListAdd.this, CourseList.class);
                startActivity(intent);
                finish();
            }
        });

        // fill the noteListArrayList with every course code that has not been taken by the current user
        ArrayList<String> noteListArrayList = new ArrayList<>();
        for(int i = 0; i < courseDB.getCourses().size(); i++) {
            if(!MainActivity.currentUser.getCourseCodesTaken().contains(courseDB.getCourses().get(i).getCode()))
                noteListArrayList.add(courseDB.getCourses().get(i).getCode());
        }
        // then turn that into an array so we can pass it into the adapter
        noteList = noteListArrayList.toArray(new String[0]);

        listView = findViewById(R.id.listviewz);

        // Array Adapter
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                noteList);

        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String code = (String) listView.getItemAtPosition(i);

                // update the current user's courseCodesTaken with the new course code
                ArrayList<String> newTaken = (ArrayList) MainActivity.currentUser.getCourseCodesTaken();
                newTaken.add(code);
                MainActivity.currentUser.setCourseCodesTaken(newTaken);

                // edit the user in the database
                UserDatabase u = new UserDatabase();
                u.editUser(MainActivity.currentUser, MainActivity.currentUser.getEmail());

                // go back to CourseList.java having now updated the current user
                Intent intent = new Intent(CourseListAdd.this, CourseList.class);
                startActivity(intent);
                finish();
            }
        });
    }
}