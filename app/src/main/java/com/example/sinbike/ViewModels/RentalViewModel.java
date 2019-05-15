package com.example.sinbike.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.sinbike.Observers.RentalViewModelObserver;
import com.example.sinbike.POJO.Rental;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Services.RentalService;

import java.util.List;

public class RentalViewModel extends AndroidViewModel
{
    private static final String TAG = "RentalViewModel";

    private LifecycleOwner lifecycleOwner;
    private RentalService rentalService;
    private RentalViewModelObserver observer;

    public RentalViewModel(Application application){
        super(application);
        this.rentalService = new RentalService(application);
    }

    public void setObserver(RentalViewModelObserver observer) {
        this.observer = observer;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    public void getRentalsOfUser(String accountId){
        final LiveData<com.example.sinbike.Repositories.common.Resource<List<Rental>>> liveobs = this.rentalService.getAllRentalByUser(accountId);
        Observer obs = new Observer<com.example.sinbike.Repositories.common.Resource<List<Rental>>>(){
            @Override
            public void onChanged(com.example.sinbike.Repositories.common.Resource<List<Rental>> rentalList) {
                List<Rental> parkingLots = rentalList.data();

                Log.d(TAG, parkingLots.toString());

                observer.showRentalList(parkingLots);
            }
        };
        liveobs.observe(this.lifecycleOwner, obs);
    }

    public CompletionLiveData createRental(Rental rental){

        return this.rentalService.create(rental);
    }
}
