package com.parkingLot.observers;

public class AirportSecurity implements ParkingLotObserver {
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
