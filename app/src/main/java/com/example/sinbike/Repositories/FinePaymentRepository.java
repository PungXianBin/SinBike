package com.example.sinbike.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sinbike.POJO.FinePayment;
import com.example.sinbike.POJO.Payment;
import com.example.sinbike.Repositories.Firestore.FirestoreRepository;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

import static com.example.sinbike.Constants.COLLECTION_FINE_PAYMENT;

public class FinePaymentRepository extends FirestoreRepository<FinePayment> {

    private static final String TAG = "FinePaymentRepository";

    private static final Class<FinePayment> CLASS_TYPE = FinePayment.class;
    private MutableLiveData<FinePayment> finePayment;
    private String userId;

    public FinePaymentRepository(Application application) {
        super(application, CLASS_TYPE);
        CollectionReference collectionReference = firestore.collection(COLLECTION_FINE_PAYMENT);
        super.setCollectionReference(collectionReference);
        this.finePayment = new MutableLiveData<>();
    }

    public FinePaymentRepository(Application application, String userId) {
        super(application, CLASS_TYPE);
        this.userId = userId;
        CollectionReference collectionReference = firestore.collection(COLLECTION_FINE_PAYMENT);
        super.setCollectionReference(collectionReference);
        this.finePayment = new MutableLiveData<>();
    }

    private Query query() {
        return super.collectionReference;
    }

    public QueryLiveData<Payment> getAllPayment(String accountId) {
        return new QueryLiveData<>(query().whereEqualTo("accountId", accountId),
                Payment.class);
    }

    public CompletionLiveData createPayment(FinePayment payment){
        final CompletionLiveData completion = new CompletionLiveData();
        this.create(payment).addOnCompleteListener(completion);

        return completion;
    }

    public LiveData<Resource<Boolean>> updatePayment(String docId, FinePayment payment){
        return update(docId, payment);
    }

    public LiveData<Resource<Boolean>> deletePayment(String docId){
        return delete(docId);
    }
}
