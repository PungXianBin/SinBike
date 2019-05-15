package com.example.sinbike.POJO;

import com.example.sinbike.Repositories.common.Model;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class Fine extends Model {

    private String accountId;
    private @ServerTimestamp Timestamp fineDate;
    private double amount;
    private String location;
    private boolean isSelected;
    private int status;

    public Fine() {
    }

    public Fine(String accountId, Timestamp fineDate, double amount, String location, int status) {
        this.accountId = accountId;
        this.fineDate = fineDate;
        this.amount = amount;
        this.location = location;
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Timestamp getFineDate() {
        return fineDate;
    }

    public void setFineDate(Timestamp fineDate) {
        this.fineDate = fineDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Fine{" +
                "accountId='" + accountId + '\'' +
                ", fineDate=" + fineDate + '\'' +
                ", location=" + location + '\'' +
                ", amount=" + amount + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
