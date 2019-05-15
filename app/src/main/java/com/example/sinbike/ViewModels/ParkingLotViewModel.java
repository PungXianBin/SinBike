package com.example.sinbike.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.sinbike.Observers.ParkingLotViewModelObserver;
import com.example.sinbike.POJO.ParkingLot;
import com.example.sinbike.Services.ParkingLotService;

import java.util.List;

public class ParkingLotViewModel extends AndroidViewModel
{
    private static final String TAG = "ParkingLotViewModel";

    private LifecycleOwner lifecycleOwner;
    private ParkingLotService parkingLotService;
    private ParkingLotViewModelObserver observer;

    public ParkingLotViewModel(Application application){
        super(application);
        this.parkingLotService = new ParkingLotService(application);
    }

    public void setObserver(ParkingLotViewModelObserver observer) {
        this.observer = observer;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    public LiveData<com.example.sinbike.Repositories.common.Resource<List<ParkingLot>>> getAllParkinglot(){
        final LiveData<com.example.sinbike.Repositories.common.Resource<List<ParkingLot>>> liveobs = this.parkingLotService.getAllParkingLot();
        return liveobs;
    }

    public void getAllParkingLots(){
        final LiveData<com.example.sinbike.Repositories.common.Resource<List<ParkingLot>>> liveobs = this.parkingLotService.getAllParkingLot();
        Observer obs = new Observer<com.example.sinbike.Repositories.common.Resource<List<ParkingLot>>>(){
            @Override
            public void onChanged(com.example.sinbike.Repositories.common.Resource<List<ParkingLot>> parkingLotList) {
                List<ParkingLot> parkingLots = parkingLotList.data();

                Log.d(TAG, parkingLots.toString());

                observer.showParkingLotList(parkingLots);
            }
        };
        liveobs.observe(this.lifecycleOwner, obs);
    }
}
