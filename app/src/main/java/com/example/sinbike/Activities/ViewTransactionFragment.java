package com.example.sinbike.Activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sinbike.POJO.Account;
import com.example.sinbike.POJO.Transaction;
import com.example.sinbike.R;
import com.example.sinbike.Repositories.common.Resource;
import com.example.sinbike.ViewModels.AccountViewModel;
import com.example.sinbike.ViewModels.TransactionViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewTransactionFragment extends Fragment {
    List<Transaction> transactionList = new ArrayList<>();
    ListView listView;
    TransactionViewModel transactionViewModel;
    AccountViewModel accountViewModel;
    Account account;
    transactionAdapter transactionAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_transaction, container, false);

        initViewModel();
        listView = view.findViewById(R.id.transaction_listview);

        populateList();

        transactionAdapter = new transactionAdapter(transactionList);
        listView.setAdapter(transactionAdapter);

        return view;
    }

    public void initViewModel() {
        this.accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        this.account = accountViewModel.getAccount();
        this.accountViewModel.setLifecycleOwner(this);
        this.transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
    }

    public void populateList() {
        transactionViewModel.getAllTransaction(account.id).removeObservers(this);
        transactionViewModel.getAllTransaction(account.id).observe(this, new Observer<com.example.sinbike.Repositories.common.Resource<List<Transaction>>>() {
            @Override
            public void onChanged(Resource<List<Transaction>> listResource) {
                transactionList.clear();
                transactionList.addAll(listResource.data());
                transactionAdapter.notifyDataSetChanged();
            }
        });
    }
}
