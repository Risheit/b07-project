package com.presenters.users;

import com.callbacks.Caller;
import com.models.ModelManager;
import com.models.UserDatabase;
import com.models.onGetDataListener;

public class UserManagement {

    private final UserDatabase connection;

    /**
     * Instantiates a new UserManagement class.
     * @param connection The database this management class is connected to.
     */

    public UserManagement(UserDatabase connection) {
        this.connection = connection;
    }

    public void login(String email, String password, UserLoginActions actions) {
        connection.getUser(
                email,
                new onGetDataListener<User>() {
                    @Override
                    public void onSuccess(User user) {
                        String dbPassword;

                        if (user == null) {
                            actions.invalidEmail();
                            return;
                        }

                        dbPassword = user.getPassword();
                        if (!dbPassword.equals(password)) {
                            actions.incorrectPassword();
                        }

                        if (user.getType().equals("student")){ // Student Account
                            actions.studentLoginSuccess();
                        } else { // Admin Account
                            actions.adminLoginSuccess();
                        }

                    }

                    @Override
                    public void onFailure() {

                    }
                }
        );
    }

    /**
     * This function adds an user to the database if it does not exist already, otherwise does nothing
     * @param user is the object to be added
     */
    public void signupUser(User user) {
        connection.getUser(user.getEmail(), new onGetDataListener<User>() {
            @Override
            public void onSuccess(User data) {
                if (data != null) {
                    return;
                }

                connection.addUser(user);
            }

            @Override
            public void onFailure() {

            }
        });
    }
}
