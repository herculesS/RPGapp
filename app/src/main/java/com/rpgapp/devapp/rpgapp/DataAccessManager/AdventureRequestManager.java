package com.rpgapp.devapp.rpgapp.DataAccessManager;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rpgapp.devapp.rpgapp.Model.Adventure;

import java.util.ArrayList;

public class AdventureRequestManager {

    public interface OnAdventuresLoaded {
        void onComplete(ArrayList<Adventure> adventures);
    }

    public interface OnAdventureAdded {
        void onAdded();
    }

    static public void addAdventure(Adventure ad, final OnAdventureAdded callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("adventures").add(ad).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                callback.onAdded();
            }
        });
    }

    static public void getAdventures(final OnAdventuresLoaded callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final ArrayList<Adventure> toReturn = new ArrayList<>();
        db.collection("adventures").orderBy("title").
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Adventure a = document.toObject(Adventure.class);
                        a.setId(document.getId());
                        toReturn.add(a);
                    }

                    callback.onComplete(toReturn);

                }
            }
        });


    }

    static public void seed() {


        for (int i = 0; i < 10; i++) {
            Adventure a = new Adventure();
            a.setTitle("test " + i);
            a.setDescription("a description " + i);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("adventures").add(a);

        }

    }


}
