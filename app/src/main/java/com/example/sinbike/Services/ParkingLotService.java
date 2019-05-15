package com.example.sinbike.Services;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.sinbike.POJO.ParkingLot;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.ParkingLotRepository;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;

public class ParkingLotService {
    private static final String TAG = "ParkingLotService";
    private ParkingLotRepository parkingLotRepository;

    public ParkingLotService(Application application){
        this.parkingLotRepository = new ParkingLotRepository(application);
    }

    public QueryLiveData<ParkingLot> getAllParkingLot(){
        return this.parkingLotRepository.getAllParkingLot();
    }

    public CompletionLiveData create(ParkingLot parkingLot){
        return this.parkingLotRepository.createParkingLot(parkingLot);
    }

    public LiveData<Resource<Boolean>> update(String docId, ParkingLot parkingLot){
        return this.parkingLotRepository.updateParkingLot(docId, parkingLot);
    }

    public LiveData<Resource<Boolean>> delete(String docId){
        return this.parkingLotRepository.deleteParkingLot(docId);
    }
}
