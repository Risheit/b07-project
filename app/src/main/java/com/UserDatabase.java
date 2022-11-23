package com;

import static android.content.ContentValues.TAG;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.presenters.User;
import android.util.Log;
import com.google.firebase.database.Query;
import com.google.firebase.database.DatabaseReference;

public class UserDatabase {
    private DatabaseReference ref = FirebaseDatabase.getInstance("https://b07-project-e5893-default-rtdb.firebaseio.com/").getReference("users");

    public void addUser(String type, String name, String email, String password){
        User user = new User(type, name, email, password);
        ref.child("users").child("u1").setValue(user);
    }

    public void removeUser(String emailKey){
        Query userSearch = ref.child("users").orderByChild("email").equalTo(emailKey);
        userSearch.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot){
                for (DataSnapshot deletedUser: snapshot.getChildren()){
                    deletedUser.getRef().removeValue();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError){
                Log.e(TAG, "no user found", databaseError.toException());
            }
        });
    }
}
