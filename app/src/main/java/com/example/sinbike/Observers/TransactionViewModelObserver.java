package com.example.sinbike.Observers;

import com.example.sinbike.POJO.Transaction;

import java.util.List;

public interface TransactionViewModelObserver {
    void showAllTransaction(List<Transaction> transactionList);
}
