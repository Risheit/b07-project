package com.models;

import com.models.users.UserDatabaseInterface;
import com.models.users.User;
import com.models.users.UserLoginActions;
import com.models.users.UserManagement;

import org.junit.*;
import static org.junit.Assert.*;

public class UserManagementTests {
    private UserDatabaseInterface dbMock;
    private UserManagement management;
    private User user1, user2;

    @Before
    public void setUp() {
        dbMock = new DatabaseMock();
        management = new UserManagement(dbMock);

        user1 = new User("Student", "Charles Mominjay", "charlesmj@gmail.com",
                "password123");
        user2 = new User("Admin", "Admin Man", "admin@adminmail.ca",
                "ComplexAdminPassword");

        dbMock.addUser(user1);
        dbMock.addUser(user2);
    }

    @Test
    public void testStudentLoginSuccess() {
        management.login(user1.getEmail(), user1.getPassword(), new UserLoginActions() {
            @Override
            public void studentLoginSuccess(User user) {
                assertEquals(user1, user);
            }

            @Override
            public void adminLoginSuccess(User user) {
                fail("Admin Login reached instead of Student Login.");
            }

            @Override
            public void incorrectPassword(User user) {
                fail("Incorrect password error received when correct password given.");
            }

            @Override
            public void invalidEmail() {
                fail("Invalid email error received when email is valid.");
            }
        });
    }

    @Test
    public void testAdminLoginSuccess() {
        management.login(user2.getEmail(), user2.getPassword(), new UserLoginActions() {
            @Override
            public void studentLoginSuccess(User user) {
                fail("Student Login reached instead of Admin Login.");
            }

            @Override
            public void adminLoginSuccess(User user) {
                assertEquals(user2, user);
            }

            @Override
            public void incorrectPassword(User user) {
                fail("Incorrect password error received when correct password given.");
            }

            @Override
            public void invalidEmail() {
                fail("Invalid email error received when email is valid.");
            }
        });
    }

    @Test
    public void testLoginWithIncorrectPassword() {
        management.login(user1.getEmail(), "", new UserLoginActions() {
            @Override
            public void studentLoginSuccess(User user) {
                fail("Student Login reached instead of receiving incorrect password error.");
            }

            @Override
            public void adminLoginSuccess(User user) {
                fail("Admin Login reached instead of receiving incorrect password error.");
            }

            @Override
            public void incorrectPassword(User user) {
                assertEquals(user1, user);
            }

            @Override
            public void invalidEmail() {
                fail("Invalid email error received when email is valid.");
            }
        });
    }

    @Test
    public void testLoginWithNullPassword() {
        management.login(user1.getEmail(), null, new UserLoginActions() {
            @Override
            public void studentLoginSuccess(User user) {
                fail("Student Login reached instead of receiving incorrect password error.");
            }

            @Override
            public void adminLoginSuccess(User user) {
                fail("Admin Login reached instead of receiving incorrect password error.");
            }

            @Override
            public void incorrectPassword(User user) {
                assertEquals(user1, user);
            }

            @Override
            public void invalidEmail() {
                fail("Invalid email error received when email is valid.");
            }
        });
    }

    @Test
    public void testLoginWithInvalidEmail() {
        management.login("invalidEmail@bad.com", user1.getPassword(), new UserLoginActions() {
            @Override
            public void studentLoginSuccess(User user) {
                fail("Student Login reached instead of receiving invalid email error.");
            }

            @Override
            public void adminLoginSuccess(User user) {
                fail("Admin Login reached instead of receiving invalid email error.");
            }

            @Override
            public void incorrectPassword(User user) {
                fail("Incorrect password error received instead of receiving invalid email error.");
            }

            @Override
            public void invalidEmail() {
                // Pass test
            }
        });
    }

    @Test
    public void testLoginWithNullEmail() {
        management.login(null, user1.getPassword(), new UserLoginActions() {
            @Override
            public void studentLoginSuccess(User user) {
                fail("Student Login reached instead of receiving invalid email error.");
            }

            @Override
            public void adminLoginSuccess(User user) {
                fail("Admin Login reached instead of receiving invalid email error.");
            }

            @Override
            public void incorrectPassword(User user) {
                fail("Incorrect password error received instead of receiving invalid email error.");
            }

            @Override
            public void invalidEmail() {
                // Pass test
            }
        });
    }

    @Test
    public void testSignupUserSuccess() {
        User user = new User("student", "test student", "newmail@mail.ca",
                "abc123");
        management.signupUser(user);
        assertEquals(user, ((DatabaseMock)dbMock).getUserSynchronous(user.getEmail()));
    }

    @Test
    public void testSignupUserSameEmail() {
        User user = new User("student", "test student", user1.getEmail(),
                "");
        management.signupUser(user);
        assertNotEquals(user, ((DatabaseMock)dbMock).getUserSynchronous(user.getEmail()));
    }
}
