package com.example.sinbike.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.sinbike.Observers.TransactionViewModelObserver;
import com.example.sinbike.POJO.Account;
import com.example.sinbike.POJO.Transaction;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Services.TransactionService;

import java.util.List;

public class TransactionViewModel extends AndroidViewModel
{
    private static final String TAG = "TransactionViewModel";

    private LifecycleOwner lifecycleOwner;
    private TransactionService transactionService;
    private TransactionViewModelObserver observer;
    private String accountId;
    private static Transaction transaction;
    private Account account;

    public TransactionViewModel(Application application){
        super(application);
        this.transactionService = new TransactionService(application);
    }

    public void setObserver(TransactionViewModelObserver observer) {
        this.observer = observer;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    public void setAccountId(String accountId){
        this.accountId = accountId;
    }

    public LiveData<com.example.sinbike.Repositories.common.Resource<List<Transaction>>> getAllTransaction(String accountId) {
        final LiveData<com.example.sinbike.Repositories.common.Resource<List<Transaction>>> liveobs = this.transactionService.getAllTransactionOfUser(accountId);
        return liveobs;
    }

    public void getAllTransactionList(){
        final LiveData<com.example.sinbike.Repositories.common.Resource<List<Transaction>>> liveobs = this.transactionService.getAllTransactionOfUser(accountId);
        Observer obs = new Observer<com.example.sinbike.Repositories.common.Resource<List<Transaction>>>(){
            @Override
            public void onChanged(com.example.sinbike.Repositories.common.Resource<List<Transaction>> transactionList) {
                List<Transaction> transactions = transactionList.data();

                Log.d(TAG, transactions.toString());
                observer.showAllTransaction(transactions);
            }
        };
        liveobs.observe(this.lifecycleOwner, obs);
    }

    public CompletionLiveData create(Transaction transaction) {
        return this.transactionService.create(transaction);
    }

    public LiveData<Resource<Boolean>> update (Transaction tempTransaction){
        transaction = tempTransaction;
        return this.transactionService.update(account.id, transaction);
    }
}
