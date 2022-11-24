package com.planner;

import android.content.Intent;
import android.os.Bundle;

import com.example.generictemplate.R;
import com.example.generictemplate.databinding.ActivitySignUpBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.planner.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivitySignUpBinding binding;
    String email, first_name, last_name, password;

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
            }
            //User(type, email, first_name + " " + last_name, password)
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