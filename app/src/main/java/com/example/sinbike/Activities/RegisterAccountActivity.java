package com.example.sinbike.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sinbike.Activities.Dialogs.CustomDialog;
import com.example.sinbike.Constants;
import com.example.sinbike.Observers.SignUpObserver;
import com.example.sinbike.POJO.Account;
import com.example.sinbike.R;
import com.example.sinbike.Repositories.common.Resource;
import com.example.sinbike.ViewModels.AccountViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class RegisterAccountActivity extends AppCompatActivity implements SignUpObserver, View.OnClickListener {

    EditText signupName, signupEmail, signupPassword, signupPhone, signupDOB;
    TextView tvTermsandCondition;
    RadioGroup rdGender;
    RadioButton rbMale, rbFemale;
    RadioButton radioButtonOptions;
    Button btnSubmit, btnClear;
    CustomDialog customDialog;
    CheckBox checkBox;
    FirebaseAuth firebaseAuth;
    List<String> emailList = new ArrayList<>();
    List<Account> accountList = new ArrayList<>();

    String name;
    String email;
    String password;
    String phone;
    String DOB;

    AccountViewModel accountViewModel;

    // variable to hold the gender value
    private String genderOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        this.init();
        this.setListener();
        this.initToolbar();
        emailList.clear();
        accountViewModel.getAllAccount().removeObservers(this);
        accountViewModel.getAllAccount().observe(this, new Observer<Resource<List<Account>>>() {
            @Override
            public void onChanged(Resource<List<Account>> listResource) {
                accountList = listResource.data();
                for(int r = 0; r < accountList.size(); r++) {
                    emailList.add(accountList.get(r).getEmail());
                }
            }
        });
    }

    public void init(){

        signupName = findViewById(R.id.signupName);
        signupEmail = findViewById(R.id.signupEmail);
        signupPassword = findViewById(R.id.signupPassword);
        signupPhone = findViewById(R.id.signupPhone);
        signupDOB = findViewById(R.id.signupDOB);
        rdGender = findViewById(R.id.rdGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnClear = findViewById(R.id.btnClear);
        tvTermsandCondition = findViewById(R.id.tvTermsandCondition);
        checkBox = findViewById(R.id.cbTermsAndCondition);
        signupDOB.addTextChangedListener(new validateDOB());


        this.accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        this.accountViewModel.setSignUpObserver(this);
        this.accountViewModel.setLifecycleOwner(this);
    }

    public void setListener(){

        btnClear.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        // to get the gender from the radio button
        rdGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButtonOptions = rdGender.findViewById(checkedId);

                switch (checkedId){
                    case R.id.rbMale:
                        genderOptions = radioButtonOptions.getText().toString();
                        break;

                    case R.id.rbFemale:
                        genderOptions = radioButtonOptions.getText().toString();
                        break;
                }
            }
        });
    }

    private void registerUser(){
        Account account = new Account();
        account.setName(name);
        account.setEmail(email);
        account.setTelephoneNumber(phone);
        account.setDateOfBirth(DOB);
        account.setGender(genderOptions);
        account.setStatus(Constants.ACCOUNT_OPEN);

        this.accountViewModel.createAccount(account);
    }


    private class validateDOB implements TextWatcher {
        private boolean lock;
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            if (lock || s.length() > 8) {
                return;
            }
            lock = true;
            for (int i = 2; i < s.length(); i += 3) {
                if (s.toString().charAt(i) != '/') {
                    s.insert(i, "/");
                }
            }
            lock = false;
        }
    }


    public boolean checkValidation() {
        name = signupName.getText().toString().trim();
        email = signupEmail.getText().toString().trim();
        password = signupPassword.getText().toString().trim();
        phone = signupPhone.getText().toString().trim();
        DOB = signupDOB.getText().toString().trim();
        String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


        if (name.length() <= 0){
            Toast.makeText(RegisterAccountActivity.this, "Name is Required!", Toast.LENGTH_LONG).show();
            return false;
        } else if (email.length() <= 0) {
            Toast.makeText(RegisterAccountActivity.this, "Email is Required!", Toast.LENGTH_LONG).show();
            return false;
        } else if (!email.matches(emailPattern)) {
            Toast.makeText(RegisterAccountActivity.this, "Invalid email format!", Toast.LENGTH_LONG).show();
            return false;
        } else if (password.length() <= 0){
            Toast.makeText(RegisterAccountActivity.this, "Password is Required!", Toast.LENGTH_LONG).show();
            return false;
        } else if (password.length() < 6){
            Toast.makeText(RegisterAccountActivity.this, "Minimum Password Length is 6!", Toast.LENGTH_LONG).show();
            return false;
        } else if (phone.length() <=0){
            Toast.makeText(RegisterAccountActivity.this, "Phone Number is Required!", Toast.LENGTH_LONG).show();
            return false;
        } else if (phone.length() < 8){
            Toast.makeText(RegisterAccountActivity.this, "Minimum Phone Length is 8!", Toast.LENGTH_LONG).show();
            return false;
        } else if (!DOB.matches("^(1[0-9]|0[1-9]|3[0-1]|2[1-9])/(0[1-9]|1[0-2])/[0-9]{4}$")) {
            Toast.makeText(RegisterAccountActivity.this, "Invalid date format! Please enter in DD/MM/YYYY", Toast.LENGTH_LONG).show();
            return false;
        } else if (DOB.length()<=0){
            Toast.makeText(RegisterAccountActivity.this, "DOB is Required!", Toast.LENGTH_LONG).show();
            return false;
        } else if (!checkBox.isChecked()){
            Toast.makeText(RegisterAccountActivity.this, "Please accept Terms & Conditions!", Toast.LENGTH_LONG).show();
            return false;
        } else for(int e = 0; e < emailList.size(); e++){
            if (email.equals(emailList.get(e))){
                Toast.makeText(RegisterAccountActivity.this, "Email has been used!", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return true;
    }


    @Override
    public void createAccountPass() {
        Toast.makeText(this, "Successfully Registered, Verification email sent. Please verify your email!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void createAccountFail() {
        Toast.makeText(this, "Account already exist.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnClear) {
            signupName.setText("");
            signupEmail.setText("");
            signupPhone.setText("");
            signupPassword.setText("");
            rdGender.clearCheck();
        }else if (v.getId() == R.id.btnSubmit){
            if (checkValidation()) {
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        sendEmailVerification();
                    } else
                        try {
                            throw task.getException();
                        } catch(FirebaseAuthWeakPasswordException e) {
                            Toast.makeText(RegisterAccountActivity.this, "Password is weak!", Toast.LENGTH_LONG).show();
                        } catch(FirebaseAuthInvalidCredentialsException e) {
                            Toast.makeText(RegisterAccountActivity.this, "This email address is invalid!", Toast.LENGTH_LONG).show();
                        } catch(FirebaseAuthUserCollisionException e) {
                            Toast.makeText(RegisterAccountActivity.this, "Account has been created!", Toast.LENGTH_LONG).show();
                        } catch(Exception e) {
                            Toast.makeText(RegisterAccountActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                });
            }
        } else if (v.getId() == R.id.tvTermsandCondition){
            customDialog = new CustomDialog(this);
            customDialog.show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void initToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Register Account");

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterAccountActivity.this , LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        registerUser();
                        Toast.makeText(RegisterAccountActivity.this, "Successfully Registered, Verification email sent. Please verify your email!", Toast.LENGTH_LONG).show();
                        accountViewModel.logout();
                        Intent intent = new Intent(RegisterAccountActivity.this , LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else
                        Toast.makeText(RegisterAccountActivity.this, "Verification email has not been sent!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

