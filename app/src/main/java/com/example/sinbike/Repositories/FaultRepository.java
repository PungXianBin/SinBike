package com.example.sinbike.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.sinbike.POJO.Fault;
import com.example.sinbike.Repositories.Firestore.FirestoreRepository;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

import static com.example.sinbike.Constants.COLLECTION_FAULT;

public class FaultRepository extends FirestoreRepository<Fault> {

    private static final String TAG = "FaultRepository";
    private static final Class<Fault> CLASS_TYPE = Fault.class;

    public FaultRepository(Application application) {
        super(application, CLASS_TYPE);
        CollectionReference collectionReference = firestore.collection(COLLECTION_FAULT);
        super.setCollectionReference(collectionReference);
    }

    private Query query() {
        return super.collectionReference;
    }

    public QueryLiveData<Fault> getAllFaults(String accountId){
        return new QueryLiveData<>(query().whereEqualTo("accountId",accountId), Fault.class);
    }

    public CompletionLiveData createFault(Fault fault){
        final CompletionLiveData completion = new CompletionLiveData();
        this.create(fault).addOnCompleteListener(completion);

        return completion;
    }

    public LiveData<Resource<Boolean>> updateFault(String docId, Fault fault){
        return update(docId, fault);
    }

    public LiveData<Resource<Boolean>> deleteFault(String docId){
        return delete(docId);
    }

}
