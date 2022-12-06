package com.models.users;

import com.models.onGetDataListener;
import com.models.users.User;

/**
 * This is an interface to manage all of the users within a firebase realtime database.
 */

public interface UserDatabaseInterface {

    /**
     * This method adds a user to the firebase realtime database
     * @param user  The user that we want to add to the database
     */

    void addUser(User user);

    /**
     * This method looks for a user to the firebase realtime database
     * @param email  The email of the user we want to find
     *
     */

    void getUser(String email, onGetDataListener<User> then);

    /**
     * This method removes a user from the firebase realtime database,
     * assuming the user is in the database
     * @param emailKey  The email of the user we want to delete
     */

    void removeUser(String emailKey);
}
