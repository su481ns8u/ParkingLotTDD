package com.parkingLot.observers;

import com.parkingLot.services.ParkingLot;

import java.util.List;
import java.util.Random;

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

    public int selectLot(ParkingLot parkingLot) {
        List<Integer> emptyLots = parkingLot.getEmptyLots();
        return emptyLots.get(new Random().nextInt(emptyLots.size()));
    }
}
