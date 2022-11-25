package com.presenters;

import com.models.UserDatabaseInterface;

public class UserManagement implements Verify, UserSignup {

    private final UserDatabaseInterface connection;

    /**
     * Instantiates a new UserManagement class.
     * @param connection The database this management class is connected to.
     */

    public UserManagement(UserDatabaseInterface connection) {
        this.connection = connection;
    }

    /**
     * This method checks if the calling object (an instance of User) has an account
     * in the database.
     * @return True if the user has an account within the database, and false otherwise
     */

    @Override
    public boolean verify(User user) {
        if (user == null)
            return false;

        User valid = connection.getUser(user.getEmail());

        return valid.getPassword().equals(user.getPassword());
    }

    /***
     * This function adds an user to the database if it does not exist already, otherwise does nothing
     * @param user is the object to be added
     * @returns true if user is added, false if not
     */
    @Override
    public boolean signupUser(User user) {
        if (connection.getUser(user.getEmail()) == null) {
            connection.addUser(user);
            return true;
        } else {
            return false;
        }
    }
}
