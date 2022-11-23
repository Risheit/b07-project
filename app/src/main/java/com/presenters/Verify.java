package com.presenters;

import com.models.UserDatabaseInterface;

public interface Verify {
    boolean verify(UserDatabaseInterface db, User user);
}
