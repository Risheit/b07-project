package com.presenters;

import com.models.UserDatabaseInterface;

import java.util.Objects;

public class UserManagement implements WriteUser, Verify {

    @Override
    public boolean verify(UserDatabaseInterface db, User user) {
        User valid = db.getUser(user.getEmail());

        return valid.getPassword().equals(user.getPassword());
    }
}
