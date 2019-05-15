package com.example.sinbike.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sinbike.POJO.Transaction;
import com.example.sinbike.Repositories.Firestore.FirestoreRepository;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

import static com.example.sinbike.Constants.COLLECTION_TRANSACTION;

public class TransactionRepository extends FirestoreRepository<Transaction> {

    private static final String TAG = "TransactionRepository";
    private static final Class<Transaction> CLASS_TYPE = Transaction.class;
    private MutableLiveData<Transaction> transaction;

    public TransactionRepository(Application application) {
        super(application, CLASS_TYPE);
        CollectionReference collectionReference = firestore.collection(COLLECTION_TRANSACTION);
        super.setCollectionReference(collectionReference);
        this.transaction = new MutableLiveData<>();
    }

    private Query query() {
        return super.collectionReference;
    }

    public QueryLiveData<Transaction> getAllTransactionOfUser(String accountId) {
        return new QueryLiveData<>(query().whereEqualTo("accountId", accountId),
                Transaction.class);
    }

    public CompletionLiveData createTransaction(Transaction transaction){
        final CompletionLiveData completion = new CompletionLiveData();
        this.create(transaction).addOnCompleteListener(completion);

        return completion;
    }

    public LiveData<Resource<Boolean>> updateTransaction(String docId, Transaction transaction){
        return update(docId, transaction);
    }

    public LiveData<Resource<Boolean>> deleteTransaction(String docId){
        return delete(docId);
    }
}
