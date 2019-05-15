package com.example.sinbike.Observers;

import com.example.sinbike.POJO.Rental;

import java.util.List;

public interface RentalViewModelObserver {
    void showRentalList(List<Rental> rentalList);
}
