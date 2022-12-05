package com.presenters;


import com.models.users.UserDatabaseInterface;
import com.planner.MainActivity;
import com.models.users.User;
import com.models.users.UserLoginActions;
import com.models.users.UserManagement;
import com.planner.ViewActions;

public class MainPresenter {

    private final UserManagement manager;
    private final MainActivity view;

    public MainPresenter(MainActivity view, UserDatabaseInterface db) {
        this.manager = new UserManagement(db);
        this.view = view;
    }

    public void onLoginButtonClicked() {
        String email = view.getEmailInput().getText().toString();
        String password = view.getPasswordInput().getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            view.displayErrorNotification(view,
                    "Please Enter All Possible Fields");
        } else { // Attempt to Log In
            manager.login(email, password, new UserLoginActions() {
                @Override
                public void studentLoginSuccess(User user) {
                    MainActivity.currentUser = user;
                    view.openStudentHomepage(view);
                }

                @Override
                public void adminLoginSuccess(User user) {
                    MainActivity.currentUser = user;
                    view.openAdminHomepage(view);
                }

                @Override
                public void incorrectPassword(User user) {
                    view.displayErrorNotification(view, "Incorrect Password");
                }

                @Override
                public void invalidEmail() {
                    view.displayErrorNotification(view, "Invalid Email");
                }
            });
        }
    }

    public void onSignUpButtonClicked() {
        view.openSignUpPage(view);
    }
}
