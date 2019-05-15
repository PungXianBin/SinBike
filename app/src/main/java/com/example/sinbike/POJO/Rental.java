package com.example.sinbike.POJO;

import com.example.sinbike.Repositories.common.Model;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class Rental extends Model {

    private @ServerTimestamp Timestamp rentalDate;
    private double amount;
    private String accountId;
    private String bicycleId;

    public Rental() {
    }

    public Rental(Timestamp rentalDate, double amount, String accountId, String bicycleId) {
        this.rentalDate = rentalDate;
        this.amount = amount;
        this.accountId = accountId;
        this.bicycleId = bicycleId;
    }

    public String getBicycleId() {
        return bicycleId;
    }

    public void setBicycleId(String bicycleId) {
        this.bicycleId = bicycleId;
    }

    public Timestamp getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Timestamp rentalDate) {
        this.rentalDate = rentalDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "bicycleID=" + bicycleId +
                ", rentalDate='" + rentalDate + '\'' +
                ", amount='" + amount + '\'' +
                ", accountID='" + accountId + '\'' +
                '}';
    }
}