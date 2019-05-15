package com.example.sinbike.Services;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.sinbike.POJO.Fine;
import com.example.sinbike.Repositories.FineRepository;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;

public class FineService {
    private static final String TAG = "FineService";
    private FineRepository fineRepository;

    public FineService(Application application){
        this.fineRepository = new FineRepository(application);
    }

    public FineService(Application application, String userId){
        this.fineRepository = new FineRepository(application, userId);
    }

    public QueryLiveData<Fine> getAllFine(String accountId) {
        return this.fineRepository.getAllFine(accountId);
    }

    public CompletionLiveData createFine(Fine fine){
        return this.fineRepository.createFine(fine);
    }

    public LiveData<Resource<Boolean>> updateFine(String docId, Fine fine){
        return this.fineRepository.updateFine(docId, fine);
    }

    public LiveData<Resource<Boolean>> deleteFine(String docId){
        return this.fineRepository.deleteFine(docId);
    }
}
