package com.models;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
        /*
        addUser() adds a user with the given properties to the database with a unique key

        the unique key is read from the database's "uniqueVal" value that is then incremented to
        ensure that it will always have a different value each time it is read
         */
        ref.child("uniqueVal").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("UserDatabase", "Error reading uniqueVal", task.getException());
                } else {
                    // if we get here, then there has been no error reading "uniqueVal" (ie the task
                    // was successful)
                    // first we read the "uniqueVal" value in the database
                    int un = task.getResult().getValue(Integer.class);

                    // increment the uniqueVal value in the database so that it will be unique the
                    // next time we call it
                    ref.child("uniqueVal").setValue(un + 1);

                    // now we can add the user with our new unique key
                    ref.child("users").child(String.valueOf(un)).setValue(user);
                    /*
                    note: the above line MUST be inside this else{} statement so that we can add
                    the user to the database AFTER we read the uniqueVal value into un.

                    i.e., if the line is outside of the onComplete() method, the user will likely
                    be added BEFORE the task is completed and before it is assigned a value,
                    resulting in unwanted behaviour. this is because the OnCompleteListener runs
                    asynchronously to the rest of the code (the rest of the addUser() method does
                    not wait for the task to finish before running)
                     */
                }
            }
        });
    }

    public User getUser(String email){
        DatabaseReference userSearch = ref.child("users").child("email");
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
        /*
        removeUser() deletes the users with the given email from the database

        by design, there should only be one user per email, but the design of the function deletes
        all users with the given email regardless.
         */
        Query userQuery = ref.child("users").orderByChild("email").equalTo(emailKey);

        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s: snapshot.getChildren()) {
                    s.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("UserDatabase", "Error deleting the user");
            }
        });
    }


}


