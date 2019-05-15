package com.example.sinbike.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sinbike.POJO.Account;
import com.example.sinbike.Repositories.Firestore.FirestoreRepository;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

import static com.example.sinbike.Constants.COLLECTION_ACCOUNT;

public class AccountRepository extends FirestoreRepository<Account> {

    private static final String TAG = "AccountRepository";

    private static final Class<Account> CLASS_TYPE = Account.class;
    private MutableLiveData<Account> account;
    private String userId;

    public AccountRepository(Application application) {
        super(application, CLASS_TYPE);
        CollectionReference collectionReference = firestore.collection(COLLECTION_ACCOUNT);
        super.setCollectionReference(collectionReference);
        this.account = new MutableLiveData<>();
    }

    public AccountRepository(Application application, String userId) {
        super(application, CLASS_TYPE);
        this.userId = userId;
        CollectionReference collectionReference = firestore.collection(COLLECTION_ACCOUNT);
        super.setCollectionReference(collectionReference);
        this.account = new MutableLiveData<>();
    }

    private Query query() {
        return super.collectionReference;
    }

    public QueryLiveData<Account> getAllAccount() {
        return new QueryLiveData<>(query(),
                Account.class);
    }

    public QueryLiveData<Account> login(String email) {
        return new QueryLiveData<>(query().whereEqualTo("email", email),
                Account.class);
    }

    public QueryLiveData<Account> checkEmail(String email) {
        return new QueryLiveData<>(query().whereEqualTo("email", email),
                Account.class);
    }

    public CompletionLiveData createAccount(Account account){
        // Creates an instance to the authentication service in firebase.
       // FirebaseAuth.getInstance().createUserWithEmailAndPassword(account.getEmail(), account.getPassword());

        final CompletionLiveData completion = new CompletionLiveData();
        this.create(account).addOnCompleteListener(completion);

        return completion;
    }

    public LiveData<Resource<Boolean>> updateAccount(String docId, Account account){
        return update(docId, account);
    }

    public LiveData<Resource<Boolean>> deleteAccount(String docId){
        return delete(docId);
    }
}
