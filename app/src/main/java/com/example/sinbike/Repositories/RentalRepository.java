package com.example.sinbike.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sinbike.POJO.Rental;
import com.example.sinbike.Repositories.Firestore.FirestoreRepository;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

import static com.example.sinbike.Constants.COLLECTION_RENTAL;

public class RentalRepository extends FirestoreRepository<Rental> {

    private static final String TAG = "RentalRepository";

    private static final Class<Rental> CLASS_TYPE = Rental.class;
    private MutableLiveData<Rental> rental;

    public RentalRepository(Application application) {
        super(application, CLASS_TYPE);
        CollectionReference collectionReference = firestore.collection(COLLECTION_RENTAL);
        super.setCollectionReference(collectionReference);
        this.rental = new MutableLiveData<>();
    }

    private Query query() {
        return super.collectionReference;
    }

    public QueryLiveData<Rental> getAllRentalByUser(String accountId) {
        return new QueryLiveData<>(query().whereEqualTo("accountId", accountId),
                Rental.class);
    }

    public CompletionLiveData createNewRental(Rental rental){
        final CompletionLiveData completion = new CompletionLiveData();
        this.create(rental).addOnCompleteListener(completion);

        return completion;
    }

    public LiveData<Resource<Boolean>> updateRental(String docId, Rental rental){
        return update(docId, rental);
    }

    public LiveData<Resource<Boolean>> deleteRental(String docId){
        return delete(docId);
    }
}
