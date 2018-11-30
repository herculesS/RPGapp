package com.rpgapp.devapp.rpgapp.DataAccessManager.AdventureRequests;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rpgapp.devapp.rpgapp.Model.Adventure;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class AdventureRequestManager {

    public interface OnAdventuresLoaded {
        void onComplete(ArrayList<Adventure> adventures);
    }

    public interface ObservesAdventure {
        void onChangeInAdventure(Adventure ad);
    }

    public interface OnAdventureLoaded {
        void onAdventureLoaded(Adventure a);
    }

    public interface OnAdventureAdded {
        void onAdded();
    }

    public interface OnSaveAdventure {
        void onSaved();
    }

    static public void watchAdventure(String adventureId, final ObservesAdventure observer) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db
                .collection("adventures")
                .document(adventureId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Adventure ad = documentSnapshot.toObject(Adventure.class);
                observer.onChangeInAdventure(ad);
            }
        });
    }
    static public void loadAdventure(String id, final OnAdventureLoaded callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db
                .collection("adventures")
                .document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Adventure ad = document.toObject(Adventure.class);
                            callback.onAdventureLoaded(ad);
                        } else {

                        }

                    }
                });
    }

    static public void saveAdventure(Adventure ad, final OnSaveAdventure callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Log.d("issue", "save");
        db.collection("adventures").
                document(ad.getId()).
                set(ad).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Log.d("issue", "saved");
                callback.onSaved();
            }
        });

    }

    static public void addAdventure(Adventure ad, final OnAdventureAdded callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String id = db.collection("adventures").document().getId();
        ad.setId(id);
        db.collection("adventures").document(id).set(ad).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
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
