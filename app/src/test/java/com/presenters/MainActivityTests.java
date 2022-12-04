package com.presenters;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.text.Editable;
import android.widget.EditText;

import com.models.UserDatabaseMock;
import com.models.users.User;
import com.planner.MainActivity;

import org.junit.Before;
import org.junit.Test;

/**
 * MainActivity unit tests, which execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MainActivityTests {
    private MainActivity view;
    private MainPresenter presenter;

    private Editable emailReturn;
    private Editable passwordReturn;

    private User user1, user2;

    @Before
    public void setUp() {
        // Initialize Mocks
        UserDatabaseMock model = new UserDatabaseMock();
        EditText emailInput = mock(EditText.class);
        EditText passwordInput = mock(EditText.class);

        emailReturn = mock(Editable.class);
        passwordReturn = mock(Editable.class);

        view = mock(MainActivity.class);
        presenter = new MainPresenter(view, model);

        user1 = new User("Student", "Charles Mominjay", "charlesmj@gmail.com",
                "password123");
        user2 = new User("Admin", "Admin Man", "admin@adminmail.ca",
                "ComplexAdminPassword");

        model.addUser(user1);
        model.addUser(user2);

        // Setup Default Mocks
        when(view.getEmailInput()).thenReturn(emailInput);
        when(view.getPasswordInput()).thenReturn(passwordInput);
        when(emailInput.getText()).thenReturn(emailReturn);
        when(passwordInput.getText()).thenReturn(passwordReturn);
    }

    @Test
    public void testLoginWithStudentLogin() {
        // Setup Mocks
        when(emailReturn.toString()).thenReturn(user1.getEmail());
        when(passwordReturn.toString()).thenReturn(user1.getPassword());

        presenter.onLoginButtonClicked();

        // Check that Student Page opened
        verify(view).openStudentHomepage(view, user1.getName());
    }

    @Test
    public void testLoginWithAdminLogin() {
        // Setup Mocks
        when(emailReturn.toString()).thenReturn(user2.getEmail());
        when(passwordReturn.toString()).thenReturn(user2.getPassword());

        presenter.onLoginButtonClicked();

        // Check that Admin Page opened
        verify(view).openAdminHomepage(view, user2.getName());
    }

    @Test
    public void testLoginWithInvalidEmail() {
        // Setup Mocks
        when(emailReturn.toString()).thenReturn("invalidEmail@mail");
        when(passwordReturn.toString()).thenReturn(user1.getPassword());

        presenter.onLoginButtonClicked();

        // Check Error displayed
        verify(view).displayErrorNotification(view, "Invalid Email");
    }

    @Test
    public void testLoginWithInvalidPassword() {
        // Setup Mocks
        when(emailReturn.toString()).thenReturn(user1.getEmail());
        when(passwordReturn.toString()).thenReturn(user2.getPassword());

        presenter.onLoginButtonClicked();

        // Check Error displayed
        verify(view).displayErrorNotification(view,"Incorrect Password");
    }

    @Test
    public void testLoginWithEmptyEmail() {
        // Setup Mocks
        when(emailReturn.toString()).thenReturn("");
        when(passwordReturn.toString()).thenReturn(user1.getPassword());

        presenter.onLoginButtonClicked();

        // Check Error displayed
        verify(view).displayErrorNotification(view,"Please Enter All Possible Fields");
    }

    @Test
    public void testSignUp() {
        presenter.onSignUpButtonClicked();

        // Check that Sign Up page opened
        verify(view).openSignUpPage(view);
    }
}