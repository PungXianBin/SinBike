package com.example.sinbike.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.sinbike.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopUpFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_top_up, container, false);
        Button topupBtn = view.findViewById(R.id.topupBtn);

        EditText amt = view.findViewById(R.id.amt);

        topupBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                double amount = Double.parseDouble(amt.getText().toString());
                if (amount < 10.00 ) {
                    amt.setError("Minimum Top Up Value must be 10SGD");
                    amt.requestFocus();
                }


                else {
                    Intent intent = new Intent(getActivity(),CardFormActivity.class);
                    intent.putExtra("amount", amount);
                    startActivity(intent);
                }
            }
        });

        return view;
    }
}
