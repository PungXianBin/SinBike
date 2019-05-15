package com.example.sinbike.POJO;

import com.example.sinbike.Repositories.common.Model;
import com.google.firebase.firestore.GeoPoint;

public class ParkingLot extends Model {

    private GeoPoint address;

    public ParkingLot() {
    }

    public ParkingLot(GeoPoint address) {
        this.address = address;
    }

    public GeoPoint getAddress() {
        return address;
    }

    public void setAddress(GeoPoint address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "ParkingLot{" +
                "address=" + address +
                ", id='" + id + '\'' +
                '}';
    }
}
