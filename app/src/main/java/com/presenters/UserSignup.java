package com.presenters;

import android.view.View;
import android.widget.EditText;

import com.models.UserDatabase;
import com.models.UserDatabaseInterface;

public interface UserSignup {
    /***
    Takes a object user of type User and a UserDatabaseInterface db; adds user to the database
     if it does not exist already, returns true if user is added, false if not
     */
    boolean signupUser(User user);
}
