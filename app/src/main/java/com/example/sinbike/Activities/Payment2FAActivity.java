package com.example.sinbike.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.sinbike.Constants;
import com.example.sinbike.POJO.Account;
import com.example.sinbike.POJO.Transaction;
import com.example.sinbike.R;
import com.example.sinbike.ViewModels.AccountViewModel;
import com.example.sinbike.ViewModels.TransactionViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Payment2FAActivity extends AppCompatActivity {

    Button confirmBtn, submitBtn;
    EditText etPhone,  etVeriCode;
    FirebaseAuth mAuth;
    String codeSent;
    AccountViewModel accountViewModel;
    Account account;
    TransactionViewModel transactionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_2fa);

        confirmBtn = findViewById(R.id.confirmBtn);
        submitBtn = findViewById(R.id.submitBtn);

        mAuth = FirebaseAuth.getInstance();

        etPhone = findViewById(R.id.phone_no);
        etPhone.setText("+65");
        Selection.setSelection(etPhone.getText(), etPhone.getText().length());

        etVeriCode = findViewById(R.id.veri_code);
        initViewModel();

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View args0) {
                sendVerificationCode();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View args0) {
                verifySignInCode();
            }
        });

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().startsWith("+65")){
                    etPhone.setText("+65");
                    Selection.setSelection(etPhone.getText(), etPhone.getText().length());
                }
            }
        });

    }

    public void initViewModel() {
        this.accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        this.account = accountViewModel.getAccount();
        this.transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
    }

    private void verifySignInCode() {
        String code = etVeriCode.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Bundle extras = getIntent().getExtras();
                            double amount = extras.getDouble("amount");
                            double oldAmount = account.getAccountBalance();
                            double newAmount = amount + oldAmount;
                            account.setAccountBalance(newAmount);
                            String date = account.getDateOfBirth();
                            account.setDateOfBirth(date);
                            String gender = account.getGender();
                            account.setGender(gender);
                            String name = account.getName();
                            account.setName(name);
                            accountViewModel.update(account);

                            Transaction transaction = new Transaction();
                            transaction.setTransactionType(Constants.TRANSACTION_TYPE_TOPUP);
                            transaction.settransactionDate(Timestamp.now());
                            transaction.setAmount(amount);
                            transaction.setAccountId(account.id);
                            transactionViewModel.create(transaction);

                            Intent intent = new Intent(Payment2FAActivity.this , SuccessfulTopUpMessage.class);
                            startActivity(intent);
                            finish();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(),
                                        "Incorrect Verification Code ", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void sendVerificationCode(){
        String phoneNo = etPhone.getText().toString();

        if(phoneNo.isEmpty()){
            etPhone.setError("Phone number is required");
            etPhone.requestFocus();
            return;
        }

        if(phoneNo.length() < 10 ){
            etPhone.setError("Please enter a valid phone number");
            etPhone.requestFocus();
            return;
        }
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNo,
                1,
                TimeUnit.MINUTES,
                this,
                mCallbacks);
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
        }
        @Override
        public void onVerificationFailed(FirebaseException e) {
        }
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
        }
    };


}
