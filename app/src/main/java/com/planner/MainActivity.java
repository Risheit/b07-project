package com.planner;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.models.course.CourseDatabase;
import com.models.users.User;
import com.models.users.UserDatabase;
import com.planner.databinding.ActivityMainBinding;

/* TODO: Refactor all this

FirstFragment,Second -- MainActivity
First2Fragment,Second -- SignUpActivity
First3Fragment,Second -- HomepageActivity
First4Fragment,Second -- AdminHomepageActivity
First5Fragment,Second --
First6Fragment,Second --
First7Fragment,Second --
First8Fragment,Second --
First9Fragment,Second --

UselessActivities:
    - AddCourseActivity is useless -> Rename AddCourse2
    - AdminActivity
    - RemoveCourseActivity -> Rename RemoveCourse2
 */


public class MainActivity extends AppCompatActivity implements ViewActions {
    private AppBarConfiguration appBarConfiguration;
    ActivityMainBinding binding;

    EditText emailInput;
    EditText passwordInput;
    Button logInButton;
    Button signUpButton;

    private MainActivityPresenter presenter;
    CourseDatabase courseDB;
    public static User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        courseDB = CourseDatabase.getInstance();

        super.onCreate(savedInstanceState);

        presenter = new MainActivityPresenter(this, new UserDatabase(
                "https://b07-project-e5893-default-rtdb.firebaseio.com/"
        ));

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.SignUpHeader);

        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController,
                appBarConfiguration);

        emailInput = findViewById(R.id.inputEmail2);
        passwordInput = findViewById(R.id.inputPassword2);
        logInButton = findViewById(R.id.logInButton3);
        signUpButton = findViewById(R.id.signUpButton);

        logInButton.setOnClickListener(view -> presenter.onLoginButtonClicked());
        signUpButton.setOnClickListener(view -> presenter.onSignUpButtonClicked());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void openStudentHomepage(String studentName) {
        Intent intent = new Intent(this, HomePageActivity.class);
        intent.putExtra("name", studentName);
        startActivity(intent);
        finish();
    }

    public void openAdminHomepage(String adminName) {
        Intent intent = new Intent(this, AdminHomePageActivity.class);
        intent.putExtra("name", adminName);
        startActivity(intent);
        finish();
    }

    public void openSignUpPage() {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    public EditText getEmailInput() {
        return emailInput;
    }

    public EditText getPasswordInput() {
        return passwordInput;
    }
}