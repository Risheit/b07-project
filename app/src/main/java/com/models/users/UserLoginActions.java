package com.models.users;

public interface UserLoginActions {
    void studentLoginSuccess(User user);
    void adminLoginSuccess(User user);
    void incorrectPassword(User user);
    void invalidEmail();
}
