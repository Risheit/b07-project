package com.planner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.models.UserDatabase;
import com.planner.databinding.ActivitySignUpBinding;
import com.presenters.User;
import com.presenters.UserManagement;

public class SignUpActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivitySignUpBinding binding;
    String email, first_name, last_name, password;
    String type;

    EditText new_emailInput;
    EditText first_nameInput;
    EditText last_nameInput;
    EditText passInput;

    Button signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.SignUpHeader);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_sign_up);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        new_emailInput = (EditText) findViewById(R.id.signUpInputEmail);
        first_nameInput = (EditText) findViewById(R.id.editTextTextPersonName);
        last_nameInput = (EditText) findViewById(R.id.SignInInputLastName);
        passInput = (EditText) findViewById(R.id.editTextTextPassword2);

        signUpButton = (Button) findViewById(R.id.signUpButton);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = new_emailInput.getText().toString();
                first_name = first_nameInput.getText().toString();
                last_name = last_nameInput.getText().toString();
                password = passInput.getText().toString();
                type = "Student"; //assume all users are students?
                User user = new User(type, email, first_name + " " + last_name, password);
                new UserManagement(new UserDatabase()).signupUser(user);
            }

        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_sign_up);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void goToLogInActivity (View view){
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }
}