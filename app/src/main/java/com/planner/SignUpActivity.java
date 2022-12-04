package com.planner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.models.users.UserDatabase;
import com.planner.databinding.ActivitySignUpBinding;
import com.presenters.SignUpActivityPresenter;

public class SignUpActivity extends AppCompatActivity {
    DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://b07-project-e5893-default-rtdb.firebaseio.com/");
    private AppBarConfiguration appBarConfiguration;
    private ActivitySignUpBinding binding;

    private SignUpActivityPresenter presenter;

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

        presenter = new SignUpActivityPresenter(this, new UserDatabase(
                "https://b07-project-e5893-default-rtdb.firebaseio.com/"
        ));

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
        confirmPass = (EditText) findViewById(R.id.ConfirmPassword);
        signUpButton = (Button) findViewById(R.id.NewAccountButton);
        backButton = (Button) findViewById(R.id.ReturnToLogIn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });

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

    public void displayErrorNotification(String errorName) {
        Toast.makeText(SignUpActivity.this, errorName,
                Toast.LENGTH_SHORT).show();
    }
}