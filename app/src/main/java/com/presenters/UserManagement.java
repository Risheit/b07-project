package com.presenters;

import android.view.View;
import android.widget.EditText;

import com.models.UserDatabaseInterface;

public class UserManagement implements Verify, UserSignup {

    @Override
    public boolean verify(UserDatabaseInterface db, User user) {
        User valid = db.getUser(user.getEmail());

        return !(valid == null) && valid.getPassword().equals(user.getPassword());
    }

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
