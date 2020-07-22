package com.parkingLot.observers;

public interface ParkingLotObserver {
    void isCapacityFull(boolean isFull);

    boolean capacityFullStatus();
}
