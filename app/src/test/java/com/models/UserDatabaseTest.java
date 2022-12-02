//package com.models;
//
//import com.google.firebase.database.DatabaseReference;
//import com.presenters.UserManagement;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//@RunWith(MockitoJUnitRunner.class)
//public class UserDatabaseTest {
//
//
//    private UserManagement userManagement;
//    @Mock
//    UserDatabaseInterface connection;
//
//
//    @Before
//    public void initMocks() {
//
//        connection = new UserDatabase();
//        userManagement = createMockSharedPreference();
//        // Create a mocked SharedPreferences that fails at saving data.
//        mMockBrokenSharedPreferencesHelper = createBrokenMockSharedPreference();
//    }
//
//    @Test
//    public void addUserTest() {
//
//    }
//
//    private UserManagement createMockSharedPreference() {
//
//        when(connection.getDbReference())
//                .thenReturn(new DatabaseReference);
//
//    }
//}
