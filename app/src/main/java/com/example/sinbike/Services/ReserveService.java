package com.example.sinbike.Services;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.sinbike.POJO.Reservation;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.ReservationRepository;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;

public class ReserveService {
    private static final String TAG = "RentalService";
    private ReservationRepository reservationRepository;

    public ReserveService(Application application){
        this.reservationRepository = new ReservationRepository(application);
    }

    public QueryLiveData<Reservation> getAllReservationByUser(String id){
        return this.reservationRepository.getAllReservationByUser(id);
    }

    public CompletionLiveData create(Reservation reservation){
        return this.reservationRepository.createNewReservation(reservation);
    }

    public LiveData<Resource<Boolean>> update(String docId, Reservation reservation){
        return this.reservationRepository.updateReservation(docId, reservation);
    }

    public LiveData<Resource<Boolean>> delete(String docId){
        return this.reservationRepository.deleteReservation(docId);
    }
}
