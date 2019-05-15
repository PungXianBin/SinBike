package com.example.sinbike.Observers;

import com.example.sinbike.POJO.Reservation;

import java.util.List;

public interface ReservationViewModelObserver {
    void showReservationList(List<Reservation> reservationList);
}
