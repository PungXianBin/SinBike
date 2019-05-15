package com.example.sinbike.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.sinbike.Observers.BicycleViewModelObserver;
import com.example.sinbike.POJO.Bicycle;
import com.example.sinbike.Services.BicycleService;
import com.google.firebase.firestore.GeoPoint;

import java.util.List;

public class BicycleViewModel extends AndroidViewModel
{
    private static final String TAG = "BicycleViewModel";

    private LifecycleOwner lifecycleOwner;
    private BicycleService bicycleService;
    private BicycleViewModelObserver observer;
    private List<Bicycle> bicycles;
    private Bicycle bicycle;

    public BicycleViewModel(Application application){
        super(application);
        this.bicycleService = new BicycleService(application);
    }

    public void setObserver(BicycleViewModelObserver observer) {
    this.observer = observer;
}

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    public Bicycle getBicycle(){
        return this.bicycle;
    }

    public LiveData<com.example.sinbike.Repositories.common.Resource<List<Bicycle>>> getAllBicycles(){
        final LiveData<com.example.sinbike.Repositories.common.Resource<List<Bicycle>>> liveobs = this.bicycleService.getAllBicycle();
        return liveobs;
    }

    public void getAllBicycle(){
        final LiveData<com.example.sinbike.Repositories.common.Resource<List<Bicycle>>> liveobs = this.bicycleService.getAllBicycle();

        Observer obs = new Observer<com.example.sinbike.Repositories.common.Resource<List<Bicycle>>>(){
            @Override
            public void onChanged(com.example.sinbike.Repositories.common.Resource<List<Bicycle>> listResource) {
                bicycles = listResource.data();

                Log.d(TAG, bicycles.toString());

                observer.showBicycleList(bicycles);
                liveobs.removeObserver(this);
            }
        };
        liveobs.observe(this.lifecycleOwner, obs);
    }

    public void updateBicycleLocation(String docId, Bicycle bicycle, double latitude, double longitude){
        GeoPoint newGeoPoint = new GeoPoint(latitude, longitude);
        bicycle.setCoordinate(newGeoPoint);
        this.bicycleService.update(docId, bicycle);
    }
}
