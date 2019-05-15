package com.example.sinbike.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sinbike.POJO.Bicycle;
import com.example.sinbike.Repositories.Firestore.FirestoreRepository;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

import static com.example.sinbike.Constants.COLLECTION_BICYCLE;

public class BicycleRepository extends FirestoreRepository<Bicycle> {

    private static final String TAG = "BicycleRepository";

    private static final Class<Bicycle> CLASS_TYPE = Bicycle.class;
    private MutableLiveData<Bicycle> bicycle;

    public BicycleRepository(Application application) {
        super(application, CLASS_TYPE);
        CollectionReference collectionReference = firestore.collection(COLLECTION_BICYCLE);
        super.setCollectionReference(collectionReference);
        this.bicycle = new MutableLiveData<>();
    }

    private Query query() {
        return super.collectionReference;
    }

    public QueryLiveData<Bicycle> getAllBicycle() {
        return new QueryLiveData<>(query(),
                Bicycle.class);
    }


    public CompletionLiveData createBicycle(Bicycle bicycle){
        final CompletionLiveData completion = new CompletionLiveData();
        this.create(bicycle).addOnCompleteListener(completion);

        return completion;
    }

    public LiveData<Resource<Boolean>> updateBicycle(String docId, Bicycle bicycle){
        return update(docId, bicycle);
    }

    public LiveData<Resource<Boolean>> deleteBicycle(String docId){
        return delete(docId);
    }
}
