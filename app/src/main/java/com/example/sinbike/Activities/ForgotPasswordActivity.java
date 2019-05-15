package com.example.sinbike.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sinbike.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etEmail;
    private Button resetPassword, btnBack;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        this.init();
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Forget Password");
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public void init(){
        resetPassword = findViewById(R.id.resetPassword);
        etEmail = findViewById(R.id.etEmail);

        firebaseAuth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.resetPassword){
            this.resetPassword();
        }
    }

    public void resetPassword(){
        String email = etEmail.getText().toString().trim();

        if (email.equals("")){
            Toast.makeText(ForgotPasswordActivity.this, "Please enter your registered email address!" , Toast.LENGTH_LONG).show();
        } else {
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ForgotPasswordActivity.this, "Password reset email sent!" , Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));

                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Error in sending email!" , Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}
