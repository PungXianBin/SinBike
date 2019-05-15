package com.example.sinbike.POJO;

import com.example.sinbike.Repositories.common.Model;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class Transaction extends Model {
    private double amount;
    private @ServerTimestamp Timestamp transactionDate;
    private String accountId;
    private String transactionType;
    private String paymentId;

    public Transaction() {
    }

    public Transaction(double amount, Timestamp transactionDate, String accountId, String transactionType) {
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.accountId = accountId;
        this.transactionType = transactionType;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp gettransactionDate() {
        return transactionDate;
    }

    public void settransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount='" + amount + '\'' +
                ", transactionDate=" + transactionDate +
                ", id='" + id + '\'' +
                '}';
    }
}
