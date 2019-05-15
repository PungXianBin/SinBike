package com.example.sinbike.Repositories.common;

/**
 * Dependencies
 */

import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

/**
 * Class that handles DocumentLiveData
 * @param <T>
 */
public class DocumentLiveData<T> extends LiveData<Resource<T>>
        implements EventListener<DocumentSnapshot> {

    /**
     * Variables needed for the class to work.
     */
    private final Class<T> type;
    private final DocumentReference ref;
    private ListenerRegistration registration;

    /**
     * Constructor
     * @param ref
     * @param type
     */
    public DocumentLiveData(DocumentReference ref, Class<T> type) {
        this.ref = ref;
        this.type = type;
    }

    /**
     * Overriden onEvent function happens when there is an event trigger
     * @param snapshot
     * @param e
     */
    @Override
    public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
        if (e != null) {
            setValue(new Resource<>(e));
            return;
        }
        setValue(new Resource<>(snapshot.toObject(type)));
    }

    /**
     * Overriden onActive function that detects that the user are active.
     */
    @Override
    protected void onActive() {
        super.onActive();
        registration = ref.addSnapshotListener(this);
    }

    /**
     * Overriden onInactive function that detects the user are inactive.
     */
    @Override
    protected void onInactive() {
        super.onInactive();
        if (registration != null) {
            registration.remove();
            registration = null;
        }
    }
}