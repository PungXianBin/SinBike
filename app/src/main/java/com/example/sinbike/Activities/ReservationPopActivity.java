package com.example.sinbike.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.sinbike.Constants;
import com.example.sinbike.POJO.Reservation;
import com.example.sinbike.R;
import com.example.sinbike.ViewModels.ReservationViewModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class ReservationPopActivity extends AppCompatActivity {

    Button btnunlock;
    ImageView icanchor;
    Animation roundingalone;

    private ArrayList<String> lst = new ArrayList<>();
    int count=0;

    private TextView timertext;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 600000; //10mins
    private boolean timerRunning;
    ReservationViewModel reservationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_reservation_pop);
        initViews();

        DisplayMetrics dn = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dn);

        int width = dn.widthPixels;
        int height = dn.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.6));

        btnunlock = findViewById(R.id.btnunlock);
        icanchor = findViewById(R.id.icanchor);
        timertext = findViewById(R.id.timertext);
        roundingalone = AnimationUtils.loadAnimation(this, R.anim.roundingalone);
        icanchor.startAnimation(roundingalone);



       countDownTimer=  new CountDownTimer(600000, 1000) {

            public void onTick(long millisUntilFinished) {
                timertext.setText(millisUntilFinished /1000/60 + ":" + (millisUntilFinished/1000)%60);
            }

            public void onFinish() {

                Reservation reservation = new Reservation();
                reservation.setReservationDate(Timestamp.now());
                reservation.setReservationStatus(Constants.RESERVATION_RESERVE);
                reservationViewModel.createReservation(reservation);


                Toast.makeText(ReservationPopActivity.this, "Expired! Reservation Released", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ReservationPopActivity.this , RentalActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();


        btnunlock.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                finish();
                startActivity(new Intent(ReservationPopActivity.this, RentalBarcodeActivity.class));
            }
        });
    }

    public void initViews(){
        this.reservationViewModel = ViewModelProviders.of(this).get(ReservationViewModel.class);
    }



}

