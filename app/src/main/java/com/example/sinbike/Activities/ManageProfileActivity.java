package com.example.sinbike.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.example.sinbike.POJO.Account;
import com.example.sinbike.R;
import com.example.sinbike.ViewModels.AccountViewModel;

public class ManageProfileActivity extends AppCompatActivity implements View.OnClickListener{

    AccountViewModel accountViewModel;
    Account account;

    EditText etName, etTelephoneNumber, etDob;
    RadioGroup rdGender;
    RadioButton rbMale, rbFemale;
    RadioButton radioButtonOptions;
    Button btnSubmit, btnClear;

    String name;
    String phone;
    String DOB;

    // variable to hold the gender value
    String genderOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_profile);

        this.initViewModel();
        this.init();
        setListener();
        this.back();
    }

    public void init(){
        etName = findViewById(R.id.signupName);
        etTelephoneNumber = findViewById(R.id.signupPhone);
        etDob = findViewById(R.id.signupDOB);
        rdGender = findViewById(R.id.rdGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnClear = findViewById(R.id.btnClear);
        etDob.addTextChangedListener(new validateDOB());


        this.initHints();

        btnSubmit.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        etDob.addTextChangedListener(new validateDOB());
    }

    public void initHints(){
        etName.setText(this.account.getName());
        etTelephoneNumber.setText(this.account.getTelephoneNumber());
        etDob.setText(this.account.getDateOfBirth());
        genderOptions = this.account.getGender();
        if(this.account.getGender().equals("Male")) {
            rbMale.setChecked(true);
        } else if (this.account.getGender().equals("Female")) {
            rbFemale.setChecked(true);
        }
    }

    public void initViewModel(){
        this.accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        this.accountViewModel.setLifecycleOwner(this);
        this.account = accountViewModel.getAccount();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnSubmit){
            if(checkValidation()) {
                this.submit();
            }
        } else if (v.getId() == R.id.btnClear){
            this.clear();
        } else {
            this.back();
        }
    }

    public void submit(){

        if (!etName.getText().toString().equals("")){
            this.account.setName(etName.getText().toString());
        }

        if(!etTelephoneNumber.getText().toString().equals("")){
            this.account.setTelephoneNumber(etTelephoneNumber.getText().toString());
        }

        if (!etDob.getText().toString().equals("")){
            this.account.setDateOfBirth(etDob.getText().toString());
        }

        this.account.setGender(genderOptions);

        double accountBalance = this.account.getAccountBalance();

        this.account.setAccountBalance(accountBalance);

        this.accountViewModel.update(this.account);
        Intent intent = new Intent(ManageProfileActivity.this , ManageDashboardActivity.class);
        Toast.makeText(this, "Account Detailed Updated!", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }

    public void clear(){
        etName.setText("");
        etTelephoneNumber.setText("");
        etDob.setText("");
    }

    public void back(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Manage Profile");

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageProfileActivity.this , ManageDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
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

    public void setListener(){

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

    public boolean checkValidation() {
        name = etName.getText().toString().trim();
        phone = etTelephoneNumber.getText().toString().trim();
        DOB = etDob.getText().toString().trim();

        if (name.length() <= 0){
            Toast.makeText(ManageProfileActivity.this, "Name is Required!", Toast.LENGTH_LONG).show();
            return false;
        } else if (phone.length() <=0){
            Toast.makeText(ManageProfileActivity.this, "Phone Number is Required!", Toast.LENGTH_LONG).show();
            return false;
        } else if (phone.length() < 8){
            Toast.makeText(ManageProfileActivity.this, "Minimum Phone Length is 8!", Toast.LENGTH_LONG).show();
            return false;
        } else if (!DOB.matches("^(1[0-9]|0[1-9]|3[0-1]|2[1-9])/(0[1-9]|1[0-2])/[0-9]{4}$")) {
            Toast.makeText(ManageProfileActivity.this, "Invalid date format! Please enter in DD/MM/YYYY", Toast.LENGTH_LONG).show();
            return false;
        } else if (DOB.length()<=0){
            Toast.makeText(ManageProfileActivity.this, "DOB is Required!", Toast.LENGTH_LONG).show();
            return false;
        } 
        return true;
    }


}
