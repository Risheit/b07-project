package com.models;

import com.presenters.User;

public interface UserDatabaseInterface {
    //void addUser(String type, String name, String email, String password);

    void addUser(User user);

    User getUser(String email);

    void removeUser(String emailKey);
}
