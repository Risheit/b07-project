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
        User valid = connection.getUser(user.getEmail());

        return !(valid == null) && valid.getPassword().equals(user.getPassword());
    }

    /***
     Takes a object user of type User and a UserDatabaseInterface db; adds user to the database
     if it does not exist already, returns true if user is added, false if not
     */

    @Override
    public boolean signupUser(User user) {
        if (!verify (user)) { // verify returns false if null
            connection.addUser(user);
            return true;
        } else {
            return false;
        }
    }
}
