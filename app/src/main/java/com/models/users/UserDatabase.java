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
        // dbRefString = "https://b07-project-e5893-default-rtdb.firebaseio.com/"
        this.ref =  FirebaseDatabase.getInstance(dbRefString).getReference();
    }

//    public void addUser(User user){
//        /*
//        addUser() adds a user with the given properties to the database with a unique key
//
//        the unique key is read from the database's "uniqueVal" value that is then incremented to
//        ensure that it will always have a different value each time it is read
//         */
//
//        ref.child("uniqueVal").get().addOnCompleteListener(task -> {
//            if (!task.isSuccessful()) {
//                Log.e("UserDatabase", "Error reading uniqueVal", task.getException());
//            } else {
//                // if we get here, then there has been no error reading "uniqueVal" (ie the task
//                // was successful)
//                // first we read the "uniqueVal" value in the database
//                int un = task.getResult().getValue(Integer.class);
//
//                // increment the uniqueVal value in the database so that it will be unique the
//                // next time we call it
//                ref.child("uniqueVal").setValue(un + 1);
//
//                // now we can add the user with our new unique key
//                ref.child("users").child(String.valueOf(un)).setValue(user);
//                /*
//                note: the above line MUST be inside this else{} statement so that we can add
//                the user to the database AFTER we read the uniqueVal value into un.
//
//                i.e., if the line is outside of the onComplete() method, the user will likely
//                be added BEFORE the task is completed and before it is assigned a value,
//                resulting in unwanted behaviour. this is because the OnCompleteListener runs
//                asynchronously to the rest of the code (the rest of the addUser() method does
//                not wait for the task to finish before running)
//                 */
//            }
//        });
//    }


    public void addUser(User user) {
        String commaEmail = user.getEmail().replace('.', ',');
        ref.child(userSection).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChild(commaEmail)) {
                    Log.i("UserDatabase", "Database to be modified in addUser");
                    ref.child(userSection).child(commaEmail).setValue(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
                Log.e("UserDatabase", "Error Adding the user");
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

    public void editUser(User user, String emailKey) {
        /*
        editUser() edits the user with the corresponding emailKey with the properties
        of user
         */
        Query userQuery = ref.child(userSection).orderByChild("email").equalTo(emailKey);

        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s: snapshot.getChildren()) {
                    s.getRef().setValue(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("UserDatabase", "Error deleting the user");
            }
        });
    }

    public void removeUser(String emailKey){
        /*
        removeUser() deletes the users with the given email from the database

        by design, there should only be one user per email, but the design of the function deletes
        all users with the given email regardless.
         */
        Query userQuery = ref.child(userSection).orderByChild("email").equalTo(emailKey);

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


