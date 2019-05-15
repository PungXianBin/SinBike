package com.example.sinbike.POJO;

import com.example.sinbike.Repositories.common.Model;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class Reservation extends Model {

    private final boolean RESERVATION_RESERVE = true;
    private final boolean RESERVATION_OPEN = false;

    private @ServerTimestamp Timestamp reservationDate;
    private boolean reservationStatus;
    private String bicycleId;

    public Reservation() {
    }

    public Reservation(Timestamp reservationDate, boolean reservationStatus) {
        this.reservationDate = reservationDate;
        this.reservationStatus = reservationStatus;
    }

    public Timestamp getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Timestamp reservationDate) {
        this.reservationDate = reservationDate;
    }

    public boolean isReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(boolean reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "bicycleID=" + bicycleId +
                ", reservationDate='" + reservationDate + '\'' +
                ", reservationStatus='" + reservationStatus + '\'' +
                ", bicycleID='" + bicycleId + '\'' +
                '}';
    }
}
