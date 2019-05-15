package com.example.sinbike.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.sinbike.POJO.Account;
import com.example.sinbike.POJO.ParkingLot;
import com.example.sinbike.R;
import com.example.sinbike.ViewModels.AccountViewModel;
import com.example.sinbike.ViewModels.ParkingLotViewModel;
import com.example.sinbike.ViewModels.PaymentViewModel;
import com.example.sinbike.ViewModels.TransactionViewModel;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.List;

public class RentalPaymentActivity
    extends AppCompatActivity implements View.OnClickListener
{

    Button btnpaynow;
    private ArrayList<String> lst = new ArrayList<>();
    ParkingLotViewModel parkingLotViewModel;
    List<ParkingLot> parkingLot = new ArrayList<>();
    Chronometer timer;
    AccountViewModel accountViewModel;
    Account account;
    double accountBalance, difference;
    TransactionViewModel transactionViewModel;
    PaymentViewModel paymentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_rental_payment);

        btnpaynow = findViewById(R.id.btnpaynow);

        final TextView textView = (TextView) findViewById(R.id.Timer_Value);
        TextView totalCost = findViewById(R.id.totalcost1);
        TextView time = findViewById(R.id.Timer_Value);

        Bundle extras = getIntent().getExtras();
        String amount = extras.getString("totalcost");
        String time1 = (String) extras.get("time");

        String newAmount = amount.replace("$", "");

        double totalamount = Double.parseDouble(newAmount);

        totalCost.setText("$" + String.format("%.2f",totalamount));
        time.setText(time1);

        btnpaynow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Bundle extras = getIntent().getExtras();
                String bicycleID = extras.getString("bicycleID");
                Intent n = new Intent(getApplicationContext(), RentalPaymentBarcodeActivity.class);
                n.putExtra("totalamount2", String.valueOf(totalamount));
                n.putExtra("bicycleID", bicycleID);
                startActivity(n);
            }
        });
    }

    public void initViewModel(){
        this.parkingLotViewModel = ViewModelProviders.of(this).get(ParkingLotViewModel.class);
        this.parkingLotViewModel.setLifecycleOwner(this);
        this.accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        this.account = accountViewModel.getAccount();
        this.transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        this.paymentViewModel = ViewModelProviders.of(this).get(PaymentViewModel.class);
    }

    @Override
    public void onClick(View v) {

    }

}



