package com.example.sinbike.POJO;

public class RentalPayment extends Payment {

    private String rentalId;

    public RentalPayment() {
    }

    public RentalPayment(String rentalId) {
        this.rentalId = rentalId;
    }

    public String getRentalId() {
        return rentalId;
    }

    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }

    @Override
    public String toString() {
        return "RentalPayment{" +
                "rentalId='" + rentalId + '\'' +
                '}';
    }
}
