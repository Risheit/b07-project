package com.presenters;

import com.models.UserDatabaseInterface;
import com.models.onGetDataListener;
import com.presenters.users.User;

import java.util.HashMap;

/**
 * A class that imitates the User Database model to allow for easier testing.
 */
public class DatabaseMock implements UserDatabaseInterface {
    HashMap<String, User> users; // email as key

    public DatabaseMock() {
        super();
        users = new HashMap<>();
    }

    @Override
    public void addUser(User user) {
        users.put(user.getEmail(), user);
    }

    @Override
    public void getUser(String email, onGetDataListener<User> then) {
        // Imitate asynchronous behaviour of the real getUser by running getUser on separate thread.
        // We don't consider then.onFailure as our database can't fail to "connect".
        new Thread(() -> {
            User user = users.get(email);
            then.onSuccess(user);
        }).start();
    }

    @Override
    public void removeUser(String emailKey) {
        users.remove(emailKey);
    }
}
