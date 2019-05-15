package com.example.sinbike.RecyclerViews.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sinbike.POJO.Fine;
import com.example.sinbike.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CardViewDataAdapter extends
        RecyclerView.Adapter<CardViewDataAdapter.ViewHolder> {

    private List<Fine> stList;

    public CardViewDataAdapter(List<Fine> parkingFine) {
        this.stList = parkingFine;
    }

    // Create new views
    @Override
    public CardViewDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.cardview_row, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final int pos = position;

        Date timestamp = stList.get(position).getFineDate().toDate();
        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        sfd.format(timestamp);

        viewHolder.textTitle.setText("Parking Fine");

        viewHolder.textFineDate.setText("Date: " + timestamp);

        viewHolder.textFineLocation.setText("Location: " + stList.get(position).getLocation());

        viewHolder.textFineAmount.setText("Amount: $" + String.format("%.2f",stList.get(position).getAmount()));

        viewHolder.chkSelected.setChecked(stList.get(position).isSelected());

        viewHolder.chkSelected.setTag(stList.get(position));


        viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Fine contact = (Fine) cb.getTag();

                contact.setSelected(cb.isChecked());
                stList.get(pos).setSelected(cb.isChecked());
            }
        });

    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        return stList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textTitle;
        public TextView textFineDate;
        public TextView textFineLocation;
        public TextView textFineAmount;

        public CheckBox chkSelected;

        public Fine singleParkingFine;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            textTitle = (TextView) itemLayoutView.findViewById(R.id.textTitle);
            textFineDate = (TextView) itemLayoutView.findViewById(R.id.textFineDate);
            textFineLocation = (TextView) itemLayoutView.findViewById(R.id.textFineLocation);
            textFineAmount = (TextView) itemLayoutView.findViewById(R.id.textFineAmount);
            chkSelected = (CheckBox) itemLayoutView.findViewById(R.id.chkSelected);
        }
    }

    // method to access in activity after updating selection
    public List<Fine> getParkingList() {
        return stList;
    }

}
