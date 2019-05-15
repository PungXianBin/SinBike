package com.example.sinbike.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sinbike.Constants;
import com.example.sinbike.POJO.Fine;
import com.example.sinbike.Repositories.Firestore.FirestoreRepository;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

import static com.example.sinbike.Constants.COLLECTION_FINE;

public class FineRepository extends FirestoreRepository<Fine> {

    private static final String TAG = "FineRepository";

    private static final Class<Fine> CLASS_TYPE = Fine.class;
    private MutableLiveData<Fine> fine;
    private String userId;

    public FineRepository(Application application) {
        super(application, CLASS_TYPE);

        CollectionReference collectionReference = firestore.collection(COLLECTION_FINE);
        super.setCollectionReference(collectionReference);
        this.fine = new MutableLiveData<>();
    }

    public FineRepository(Application application, String userId) {
        super(application, CLASS_TYPE);
        this.userId = userId;
        CollectionReference collectionReference = firestore.collection(COLLECTION_FINE);
        super.setCollectionReference(collectionReference);
        this.fine = new MutableLiveData<>();
    }

    private Query query() {
        return super.collectionReference;
    }

    public QueryLiveData<Fine> getAllFine(String accountId) {
        return new QueryLiveData<>(query().whereEqualTo("accountId", accountId).whereEqualTo("status", Constants.FINE_NOTPAID),
                Fine.class);
    }

    public CompletionLiveData createFine(Fine fine){
        final CompletionLiveData completion = new CompletionLiveData();
        this.create(fine).addOnCompleteListener(completion);

        return completion;
    }

    public LiveData<Resource<Boolean>> updateFine(String docId, Fine fine){
        return update(docId, fine);
    }

    public LiveData<Resource<Boolean>> deleteFine(String docId){
        return delete(docId);
    }
}
