package com.models;

import static android.content.ContentValues.TAG;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.presenters.User;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.Query;
import com.google.firebase.database.DatabaseReference;

public class UserDatabase implements UserDatabaseInterface {
    private final DatabaseReference ref = FirebaseDatabase.getInstance("https://b07-project-e5893-default-rtdb.firebaseio.com/").getReference();
    User u = null;

    public void addUser(User user){
        ref.child("users").child(user.getEmail()).setValue(user);
    }

    public User getUser(String email){
        DatabaseReference userSearch = ref.child("users").child(email);
        userSearch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                u = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });

        return u;
    }

    public void removeUser(String emailKey){
        DatabaseReference userSearch = ref.child("users").child(emailKey);
        userSearch.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot){
                for (DataSnapshot deletedUser: snapshot.getChildren()){
                    deletedUser.getRef().removeValue();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError){
                Log.e(TAG, "No user found", databaseError.toException());
            }
        });
    }


}


