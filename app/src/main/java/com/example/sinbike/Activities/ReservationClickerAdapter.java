package com.example.sinbike.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.sinbike.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;


public class ReservationClickerAdapter implements GoogleMap.InfoWindowAdapter, View.OnClickListener {

    private final View mWindow;
    private Context mContext;

    public ReservationClickerAdapter(Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.activity_reservation_map_pop, null);
    }

    private void rendowWindowText(Marker marker, View view){
        Button reservebtn = view.findViewById(R.id.btnreserve);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }
    
    

    @Override
    public void onClick(View v) {
        
    }

    
        }
    


