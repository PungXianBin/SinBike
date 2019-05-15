package com.example.sinbike.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sinbike.R;

public class SplashActivity extends AppCompatActivity  {

    private static final String TAG = "SplashActivity";

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        this.handleSplashScreen();
    }

    public void handleSplashScreen(){
                new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}

