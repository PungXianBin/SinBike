package com.example.sinbike.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sinbike.POJO.ParkingLot;
import com.example.sinbike.Repositories.Firestore.FirestoreRepository;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

import static com.example.sinbike.Constants.COLLECTION_PARKING_LOT;

public class ParkingLotRepository extends FirestoreRepository<ParkingLot> {

    private static final String TAG = "ParkingLotRepository";

    private static final Class<ParkingLot> CLASS_TYPE = ParkingLot.class;
    private MutableLiveData<ParkingLot> parkinglot;

    public ParkingLotRepository(Application application) {
        super(application, CLASS_TYPE);
        CollectionReference collectionReference = firestore.collection(COLLECTION_PARKING_LOT);
        super.setCollectionReference(collectionReference);
        this.parkinglot = new MutableLiveData<>();
    }

    private Query query() {
        return super.collectionReference;
    }

    public QueryLiveData<ParkingLot> getAllParkingLot() {
        return new QueryLiveData<>(query(),
                ParkingLot.class);
    }

    public CompletionLiveData createParkingLot(ParkingLot parkingLot){
        final CompletionLiveData completion = new CompletionLiveData();
        this.create(parkingLot).addOnCompleteListener(completion);

        return completion;
    }

    public LiveData<Resource<Boolean>> updateParkingLot(String docId, ParkingLot parkingLot){
        return update(docId, parkingLot);
    }

    public LiveData<Resource<Boolean>> deleteParkingLot(String docId){
        return delete(docId);
    }
}
