package com.example.sinbike.Observers;

import com.example.sinbike.POJO.Bicycle;

import java.util.List;

public interface BicycleViewModelObserver {
    void showBicycleList(List<Bicycle> bicycleList);
}
