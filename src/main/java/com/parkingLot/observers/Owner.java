package com.parkingLot.observers;

import com.parkingLot.exceptions.ParkingLotException;
import com.parkingLot.models.ParkedVehicle;
import com.parkingLot.services.ParkingLot;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Owner implements ParkingLotObserver {
    private boolean isFull;
    Attendant attendant;
    private final List<ParkingLot> parkingLotList;
    ParkingLot currentActiveLot;

    public Owner() {
        attendant = new Attendant();
        parkingLotList = new LinkedList<>();
    }

    public void addLot(ParkingLot parkingLot) {
        this.parkingLotList.add(parkingLot);
    }

    @Override
    public void isCapacityFull(boolean isFull) {
        this.isFull = isFull;
    }

    @Override
    public boolean capacityFullStatus() {
        return this.isFull;
    }

    public int selectParkSpace(ParkingLot parkingLot) {
        List<Integer> emptyLots = parkingLot.getEmptyLots();
        return emptyLots.get(0);
    }

    public int selectParkSpace() {
        parkingLotList.sort(Comparator
                .comparing(list -> list
                        .getEmptyLots().size(), Comparator.reverseOrder()));
        this.currentActiveLot = parkingLotList.get(0);
        return currentActiveLot.getEmptyLots().get(0);
    }

    public void informAttendantAndPark(ParkedVehicle vehicle, ParkingLot parkingLot) throws ParkingLotException {
        int selectedSpace = this.selectParkSpace(parkingLot);
        attendant.park(selectedSpace, vehicle, parkingLot);
    }

    public void informAttendantAndPark(ParkedVehicle vehicle) throws ParkingLotException {
        int selectedSpace = this.selectParkSpace();
        attendant.park(selectedSpace, vehicle, currentActiveLot);
    }
}
