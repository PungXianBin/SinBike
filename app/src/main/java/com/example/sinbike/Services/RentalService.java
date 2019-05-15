package com.example.sinbike.Services;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.sinbike.POJO.Rental;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.RentalRepository;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;

public class RentalService {
    private static final String TAG = "RentalService";
    private RentalRepository rentalRepository;

    public RentalService(Application application){
        this.rentalRepository = new RentalRepository(application);
    }

    public QueryLiveData<Rental> getAllRentalByUser(String id){
        return this.rentalRepository.getAllRentalByUser(id);
    }

    public CompletionLiveData create(Rental rental){
        return this.rentalRepository.createNewRental(rental);
    }

    public LiveData<Resource<Boolean>> update(String docId, Rental rental){
        return this.rentalRepository.updateRental(docId, rental);
    }

    public LiveData<Resource<Boolean>> delete(String docId){
        return this.rentalRepository.deleteRental(docId);
    }
}
