package com.example.sinbike.POJO;

import com.example.sinbike.Repositories.common.Model;
import com.google.firebase.firestore.GeoPoint;

public class Bicycle extends Model {
    private GeoPoint coordinate;
    private boolean rented;

    public Bicycle() {
    }

    public Bicycle(GeoPoint coordinate, boolean rented) {
        this.coordinate = coordinate;
        this.rented = rented;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    public GeoPoint getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(GeoPoint coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public String toString() {
        return "Bicycle{" +
                "coordinate=" + coordinate +
                ", id='" + id + '\'' +
                '}';
    }
}
