package com.rpgapp.devapp.rpgapp.DataAccessManager.UserRequests;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rpgapp.devapp.rpgapp.Model.User;

import java.util.ArrayList;

public class UserRequestManager {
    public interface OnSaveUser {
        void onUserSaved();
    }

    public interface OnUserSearchComplete {
        void onUserSearchComplete(ArrayList<User> user);
    }

    public static void saveUser(User user, final OnSaveUser callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .document(user.getId())
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        callback.onUserSaved();
                    }
                });
    }

    public static void searchUserByName(String name, final OnUserSearchComplete callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final ArrayList<User> users = new ArrayList<>();
        db
                .collection("Users")
                .orderBy("name")
                .startAt(name)
                .endAt(name + "\uf8ff")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                users.add(document.toObject(User.class));
                            }
                            callback.onUserSearchComplete(users);
                        }
                    }
                });
    }
}
