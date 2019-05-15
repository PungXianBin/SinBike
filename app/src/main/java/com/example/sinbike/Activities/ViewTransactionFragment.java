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
import com.google.firebase.Timestamp;

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
    List<String> type = new ArrayList<>();
    List<Timestamp> date = new ArrayList<>();
    List<Double> amount = new ArrayList<>();
    List<String> accountId = new ArrayList<>();
    List<Transaction> transactionList1 = new ArrayList<>();
    List<Transaction> transactionList2 = new ArrayList<>();
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

        transactionAdapter = new transactionAdapter(transactionList2);
        listView.setAdapter(transactionAdapter);

        return view;
    }

    public void initViewModel() {
        this.accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        this.account = accountViewModel.getAccount();
        this.accountViewModel.setLifecycleOwner(this);
        this.transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
    }

    public List<Transaction> populateList() {
        transactionViewModel.getAllTransaction(account.id).removeObservers(this);
        transactionViewModel.getAllTransaction(account.id).observe(this, new Observer<com.example.sinbike.Repositories.common.Resource<List<Transaction>>>() {
            @Override
            public void onChanged(Resource<List<Transaction>> listResource) {
                transactionList = listResource.data();
                for (int y = 0; y < transactionList.size(); y++) {
                    transactionList1.add(transactionList.get(y));
                }
                getData();

            }
        });
        return transactionList1;
    }

    public void getData(){
        for(int r =0; r < transactionList1.size(); r++){
            type.add(transactionList1.get(r).getTransactionType());
            date.add(transactionList1.get(r).gettransactionDate());
            amount.add(transactionList1.get(r).getAmount());
            accountId.add(transactionList1.get(r).getAccountId());

            Transaction transaction = new Transaction(amount.get(r), date.get(r), accountId.get(r), type.get(r));
            transactionList2.add(transaction);
            transactionAdapter.notifyDataSetChanged();
        }
        listView.refreshDrawableState();
    }
}
