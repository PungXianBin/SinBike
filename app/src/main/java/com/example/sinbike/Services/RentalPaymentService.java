package com.example.sinbike.Services;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.sinbike.POJO.Payment;
import com.example.sinbike.POJO.RentalPayment;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.RentalPaymentRepository;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;

public class RentalPaymentService {
    private static final String TAG = "RentalPaymentService";
    private RentalPaymentRepository rentalPaymentRepository;

    public RentalPaymentService(Application application) {
        this.rentalPaymentRepository = new RentalPaymentRepository(application);
    }

    public RentalPaymentService(Application application, String userId) {
        this.rentalPaymentRepository = new RentalPaymentRepository(application, userId);
    }

    public QueryLiveData<Payment> getAllPayment(String accountId) {
        return this.rentalPaymentRepository.getAllPayment(accountId);
    }

    public CompletionLiveData createPayment(RentalPayment payment) {
        return this.rentalPaymentRepository.createPayment(payment);
    }

    public LiveData<Resource<Boolean>> updatePayment(String docId, RentalPayment payment) {
        return this.rentalPaymentRepository.updatePayment(docId, payment);
    }

    public LiveData<Resource<Boolean>> deletePayment(String docId) {
        return this.rentalPaymentRepository.deletePayment(docId);
    }
}
