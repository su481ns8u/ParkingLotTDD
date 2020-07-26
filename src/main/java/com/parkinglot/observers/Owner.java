package com.parkinglot.observers;

public class Owner implements ParkingLotObserver {
    private boolean isFull;

    @Override
    public void isCapacityFull(boolean isFull) {
        this.isFull = isFull;
    }

    @Override
    public boolean capacityFullStatus() {
        return this.isFull;
    }
}
