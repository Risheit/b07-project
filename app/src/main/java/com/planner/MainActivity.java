package com.planner;

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

import com.models.course.Course;
import com.models.course.CourseDatabase;
import com.models.users.User;
import com.models.users.UserDatabase;
import com.planner.databinding.ActivityMainBinding;
import com.presenters.MainPresenter;

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

*/


public class MainActivity extends AppCompatActivity implements ViewActions {

    private EditText emailInput;
    private EditText passwordInput;

    private MainPresenter presenter;
    public static User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // dont delete this line
        // called at the start of the program so we can load all the courses into the arraylist
        CourseDatabase.getInstance();

        presenter = new MainPresenter(this, new UserDatabase());

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.SignUpHeader);

        navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController,
                appBarConfiguration);

        emailInput = findViewById(R.id.inputEmail2);
        passwordInput = findViewById(R.id.inputPassword2);
        Button logInButton = findViewById(R.id.logInButton3);
        Button signUpButton = findViewById(R.id.signUpButton);

        // Setup Listeners
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
        return item.getItemId() == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    public EditText getEmailInput() {
        return emailInput;
    }

    public EditText getPasswordInput() {
        return passwordInput;
    }
}