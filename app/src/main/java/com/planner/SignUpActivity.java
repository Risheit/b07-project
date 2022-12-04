package com.planner;

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
import com.presenters.SignUpActivityPresenter;

public class SignUpActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;

    private SignUpActivityPresenter presenter;

    private EditText new_emailInput;
    private EditText first_nameInput;
    private EditText last_nameInput;
    private EditText passInput;
    private EditText confirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySignUpBinding binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        Button signUpButton = (Button) findViewById(R.id.NewAccountButton);

        NavController navController = Navigation.findNavController(
                this,
                R.id.nav_host_fragment_content_sign_up
        );
        NavigationUI.setupActionBarWithNavController(
                this, navController,
                appBarConfiguration
        );

        new_emailInput = (EditText) findViewById(R.id.signUpInputEmail);
        first_nameInput = (EditText) findViewById(R.id.editTextTextPersonName);
        last_nameInput = (EditText) findViewById(R.id.SignInInputLastName);
        passInput = (EditText) findViewById(R.id.editTextTextPassword2);
        confirmPass = (EditText) findViewById(R.id.ConfirmPassword);

        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();

        presenter = new SignUpActivityPresenter(this, new UserDatabase(
                "https://b07-project-e5893-default-rtdb.firebaseio.com/"
        ));

        setContentView(binding.getRoot());

        setSupportActionBar(binding.SignUpHeader);

        signUpButton.setOnClickListener(view -> presenter.onSignUpButtonClicked());
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_sign_up);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
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