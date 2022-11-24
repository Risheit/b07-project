package com.presenters;

import android.view.View;
import android.widget.EditText;

import com.models.UserDatabaseInterface;

public class UserManagement implements Verify, UserSignup {

    /**
     * This method checks if the calling object (an instance of User) has an account
     * in the database.
     * @return True if the user has an account within the database, and false otherwise
     */

    @Override
    public boolean verify(UserDatabaseInterface db, User user) {
        User valid = db.getUser(user.getEmail());

        return !(valid == null) && valid.getPassword().equals(user.getPassword());
    }

    /***
     Takes a object user of type User and a UserDatabaseInterface db; adds user to the database
     if it does not exist already, returns true if user is added, false if not
     */

    @Override
    public boolean signupUser(User user, UserDatabaseInterface db) {
        if (!verify (db, user)) { // verify returns false if null
            db.addUser(user);
            return true;
        } else {
            return false;
        }
    }
}
