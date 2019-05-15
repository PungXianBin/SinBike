package com.example.sinbike.Services;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.sinbike.POJO.Transaction;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.TransactionRepository;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;

public class TransactionService {
    private static final String TAG = "TransactionService";
    private TransactionRepository transactionRepository;

    public TransactionService(Application application){
        this.transactionRepository = new TransactionRepository(application);
    }

    public QueryLiveData<Transaction> getAllTransactionOfUser(String accountId){
        return this.transactionRepository.getAllTransactionOfUser(accountId);
    }

    public CompletionLiveData create(Transaction transaction){
        return this.transactionRepository.createTransaction(transaction);
    }

    public LiveData<Resource<Boolean>> update(String docId, Transaction transaction){
        return this.transactionRepository.updateTransaction(docId, transaction);
    }

    public LiveData<Resource<Boolean>> delete(String docId){
        return this.transactionRepository.deleteTransaction(docId);
    }
}
