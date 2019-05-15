package com.example.sinbike.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sinbike.POJO.Fine;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Services.FineService;

import java.util.List;

public class FineViewModel extends AndroidViewModel {
    private static final String TAG = "FineViewModel";

    private FineService fineService;

    public FineViewModel(Application application){
        super(application);
        this.fineService = new FineService(application);
    }

    public LiveData<com.example.sinbike.Repositories.common.Resource<List<Fine>>> getAllFine(String accountId) {
        final LiveData<com.example.sinbike.Repositories.common.Resource<List<Fine>>> liveobs = this.fineService.getAllFine(accountId);
        return liveobs;
    }

    public CompletionLiveData createFine(Fine fine){

        return this.fineService.createFine(fine);
    }

    public LiveData<Resource<Boolean>> updateFine(String docId, Fine fine){
        return this.fineService.updateFine(docId, fine);
    }

    public LiveData<Resource<Boolean>> deleteFine(String docId){
        return this.fineService.deleteFine(docId);
    }
}
