package com.example.sinbike.Observers;

import com.example.sinbike.POJO.ParkingLot;

import java.util.List;

public interface ParkingLotViewModelObserver {
    void showParkingLotList(List<ParkingLot> parkingLotList);

}
