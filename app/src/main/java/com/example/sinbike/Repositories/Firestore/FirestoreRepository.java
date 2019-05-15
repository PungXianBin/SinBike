package com.example.sinbike.Repositories.Firestore;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.sinbike.Repositories.common.Model;
import com.example.sinbike.Repositories.common.QueryLiveData;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.Objects;

/**
 * This class establishes connection with cloud fire store and sets reference to collection specified from calling class
 * Basic CRUD operations on collection provided by this generic repo class
 * @param <T> The POJO use for CRUD operations
 */
public abstract class FirestoreRepository<T extends Model> implements FirestoreRepositoryInterface<T> {
    private final static String TAG = "FirestoreRepository";
    private final Class<T> classType;
    protected FirebaseFirestore firestore;
    protected CollectionReference collectionReference;

    /**
     * @param application
     * @param classType
     */
    public FirestoreRepository(Application application, Class<T> classType) {
        Log.d(TAG, "Init firestore repo...");
        this.firestore = FirebaseFirestore.getInstance(Objects.requireNonNull(FirebaseApp.initializeApp(application)));
        this.classType = classType;
    }

    /**
     * @param collectionReference
     */
    public void setCollectionReference(CollectionReference collectionReference) {
        this.collectionReference = collectionReference;
    }

    /**
     * Inserts object into collection
     * @param obj the object to be inserted
     * @return
     */
    @Override
    public Task<Void> create(T obj) {
        return collectionReference.getFirestore().runTransaction(transaction -> {
            transaction.set(collectionReference.document(), obj);
            return null;
        });
    }

    public Task<Void> create(User user, String userUID){
        return collectionReference.getFirestore().runTransaction(transaction->{
            transaction.set(collectionReference.document(userUID),user);
            return null;
        });
    }

    /**
     * @return
     */
    @Override
    public QueryLiveData<T> readAll() {
        return new QueryLiveData<>(collectionReference, classType);
    }

    /**
     * @param id
     * @param obj
     * @return
     */
    @Override
    public MutableLiveData<Resource<Boolean>> update(String id, T obj) {
        final MutableLiveData<Resource<Boolean>> data = new MutableLiveData<>();
        collectionReference.document(id).set(obj)
                .addOnSuccessListener(documentReference -> data.postValue(Resource.success(true)))
                .addOnFailureListener(e -> data.postValue(Resource.error(e.getLocalizedMessage(), true)));
        return data;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public MutableLiveData<Resource<Boolean>> delete(String id) {
        final MutableLiveData<Resource<Boolean>> data = new MutableLiveData<>();
        collectionReference.document(id).delete()
                .addOnSuccessListener(documentReference -> data.postValue(Resource.success(true)))
                .addOnFailureListener(e -> data.postValue(Resource.error(e.getLocalizedMessage(), true)));
        return data;
    }
}