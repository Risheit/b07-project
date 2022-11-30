package com.presenters.users;

public interface UserLoginActions {
    void studentLoginSuccess();
    void adminLoginSuccess();
    void incorrectPassword();
    void invalidEmail();
}
