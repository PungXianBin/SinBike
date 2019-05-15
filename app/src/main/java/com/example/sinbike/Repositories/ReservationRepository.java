package com.example.sinbike.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sinbike.POJO.Reservation;
import com.example.sinbike.Repositories.Firestore.FirestoreRepository;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

import static com.example.sinbike.Constants.COLLECTION_RESERVATION;

public class ReservationRepository extends FirestoreRepository<Reservation> {

    private static final String TAG = "ReservationRepository";

    private static final Class<Reservation> CLASS_TYPE = Reservation.class;
    private MutableLiveData<Reservation> reservation;

    public ReservationRepository(Application application) {
        super(application, CLASS_TYPE);
        CollectionReference collectionReference = firestore.collection(COLLECTION_RESERVATION);
        super.setCollectionReference(collectionReference);
        this.reservation = new MutableLiveData<>();
    }

    private Query query() {
        return super.collectionReference;
    }

    public QueryLiveData<Reservation> getAllReservationByUser(String accountId) {
        return new QueryLiveData<>(query().whereEqualTo("accountId", accountId),
                Reservation.class);
    }

    public CompletionLiveData createNewReservation(Reservation reservation){
        final CompletionLiveData completion = new CompletionLiveData();
        this.create(reservation).addOnCompleteListener(completion);

        return completion;
    }

    public LiveData<Resource<Boolean>> updateReservation(String docId, Reservation reservation){
        return update(docId, reservation);
    }

    public LiveData<Resource<Boolean>> deleteReservation(String docId){
        return delete(docId);
    }
}
