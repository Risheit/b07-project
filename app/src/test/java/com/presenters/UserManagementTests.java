// TODO: Fix Tests

//package com.presenters;
//
//import com.models.UserDatabase;
//import com.models.UserDatabaseInterface;
//
//import org.junit.*;
//import static org.junit.Assert.*;
//
//public class UserManagementTests {
//    private UserDatabaseInterface dbMock;
//    private UserManagement management;
//    private User user1;
//
//    @Before
//    public void setUp() {
//        dbMock = new DatabaseMock();
//        management = new UserManagement(dbMock);
//
//        user1 = new User("student", "Charles Mominjay", "charlesmj@gmail.com",
//                "password123");
//
//        dbMock.addUser(user1);
//    }
//
//    @Test
//    public void testVerifySuccess() {
//        assertTrue(management.verify(user1));
//    }
//
//    @Test
//    public void testVerifyFailure() {
//        User user = new User("student", "", "charlesmj@gmail.com", "");
//        assertFalse(management.verify(user));
//    }
//
//    @Test
//    public void testVerifyWithNullPassword() {
//        User user = new User("student", "", "charlesmj@gmail.com", null);
//        assertFalse(management.verify(user));
//    }
//
//    @Test
//    public void testVerifyWithNullUser() {
//        assertFalse(management.verify(null));
//    }
//
//    @Test
//    public void testSignupUserSuccess() {
//        User user = new User("student", "test student", "newmail@mail.ca",
//                "abc123");
//        management.signupUser(user);
//        assertEquals(user, dbMock.getUser(user.getEmail()));
//    }
//
//    @Test
//    public void testSignupUserSuccessReturnsTrue() {
//        User user = new User("student", "test student", "newmail@mail.ca",
//                "abc123");
//        assertTrue(management.signupUser(user));
//    }
//
//    @Test
//    public void testSignupUserFails() {
//        assertFalse(management.signupUser(user1));
//    }
//
//    @Test
//    public void testSignupUserSameEmail() {
//        User user = new User("student", "test student", "charlesmj@gmail.com",
//                "");
//        management.signupUser(user);
//        assertNotEquals(user, dbMock.getUser(user.getEmail()));
//    }
//
//    @Test
//    public void testSignupUserFailureReturnsFalse() {
//        User user = new User("student", "test student", "charlesmj@gmail.com",
//                "");
//        assertFalse(management.signupUser(user));
//    }
//}
