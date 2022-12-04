package com.presenters;

import com.models.users.User;
import com.models.users.UserDatabaseInterface;
import com.models.users.UserManagement;
import com.planner.SignUpActivity;
import com.planner.ViewActions;

public class SignUpActivityPresenter {

    UserManagement manager;
    SignUpActivity view;

    public SignUpActivityPresenter(SignUpActivity view, UserDatabaseInterface db) {
        this.manager = new UserManagement(db);
        this.view = view;
    }

    public void onSignUpButtonClicked() {
        String email = view.getNew_emailInput().getText().toString();
        String first_name = view.getFirst_nameInput().getText().toString();
        String last_name = view.getLast_nameInput().getText().toString();
        String password = view.getPassInput().getText().toString();
        String conPass = view.getConfirmPass().getText().toString();
        String type = "Student"; //assume all users are students?

        User new_user = new User(type,first_name + " " + last_name, email, password);
        System.out.println(email);

        if (email.isEmpty() || first_name.isEmpty() || last_name.isEmpty() || password.isEmpty() ||
                conPass.isEmpty()) {
            ViewActions.displayErrorNotification(view,"Please Enter All Fields");
        } else {
            if (!password.equals(conPass)) {
                ViewActions.displayErrorNotification(view, "Passwords do not match");
            } else {
                manager.signupUser(new_user);
                view.displayErrorNotification("User added, Please Log In");
            }
        }
    }
}
