package com.models.users;

import com.models.onGetDataListener;

public class UserManagement {

    public static final String studentConnection = "Student";
    public static final String adminConnection = "Admin";

    private final UserDatabaseInterface connection;

    /**
     * Instantiates a new UserManagement class.
     * @param connection The database this management class is connected to.
     */
    public UserManagement(UserDatabaseInterface connection) {
        this.connection = connection;
    }

    /**
     * Asynchronous method that attempts to log a user in given an email and password.
     * @param email The user's email.
     * @param password The user's password.
     * @param actions Actions to take depending on result of log in attempt.
     */
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
                            actions.incorrectPassword(user);
                            return;
                        }

                        if (user.getType().equals(studentConnection)){ // Student Account
                            actions.studentLoginSuccess(user);
                        } else if (user.getType().equals(adminConnection)) { // Admin Account
                            actions.adminLoginSuccess(user);
                        } else {
                            System.out.println("Database type that isn't student or admin exists.");
                        }

                    }

                    @Override
                    public void onFailure() {

                    }
                }
        );
    }

    /**
     * This function adds an user to the database if it does not exist already, otherwise does
     * nothing.
     * @param user is the object to be added.
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
