package com.example.sinbike.Repositories.common;

/**
 * Dependencies
 */

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles the query live data from firebase.
 * @param <T>
 */
public final class QueryLiveData<T extends Model>
        extends LiveData<Resource<List<T>>> implements EventListener<QuerySnapshot> {

    /**
     * Declaration of variables
     */
    private final Query query;
    private final Class<T> type;
    private ListenerRegistration registration;

    /**
     * Constructor
     * @param query
     * @param type
     */
    public QueryLiveData(Query query, Class<T> type) {
        this.query = query;
        this.type = type;
    }

    /**
     * Overriden onEvent function to be triggered when an event happened.
     * @param snapshots
     * @param e
     */
    @Override
    public void onEvent(QuerySnapshot snapshots, FirebaseFirestoreException e) {
        if (e != null) {
            setValue(new Resource<>(e));
            return;
        }
        setValue(new Resource<>(documentToList(snapshots)));
    }

    /**
     * Overriden onActive function detects the user is active.
     */
    @Override
    protected void onActive() {
        super.onActive();
        registration = query.addSnapshotListener(this);
    }

    /**
     * Overriden onInactive function that detects theat the user is inactive.
     */
    @Override
    protected void onInactive() {
        super.onInactive();
        if (registration != null) {
            registration.remove();
            registration = null;
        }
    }

    /**
     * Function to convert document to list.
     * @param snapshots
     * @return
     */
    @NonNull
    private List<T> documentToList(QuerySnapshot snapshots) {
        final List<T> retList = new ArrayList<>();
        if (snapshots.isEmpty()) {
            return retList;
        }

        for (DocumentSnapshot document : snapshots.getDocuments()) {
            Log.d("QueryLiveData", "documentToList: document: " + document.toString());
            retList.add(document.toObject(type).withId(document.getId()));
        }

        return retList;
    }
}
