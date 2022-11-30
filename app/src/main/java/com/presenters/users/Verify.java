package com.presenters.users;

import com.presenters.users.User;

/**
 * This is an interface that verifies is a user has an account within the database
 */

public interface Verify {

    /**
     * This method checks if the calling object (an instance of User) has an account
     * in the database.
     * @return True if the user has an account within the database, and false otherwise
     */

    boolean verify(User user);
}
