package com.planner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.models.users.UserDatabase;
import com.planner.databinding.ActivitySignUpBinding;
import com.presenters.SignUpPresenter;

public class SignUpActivity extends AppCompatActivity implements ViewActions {

    private SignUpPresenter presenter;

    private EditText new_emailInput;
    private EditText first_nameInput;
    private EditText last_nameInput;
    private EditText passInput;
    private EditText confirmPass;
    Button signUpButton;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new SignUpPresenter(this, new UserDatabase());

        ActivitySignUpBinding binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.SignUpHeader);
        //getSupportActionBar().hide();

        new_emailInput = (EditText) findViewById(R.id.signUpInputEmail);
        first_nameInput = (EditText) findViewById(R.id.editTextTextPersonName);
        last_nameInput = (EditText) findViewById(R.id.SignInInputLastName);
        passInput = (EditText) findViewById(R.id.editTextTextPassword2);
        confirmPass = (EditText) findViewById(R.id.ConfirmPassword);
        signUpButton = (Button) findViewById(R.id.NewAccountButton);
        backButton = (Button) findViewById(R.id.ReturnToLogIn);


        // Setup Listeners
        backButton.setOnClickListener(view -> presenter.onBackButtonClicked());
        signUpButton.setOnClickListener(view -> presenter.onSignUpButtonClicked());
    }

    public EditText getNew_emailInput() {
        return new_emailInput;
    }

    public EditText getFirst_nameInput() {
        return first_nameInput;
    }

    public EditText getLast_nameInput() {
        return last_nameInput;
    }

    public EditText getPassInput() {
        return passInput;
    }

    public EditText getConfirmPass() {
        return confirmPass;
    }
}