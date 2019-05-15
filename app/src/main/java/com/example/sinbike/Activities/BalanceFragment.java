package com.example.sinbike.Activities;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.sinbike.POJO.Account;
import com.example.sinbike.R;
import com.example.sinbike.ViewModels.AccountViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class BalanceFragment extends Fragment {


    AccountViewModel accountViewModel;
    Account account;
    TextView accountBalance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initViewModel();

        View v = getLayoutInflater().inflate(R.layout.fragment_balance, container, false);
        accountBalance = v.findViewById(R.id.accountBalance);
        double amountBalance = account.getAccountBalance();
        accountBalance.setText(String.format("%.2f", amountBalance));

        return v;
    }

    public void initViewModel() {
        this.accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        this.account = accountViewModel.getAccount();
    }

}
