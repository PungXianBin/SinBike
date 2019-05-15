package com.example.sinbike.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sinbike.POJO.FinePayment;
import com.example.sinbike.POJO.Payment;
import com.example.sinbike.POJO.RentalPayment;
import com.example.sinbike.Repositories.Firestore.Resource;
import com.example.sinbike.Repositories.common.CompletionLiveData;
import com.example.sinbike.Repositories.common.QueryLiveData;
import com.example.sinbike.Services.FinePaymentService;
import com.example.sinbike.Services.RentalPaymentService;

public class PaymentViewModel extends AndroidViewModel {

    private static final String TAG = "PaymentViewModel";
    private FinePaymentService finePaymentService;
    RentalPaymentService rentalPaymentService;
    private String accountId;

    public PaymentViewModel(Application application){
        super(application);
        this.finePaymentService = new FinePaymentService(application);
    }

    public void setAccountId(String accountId){
        this.accountId = accountId;
    }

    public QueryLiveData<Payment> getAllFinePayment(String accountId) {
        return this.finePaymentService.getAllPayment(accountId);
    }

    public CompletionLiveData createFinePayment(FinePayment fine){
        return this.finePaymentService.createPayment(fine);
    }

    public LiveData<Resource<Boolean>> updateFinePayment(String docId, FinePayment fine){
        return this.finePaymentService.updatePayment(docId, fine);
    }

    public LiveData<Resource<Boolean>> deleteFinePayment(String docId){
        return this.finePaymentService.deletePayment(docId);
    }

    public QueryLiveData<Payment> getAllRentalPayment(String accountId) {
        return this.finePaymentService.getAllPayment(accountId);
    }

    public CompletionLiveData createRentalPayment(RentalPayment rental) {
        return this.rentalPaymentService.createPayment(rental);
    }

    public LiveData<Resource<Boolean>> updateRentalPayment(String docId, RentalPayment rental) {
        return this.rentalPaymentService.updatePayment(docId, rental);
    }

    public LiveData<Resource<Boolean>> deleteRentalPayment(String docId) {
        return this.rentalPaymentService.deletePayment(docId);
    }
}
