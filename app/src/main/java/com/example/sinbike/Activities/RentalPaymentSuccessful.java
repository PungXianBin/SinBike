package com.example.sinbike.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.sinbike.R;
import com.google.firebase.FirebaseApp;


public class RentalPaymentSuccessful extends Activity implements View.OnClickListener {
    Button returntohome;
    Animation roundingalone;
    ImageView icanchor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_rental_payment_successful);

        icanchor = findViewById(R.id.icanchor);
        returntohome = findViewById(R.id.returntohome);

        roundingalone = AnimationUtils.loadAnimation(this, R.anim.roundingalone);
        icanchor.startAnimation(roundingalone);


        returntohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(RentalPaymentSuccessful.this, RentalActivity.class);
                startActivity(n);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
