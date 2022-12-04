package com.models.users;

/**
 * Methods to be called depending on the possible results of a call to login()
 */

public interface UserLoginActions {
    /**
     * The student was successfully logged in.
     * @param user The queried user.
     */
    void studentLoginSuccess(User user);

    /**
     * The admin was successfully logged in.
     * @param user The queried user.
     */
    void adminLoginSuccess(User user);

    /**
     * The login failed due to an incorrect password.
     * @param user The queried user.
     */
    void incorrectPassword(User user);

    /**
     * The login failed due to an invalid email.
     */
    void invalidEmail();
}
