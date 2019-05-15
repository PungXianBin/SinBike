package com.example.sinbike.Services;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.sinbike.POJO.FinePayment;
import com.example.sinbike.POJO.Payment;
import com.example.sinbike.Repositories.FinePaymentRepository;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;

public class FinePaymentService {
    private static final String TAG = "FinePaymentService";
    private FinePaymentRepository finePaymentRepository;

    public FinePaymentService(Application application){
        this.finePaymentRepository = new FinePaymentRepository(application);
    }

    public FinePaymentService(Application application, String userId){
        this.finePaymentRepository = new FinePaymentRepository(application, userId);
    }

    public QueryLiveData<Payment> getAllPayment(String accountId) {
        return this.finePaymentRepository.getAllPayment(accountId);
    }

    public CompletionLiveData createPayment(FinePayment payment){
        return this.finePaymentRepository.createPayment(payment);
    }

    public LiveData<Resource<Boolean>> updatePayment(String docId, FinePayment payment){
        return this.finePaymentRepository.updatePayment(docId, payment);
    }

    public LiveData<Resource<Boolean>> deletePayment(String docId){
        return this.finePaymentRepository.deletePayment(docId);
    }
}
