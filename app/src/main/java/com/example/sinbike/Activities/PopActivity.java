package com.example.sinbike.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sinbike.R;
import com.google.firebase.FirebaseApp;

public class PopActivity extends Activity {

    Button btnget;
    ImageView icanchor;
    TextView textView;
    Animation roundingalone;
    Chronometer timer;
    int count=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_pop);

        DisplayMetrics dn = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dn);

        icanchor = findViewById(R.id.icanchor);
        btnget = findViewById(R.id.btnget);
        timer = findViewById(R.id.timer);
        textView = findViewById(R.id.textView3);

        int width = dn.widthPixels;
        int height = dn.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.7));

        roundingalone = AnimationUtils.loadAnimation(this, R.anim.roundingalone);
        icanchor.startAnimation(roundingalone);
        timer.setBase(SystemClock.elapsedRealtime());

        Thread t=new Thread(){


            @Override
            public void run(){

                while(!isInterrupted()){

                    try {
                        Thread.sleep(120000); //2mins

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                double x = count / 10.00;
                                count++;
                                textView.setText("$" + x);
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        t.start();
        timer.start();

        btnget.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                timer.stop();
                Bundle s = getIntent().getExtras();
                String bicycleId = s.getString("barcode9");
                Intent n = new Intent(PopActivity.this, RentalPaymentActivity.class);
                n.putExtra("totalcost", textView.getText().toString());
                n.putExtra("time", timer.getText().toString());
                n.putExtra("bicycleID", bicycleId);
                startActivity(n);
            }
        });
    }
}

