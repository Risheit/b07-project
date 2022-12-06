package com.models.users;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.models.onGetDataListener;

public class UserDatabase implements UserDatabaseInterface {

    private final DatabaseReference ref;
    private final String userSection = "users";

    public UserDatabase(String dbRefString) {
        this.ref =  FirebaseDatabase.getInstance(dbRefString).getReference();
    }

    public UserDatabase() {
        this.ref =  FirebaseDatabase
                .getInstance("https://b07-project-e5893-default-rtdb.firebaseio.com/")
                .getReference();
    }

    public void addUser(User user) {
        String commaEmail = user.getEmail().replace('.', ',');
        ref.child(userSection).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChild(commaEmail)) {
                    ref.child(userSection).child(commaEmail).setValue(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("UserDatabase", "Error Adding the user: " + error.getCode());
            }
        });
    }

    public void getUser(String email, final onGetDataListener<User> then) {
        DatabaseReference userSearch = ref.child(userSection);
        String commaEmail = email.replace('.', ',');

        userSearch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.child(commaEmail).getValue(User.class);
                then.onSuccess(u);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                then.onFailure();
                System.out.println("The read failed: " + error.getCode());
                Log.e("UserDatabase", "Error retrieving the user");
            }
        });
    }

    /**
     * Edits the user with the corresponding emailKey with the properties of user.
     * @param user New User to be copied
     * @param emailKey The email to be queried for the user that needs to be changed.
     */
    public void editUser(User user, String emailKey) {
        Query userQuery = ref.child(userSection).orderByChild("email").equalTo(emailKey);

        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildren().forEach(s -> s.getRef().setValue(user));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("UserDatabase", "Error deleting the user");
            }
        });
    }

    /**
     * Deletes the users with the given email from the database.
     *
     * By design, there should only be one user per email, but the design of the function deletes
     * all users with the given email regardless.
     * @param emailKey  The email of the user we want to delete.
     */
    public void removeUser(String emailKey){
        Query userQuery = ref.child(userSection).orderByChild("email").equalTo(emailKey);

        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildren().forEach(s -> s.getRef().removeValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("UserDatabase", "Error deleting the user");
            }
        });
    }
}


