package com.example.sinbike.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sinbike.Constants;
import com.example.sinbike.POJO.Account;
import com.example.sinbike.POJO.Fine;
import com.example.sinbike.POJO.FinePayment;
import com.example.sinbike.POJO.Transaction;
import com.example.sinbike.R;
import com.example.sinbike.RecyclerViews.Adapters.CardViewDataAdapter;
import com.example.sinbike.Repositories.common.Resource;
import com.example.sinbike.ViewModels.AccountViewModel;
import com.example.sinbike.ViewModels.FineViewModel;
import com.example.sinbike.ViewModels.PaymentViewModel;
import com.example.sinbike.ViewModels.TransactionViewModel;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CheckFineActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    List<Fine> data = new ArrayList<>();
    List<Fine> stList = new ArrayList<>();
    List<Fine> fineList = new ArrayList<>();
    List<Double> totalAmount = new ArrayList<>();

    Fine singleParkingFine;

    Button btnSelection, btnPay;

    CardView cardView;
    Activity mActivity;

    TextView accountBalance, textAmount;

    PopupWindow mPopupWindow;
    double accountBalance1, totalAmount1;


    AccountViewModel accountViewModel;
    FineViewModel fineViewModel;
    Account account;

    View customView;

    TransactionViewModel transactionViewModel;
    PaymentViewModel paymentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_fine);

        initViewModel();

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Check & Pay Parking Fine");

        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(CheckFineActivity.this, ManageDashboardActivity.class);
            startActivity(intent);
            finish();
        });

        btnSelection = (Button) findViewById(R.id.pay_btn);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getData();

        // create an Object for Adapter
        mAdapter = new CardViewDataAdapter(fineList);

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);

        // mContext = getApplicationContext();
        mActivity = CheckFineActivity.this;

        stList = ((CardViewDataAdapter) mAdapter).getParkingList();

            cardView = (CardView) findViewById(R.id.FinePaymentLayout);

            btnSelection.setOnClickListener(new OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < stList.size(); i++) {
                        data.addAll(stList);
                        singleParkingFine = stList.get(i);
                        if (singleParkingFine.isSelected()) {
                            totalAmount.add(stList.get(i).getAmount());
                        }else
                            Toast.makeText(CheckFineActivity.this, "Please select a parking fine!", Toast.LENGTH_SHORT).show();
                    }
                    for (int u = 0; u < totalAmount.size(); u++) {
                        totalAmount1 += totalAmount.get(u);
                    }
                    displayFinePaymentPopup();
                }
            });
        }

    public void initViewModel() {
        this.fineViewModel = ViewModelProviders.of(this).get(FineViewModel.class);
        this.accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        this.account = accountViewModel.getAccount();
        this.transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        this.paymentViewModel = ViewModelProviders.of(this).get(PaymentViewModel.class);
    }

    public void getData() {
        fineViewModel.getAllFine(this.account.id).observe(this, new Observer<Resource<List<Fine>>>() {
            @Override
            public void onChanged(Resource<List<Fine>> listResource) {
                fineList.clear();
                fineList.addAll(listResource.data());
                mAdapter.notifyDataSetChanged();
                checkFineList();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void displayFinePaymentPopup() {
        for (int m = 0; m < stList.size(); m++) {
            singleParkingFine = stList.get(m);
            if (singleParkingFine.isSelected()) {
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                customView = inflater.inflate(R.layout.activity_fine_payment, null);
                mPopupWindow = new PopupWindow(
                        customView,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                );
                mPopupWindow.showAtLocation(btnSelection, Gravity.CENTER, 0, 0);

                btnPay = customView.findViewById(R.id.confirmBtn);

                btnPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < stList.size(); i++) {
                            singleParkingFine = stList.get(i);
                            if (singleParkingFine.isSelected()) {
                                if (account.getAccountBalance() > 0) {
                                    singleParkingFine.setStatus(1);
                                    fineViewModel.updateFine(singleParkingFine.id, singleParkingFine);
                                    double difference = accountBalance1 - totalAmount1;

                                    FinePayment finePayment = new FinePayment();
                                    finePayment.setTotalAmount(totalAmount1);
                                    finePayment.setPaymentDate(Timestamp.now());
                                    finePayment.setFineid(singleParkingFine.id);
                                    finePayment.setAccountId(account.id);
                                    paymentViewModel.createFinePayment(finePayment);

                                    account.setAccountBalance(difference);
                                    accountViewModel.update(account);


                                    Transaction transaction = new Transaction();
                                    transaction.setAmount(finePayment.getTotalAmount());
                                    transaction.setAccountId(account.id);
                                    transaction.setTransactionType(Constants.TRANSACTION_TYPE_FINE);
                                    transaction.settransactionDate(finePayment.getPaymentDate());
                                    transaction.setPaymentId(finePayment.id);
                                    transactionViewModel.create(transaction);

                                    Toast.makeText(CheckFineActivity.this, "Payment has been made successfully!", Toast.LENGTH_LONG).show();
                                } else
                                    Toast.makeText(CheckFineActivity.this, "Insufficient account balance! Please top up!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        mPopupWindow.dismiss();
                        recreate();
                    }
                });

                accountBalance = customView.findViewById(R.id.textAccountBalance);
                accountBalance1 = account.getAccountBalance();
                accountBalance.setText("Account Balance: $" + String.format("%.2f", accountBalance1));

                textAmount = customView.findViewById(R.id.textAmount);

                textAmount.setText("Total Amount: $" + String.format("%.2f", totalAmount1));

                ImageButton closeButton = customView.findViewById(R.id.fine_close);

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                        View itemLayoutView = inflater.inflate(R.layout.cardview_row, null);
                        CardViewDataAdapter.ViewHolder viewHolder = new CardViewDataAdapter.ViewHolder(itemLayoutView);
                        viewHolder.chkSelected.setChecked(false);
                        totalAmount.clear();
                        recreate();
                        // Dismiss the popup window
                        mPopupWindow.dismiss();
                    }
                });
                break;
            }
        }
    }

    public boolean checkFineList() {
        if (fineList.size() > 3) {
            account.setStatus(Constants.ACCOUNT_SUSPENDED);
            accountViewModel.update(account);
            this.accountViewModel.logout();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        for (int r = 0; r < fineList.size(); r++) {

            Date fineDate = fineList.get(r).getFineDate().toDate();

            int oldDate = fineDate.getDate();

            Date currentDate = (Calendar.getInstance().getTime());
            int diffDays = currentDate.getDate() - oldDate;

            if(diffDays > 7){
                account.setStatus(Constants.ACCOUNT_SUSPENDED);
                accountViewModel.update(account);
                this.accountViewModel.logout();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return false;
    }
}