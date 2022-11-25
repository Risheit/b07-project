package com.models;

import com.presenters.User;

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
     * @return       The user with the email matching our input parameter
     *               or null if the user is not in the database
     */

    User getUser(String email);

    /**
     * This method removes a user from the firebase realtime database,
     * assuming the user is in the database
     * @param emailKey  The email of the user we want to delete
     */

    void removeUser(String emailKey);
}
