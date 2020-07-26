package com.parkinglot.observers;

public interface ParkingLotObserver {
    void isCapacityFull(boolean isFull);

    boolean capacityFullStatus();
}
