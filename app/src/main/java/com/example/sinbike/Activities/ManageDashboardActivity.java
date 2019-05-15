package com.example.sinbike.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import com.example.sinbike.Constants;
import com.example.sinbike.POJO.Account;
import com.example.sinbike.R;
import com.example.sinbike.ViewModels.AccountViewModel;

public class ManageDashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "ManageDashboardActivity";

    public CardView rentalbikeid, walletid, reportafaultid, aboutus, manageprofileid, payfinesid;

    Button logout;
    TextView idUserName;

    AccountViewModel accountViewModel;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_dashboard);

        this.initViewModel();
        this.init();
        checkAccountStatus(accountViewModel.getAccount());
    }

    public void init(){
        rentalbikeid = findViewById(R.id.rentalbike_id);
        walletid = findViewById(R.id.wallet_id);
        reportafaultid = findViewById(R.id.reportafault_id);
        aboutus = findViewById(R.id.aboutus);
        manageprofileid = findViewById(R.id.manageprofile_id);
        payfinesid = findViewById(R.id.checkfine_id);
        logout = findViewById(R.id.btnLogout);
        idUserName = findViewById(R.id.idUserName);

        aboutus.setOnClickListener(this);
        manageprofileid.setOnClickListener(this);
        rentalbikeid.setOnClickListener(this);
        walletid.setOnClickListener(this);
        reportafaultid.setOnClickListener(this);
        logout.setOnClickListener(this);
        payfinesid.setOnClickListener(this);
        manageprofileid.setOnClickListener(this);

        this.idUserName.setText(this.account.getName());
    }

    public void initViewModel(){
        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        account = accountViewModel.getAccount();
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rentalbike_id){
            this.launchRentalActivity();
        } else if (v.getId() == R.id.wallet_id){
            this.launchTopUpActivity();
        } else if (v.getId() == R.id.reportafault_id){
            this.launchReportFault();
        } else if (v.getId() == R.id.checkfine_id){
            this.launchPayFine();
        } else if (v.getId() == R.id.aboutus){
            this.aboutus();
        } else if (v.getId() == R.id.btnLogout){
            this.logout();
        } else if (v.getId() == R.id.manageprofile_id){
            this.launchManageProfile();
        }
    }

    public void launchRentalActivity(){
        Intent i = new Intent(this, RentalActivity.class);
        startActivity(i);
    }

    public void launchTopUpActivity(){
        Intent intent = new Intent(this , WalletPage.class);
        startActivity(intent);
    }

    public void launchReportFault(){
        Intent intent = new Intent(this, ReportFaultsActivity.class);
        startActivity(intent);
    }

    public void launchPayFine(){
        Intent intent = new Intent(this, CheckFineActivity.class);
        startActivity(intent);
    }

    public void aboutus(){
        Intent intent = new Intent (this, AboutUs.class);
        startActivity(intent);
    }

    public void logout(){
        this.accountViewModel.logout();
        Intent intent = new Intent (this, LoginActivity.class);
        startActivity(intent);
    }



    public void launchManageProfile(){
        Intent intent = new Intent (this, ManageProfileActivity.class);
        startActivity(intent);
    }



    public boolean checkAccountStatus(Account account){
        if(account.getStatus()== Constants.ACCOUNT_SUSPENDED){
            this.accountViewModel.logout();
            Intent intent = new Intent (this, LoginActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Your account has been suspended!", Toast.LENGTH_LONG).show();
            return true;
        } else if (account.getStatus() == Constants.ACCOUNT_CLOSED){
            this.accountViewModel.logout();
            Intent intent = new Intent (this, LoginActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Your account has been closed!", Toast.LENGTH_LONG).show();
            return true;
        }return false;
    }
}
