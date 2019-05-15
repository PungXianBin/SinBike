package com.example.sinbike.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sinbike.R;

public class SuccessfulTopUpMessage extends AppCompatActivity {

    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_top_up_message);

        backBtn = (Button)findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View args0) {
                Intent intent = new Intent(SuccessfulTopUpMessage.this , WalletPage.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
