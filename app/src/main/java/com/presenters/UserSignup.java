package com.presenters;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UserSignup {

    public static void signupUser(EditText nameField, EditText emailField,
                                  EditText passwordField, View view) {
        User user = new User(nameField.getText().toString(), emailField.getText().toString(),
                passwordField.getText().toString());

        int duration = Toast.LENGTH_SHORT;

        /*
        if (UserManagement.writeUser(user)) {
            // snackbar registration successful
            CharSequence text = "Registriation Successful";
        } else {
            //snackbar registration failed
            CharSequence text = "Registriation Failed";
        }
         */
//        Toast toast = Toast.makeText(view.getContext(), text, duration);
        //switch back to fragment 1 - responsibility for the calling method
    }
}
