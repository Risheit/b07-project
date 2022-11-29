package com.planner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.planner.databinding.ActivitySignUpBinding;
import com.presenters.User;

public class SignUpActivity extends AppCompatActivity {
    DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://b07-project-e5893-default-rtdb.firebaseio.com/");
    private AppBarConfiguration appBarConfiguration;
    private ActivitySignUpBinding binding;
    String email, first_name, last_name, password, commaEmail, conPass;
    String type;

    EditText new_emailInput;
    EditText first_nameInput;
    EditText last_nameInput;
    EditText passInput;
    EditText confirmPass;
    Button signUpButton;
    String name;

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
        confirmPass = (EditText) findViewById(R.id.ConfirmPassword);
        signUpButton = (Button) findViewById(R.id.NewAccountButton);



        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = new_emailInput.getText().toString();
                commaEmail = email.replace('.', ',');
                first_name = first_nameInput.getText().toString();
                last_name = last_nameInput.getText().toString();
                password = passInput.getText().toString();
                conPass = confirmPass.getText().toString();
                type = "Student"; //assume all users are students?
                User new_user = new User(type,first_name + " " + last_name, email, password);
                if(email.isEmpty() || first_name.isEmpty() || last_name.isEmpty() || password.isEmpty() || conPass.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Please Enter All Possible Fields", Toast.LENGTH_SHORT).show();
                }else{
                    ref.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(commaEmail)){
                                Toast.makeText(SignUpActivity.this, "An account has already been created with this email", Toast.LENGTH_SHORT).show();
                            }else{
                                if(conPass.equals(password)){
                                    ref.child("users").child(commaEmail).setValue(new_user);
                                    name = first_name + " " + last_name;
                                    Intent intent = new Intent(SignUpActivity.this, HomePageActivity.class);
                                    intent.putExtra("name", name);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_sign_up);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

}