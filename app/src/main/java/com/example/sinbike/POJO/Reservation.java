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
    String accountId;

    public Reservation() {
    }

    public Reservation(String accountId, Timestamp reservationDate, boolean reservationStatus) {
        this.accountId = accountId;
        this.reservationDate = reservationDate;
        this.reservationStatus = reservationStatus;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Timestamp getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Timestamp reservationDate) {
        this.reservationDate = reservationDate;
    }

    public boolean getReservationStatus() {
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
