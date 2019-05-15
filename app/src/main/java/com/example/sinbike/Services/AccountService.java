package com.example.sinbike.Services;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.sinbike.POJO.Account;
import com.example.sinbike.Repositories.AccountRepository;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;

public class AccountService {
    private static final String TAG = "AccountService";
    private AccountRepository accountRepository;

    public AccountService(Application application){
        this.accountRepository = new AccountRepository(application);
    }

    public AccountService(Application application, String userId){
        this.accountRepository = new AccountRepository(application, userId);
    }

    public QueryLiveData<Account> getAllAccount(){
        return this.accountRepository.getAllAccount();
    }

    public QueryLiveData<Account> login(String email){
        return this.accountRepository.login(email);
    }

    public CompletionLiveData create(Account account){
        return this.accountRepository.createAccount(account);
    }

    public QueryLiveData<Account> checkEmail(String email) {
        return this.accountRepository.checkEmail(email);
    }

    public LiveData<Resource<Boolean>> update(String docId, Account account){
        return this.accountRepository.update(docId, account);
    }

    public LiveData<Resource<Boolean>> delete(String docId){
        return this.accountRepository.delete(docId);
    }

    public LiveData<Resource<Boolean>> updateAccountStatus(String docId, Account account, int accountStatus){
        account.setStatus(accountStatus);
        return this.accountRepository.update(docId, account);
    }
}
