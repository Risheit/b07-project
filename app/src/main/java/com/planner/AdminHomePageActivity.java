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

public class AdminHomePageActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityAdminHomePageBinding binding;
    TextView welcomeText;
    Button addCourseButton;
    Button deleteCourseButton;
    Button editCourseButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_admin_home_page);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        welcomeText = (TextView) findViewById(R.id.textView14);
        addCourseButton = (Button) findViewById(R.id.AddCourseButton);
        deleteCourseButton = (Button) findViewById(R.id.DeleteCourseButton);
        editCourseButton = (Button) findViewById(R.id.button12);

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        if(null != name){
            welcomeText.setText("Welcome Admin: " + name);
        }else{
            welcomeText.setText("Welcome Admin");
        }

        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomePageActivity.this, AddCourse2Activity.class);
                startActivity(intent);
                finish();
            }
        });

        deleteCourseButton.setOnClickListener(view -> {
            Intent intent1 = new Intent(AdminHomePageActivity.this, RemoveCourse2Activity.class);
            startActivity(intent1);
            finish();
        });

        editCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(AdminHomePageActivity.this, EditCourseActivity.class);
                startActivity(intent2);
                finish();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_admin_home_page);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}