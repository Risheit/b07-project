package com.presenters.users;

import com.presenters.users.User;

public interface UserSignup {
    /***
     This function adds an user to the database if it does not exist already, otherwise does nothing
     @param user is the object to be added
     @returns true if user is added, false if not
     */
    boolean signupUser(User user);
}
