package com.example.sinbike.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sinbike.POJO.Payment;
import com.example.sinbike.POJO.RentalPayment;
import com.example.sinbike.Repositories.Firestore.FirestoreRepository;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

import static com.example.sinbike.Constants.COLLECTION_RENTAL_PAYMENT;

public class RentalPaymentRepository extends FirestoreRepository<RentalPayment> {

    private static final String TAG = "RentalPaymentRepository";

    private static final Class<RentalPayment> CLASS_TYPE = RentalPayment.class;
    private MutableLiveData<RentalPayment> RentalPayment;
    private String userId;

    public RentalPaymentRepository(Application application) {
        super(application, CLASS_TYPE);
        CollectionReference collectionReference = firestore.collection(COLLECTION_RENTAL_PAYMENT);
        super.setCollectionReference(collectionReference);
        this.RentalPayment = new MutableLiveData<>();
    }

    public RentalPaymentRepository(Application application, String userId) {
        super(application, CLASS_TYPE);
        this.userId = userId;
        CollectionReference collectionReference = firestore.collection(COLLECTION_RENTAL_PAYMENT);
        super.setCollectionReference(collectionReference);
        this.RentalPayment = new MutableLiveData<>();
    }

    private Query query() {
        return super.collectionReference;
    }

    public QueryLiveData<Payment> getAllPayment(String accountId) {
        return new QueryLiveData<>(query().whereEqualTo("accountId", accountId),
                Payment.class);
    }

    public CompletionLiveData createPayment(RentalPayment payment) {
        final CompletionLiveData completion = new CompletionLiveData();
        this.create(payment).addOnCompleteListener(completion);

        return completion;
    }

    public LiveData<Resource<Boolean>> updatePayment(String docId, RentalPayment payment) {
        return update(docId, payment);
    }

    public LiveData<Resource<Boolean>> deletePayment(String docId) {
        return delete(docId);
    }
}
