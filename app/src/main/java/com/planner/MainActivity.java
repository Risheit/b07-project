package com.planner;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.models.UserDatabase;
import com.planner.databinding.ActivityMainBinding;
import com.presenters.users.User;
import com.presenters.users.UserLoginActions;
import com.presenters.users.UserManagement;

public class MainActivity extends AppCompatActivity {
    private UserManagement manager;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    EditText emailInput;
    EditText passwordInput;
    Button logInButton;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        // Instantiate database and reference
        manager = new UserManagement(new UserDatabase(
                "https://b07-project-e5893-default-rtdb.firebaseio.com/"));

        // When the log in button is clicked
        logInButton.setOnClickListener(view -> {
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please Enter All Possible Fields",
                        Toast.LENGTH_SHORT).show();
            } else { // Attempt to Log In
                manager.login(email, password, new UserLoginActions() {
                    @Override
                    public void studentLoginSuccess(User user) {
                        String name = user.getName();

                        // Open Student view
                        Intent intent = new Intent(MainActivity.this,
                                HomePageActivity.class);
                        intent.putExtra("name", name);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void adminLoginSuccess(User user) {
                        String name = user.getName();

                        // Open Admin view
                        Intent intent = new Intent(MainActivity.this,
                                AdminHomePageActivity.class);
                        intent.putExtra("name", name);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void incorrectPassword(User user) {
                        Toast.makeText(MainActivity.this, "Incorrect Password",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void invalidEmail() {
                        Toast.makeText(MainActivity.this, "Invalid Email",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        signUpButton.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, SignUpActivity.class)));
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










}