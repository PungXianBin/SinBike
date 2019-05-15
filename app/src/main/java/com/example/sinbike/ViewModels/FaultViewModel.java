package com.example.sinbike.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sinbike.POJO.Fault;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;
import com.example.sinbike.Services.FaultService;

import java.util.List;

public class FaultViewModel extends AndroidViewModel {

    private static final String TAG = "FaultViewModel";

    private FaultService faultService;

    public FaultViewModel(Application application) {
        super(application);
        this.faultService = new FaultService(application);
    }

    public QueryLiveData<Fault> get(String accountId) {
        return this.faultService.get(accountId);
    }

    public CompletionLiveData create(Fault fault) {
        return this.faultService.create(fault);
    }

    public LiveData<Resource<Boolean>> update(String docId, Fault fault){
        return this.faultService.update(docId, fault);
    }

    public LiveData<Resource<Boolean>> delete(String docId){
        return this.faultService.delete(docId);
    }

    public CompletionLiveData submitFault(String bicycleId, List<String> image, String category, String description){
        return this.faultService.submitFault(bicycleId, image, category, description);
    }
}
