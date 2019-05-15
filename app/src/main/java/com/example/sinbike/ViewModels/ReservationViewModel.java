package com.example.sinbike.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.sinbike.Observers.ReservationViewModelObserver;
import com.example.sinbike.POJO.Reservation;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Services.ReserveService;

import java.util.List;

public class ReservationViewModel extends AndroidViewModel
{
    private static final String TAG = "RentalViewModel";

    private LifecycleOwner lifecycleOwner;
    private ReserveService reserveService;
    ReservationViewModelObserver observer;

    public void setObserver(ReservationViewModelObserver observer) {
        this.observer = observer;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    public ReservationViewModel(Application application){
        super(application);
        this.reserveService = new ReserveService(application);
    }

    public CompletionLiveData createReservation(Reservation reservation){

        return this.reserveService.create(reservation);
    }

    public LiveData<Resource<Boolean>> update(String docId, Reservation reservation){
        return this.reserveService.update(docId, reservation);
    }

    public LiveData<com.example.sinbike.Repositories.common.Resource<List<Reservation>>> getAllReservation(String docId){
        final LiveData<com.example.sinbike.Repositories.common.Resource<List<Reservation>>>liveobs = this.reserveService.getAllReservationByUser(docId);
        return liveobs;
    }
}
