package com.planner;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.models.course.CourseDatabase;
import com.models.users.UserDatabase;
import com.planner.databinding.ActivityMainBinding;
import com.presenters.MainActivityPresenter;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    ActivityMainBinding binding;

    EditText emailInput;
    EditText passwordInput;
    Button logInButton;
    Button signUpButton;

    private MainActivityPresenter presenter;
    public static CourseDatabase courseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        courseDB = new CourseDatabase();

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
        Intent intent = new Intent(MainActivity.this,
                HomePageActivity.class);
        intent.putExtra("name", studentName);
        startActivity(intent);
        finish();
    }

    public void openAdminHomepage(String adminName) {
        Intent intent = new Intent(MainActivity.this,
                AdminHomePageActivity.class);
        intent.putExtra("name", adminName);
        startActivity(intent);
        finish();
    }

    public void openSignUpPage() {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }

    public void displayErrorNotification(String errorName) {
        Toast.makeText(MainActivity.this, errorName,
                Toast.LENGTH_SHORT).show();
    }

    public EditText getEmailInput() {
        return emailInput;
    }

    public EditText getPasswordInput() {
        return passwordInput;
    }
}