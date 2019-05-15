package com.example.sinbike.Services;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.sinbike.POJO.Fault;
import com.example.sinbike.Repositories.FaultRepository;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;

import java.util.List;

public class FaultService {
    private static final String TAG = "FaultService";
    private FaultRepository faultRepository;

    public FaultService(Application application){
        this.faultRepository = new FaultRepository(application);
    }

    public QueryLiveData<Fault> get(String accountId){
        return this.faultRepository.getAllFaults(accountId);
    }

    public CompletionLiveData create(Fault fault){
        return this.faultRepository.createFault(fault);
    }

    public LiveData<Resource<Boolean>> update(String docId, Fault fault){
        return this.faultRepository.updateFault(docId, fault);
    }

    public LiveData<Resource<Boolean>> delete(String docId){
        return this.faultRepository.deleteFault(docId);
    }

    public CompletionLiveData submitFault(String bicycleId, List<String> image, String category, String description){
        Fault fault = new Fault();
        fault.setBicycleId(bicycleId);
        fault.setImage(image);
        fault.setCategory(category);
        fault.setDescription(description);

        return this.create(fault);
    }
}
