package com.rpgapp.devapp.rpgapp.DataAccessManager.SessionRequests;

import android.support.annotation.Nullable;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.Model.Session;

public class SessionManager {
    private static final SessionManager ourInstance = new SessionManager();

    public interface ObservesSession {
        void OnChangeInSession(Session se);
        void OnSessionNotFound();
    }
    private Session openSession;

    public static SessionManager getInstance() {
        return ourInstance;
    }

    private SessionManager() {
    }


    public void watchSession(final ObservesSession observer, Adventure ad, final int position) {

        openSession = ad.getSessions().get(position);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("adventures").document(ad.getId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    Adventure adventure = documentSnapshot.toObject(Adventure.class);
                    Session se = adventure.getSessions().get(position);
                    observer.OnChangeInSession(se);

                } else {
                    observer.OnSessionNotFound();
                }

            }
        });



    }
}
