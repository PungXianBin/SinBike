package com.example.sinbike.POJO;

import com.example.sinbike.Repositories.common.Model;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class Payment extends Model {
    private @ServerTimestamp Timestamp paymentDate;
    private double totalAmount;
    private String accountId;

    public Payment() {
    }

    public Payment(String accountId, Timestamp paymentDate, double totalAmount) {
        this.accountId = accountId;
        this.paymentDate = paymentDate;
        this.totalAmount = totalAmount;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "accountId=" + accountId +
                "paymentDate=" + paymentDate +
                ", totalAmount=" + totalAmount +
                ", id='" + id + '\'' +
                '}';
    }
}

