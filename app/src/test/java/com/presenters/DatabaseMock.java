package com.presenters;

import com.models.UserDatabaseInterface;
import com.presenters.users.User;

import java.util.HashMap;

public class DatabaseMock implements UserDatabaseInterface {
    HashMap<String, User> users;

    public DatabaseMock() {
        super();
        users = new HashMap<>();
    }

    @Override
    public void addUser(User user) {
        users.put(user.getEmail(), user);
    }

    @Override
    public User getUser(String email) {
        return users.get(email);
    }

    @Override
    public void removeUser(String emailKey) {
        users.remove(emailKey);
    }
}
