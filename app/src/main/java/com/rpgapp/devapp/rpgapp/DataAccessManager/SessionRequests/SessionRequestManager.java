package com.rpgapp.devapp.rpgapp.DataAccessManager.SessionRequests;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.Model.Session;

import javax.annotation.Nullable;

public class SessionRequestManager {

    public interface ObservesSession {
        void OnChangeInSession(Session se);
    }


    static public void watchSession(final ObservesSession observer) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("sessions").document("session").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Session se = documentSnapshot.toObject(Session.class);
                observer.OnChangeInSession(se);
            }
        });



    }

    static public void watchAdventureSession(final ObservesSession observer, String adventureId, final int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("adventures").document(adventureId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Adventure ad = documentSnapshot.toObject(Adventure.class);


                Session se = ad.getSessions().get(position);
                observer.OnChangeInSession(se);
            }
        });



    }

    static public void updateSession(Session se) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("sessions").document("session").set(se);
    }
}
