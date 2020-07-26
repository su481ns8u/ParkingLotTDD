package com.parkingLot.services;

import com.parkingLot.exceptions.ParkingLotException;
import com.parkingLot.models.ParkSlot;
import com.parkingLot.models.ParkingLot;
import com.parkingLot.observers.ParkingLotObserver;

import java.time.LocalDateTime;
import java.util.*;

import static com.parkingLot.exceptions.ParkingLotException.ExceptionType.*;

public class ParkingLotService {
    private final List<ParkingLotObserver> observers;
    private final List<ParkingLot> lotList;
    private List<ParkSlot> currentSlotList;
    private ParkingLot currentLot;

    public ParkingLotService() {
        lotList = new LinkedList<>();
        currentSlotList = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public void addLot(ParkingLot parkingLot) {
        lotList.add(parkingLot);
    }

    public void addObserver(ParkingLotObserver... parkingLotObservers) {
        observers.addAll(Arrays.asList(parkingLotObservers));
    }

    public int getSlotToPark() {
        lotList.sort(Comparator
                .comparing(list -> list.getEmptySlots()
                        .size(), Comparator.reverseOrder()));
        currentLot = lotList.get(0);
        currentSlotList = currentLot.getParkSlots();
        return currentLot.getEmptySlots().get(0);
    }

    public void park(Object vehicle) throws ParkingLotException {
        if (vehicle == null) throw new ParkingLotException(INVALID_VEHICLE);
        if (this.parkStatus(vehicle)) throw new ParkingLotException(CAR_ALREADY_PARKED);
        if (areAllLotsFull()) throw new ParkingLotException(LOT_FULL);
        int slot = this.getSlotToPark();
        currentSlotList.set(slot, new ParkSlot(vehicle));
        currentLot.setParkSlots(currentSlotList);
        this.notifyObservers();
    }

    public void unPark(Object vehicle) throws ParkingLotException {
        if (vehicle == null) throw new ParkingLotException(INVALID_VEHICLE);
        if (!this.parkStatus(vehicle)) throw new ParkingLotException(NO_SUCH_VEHICLE);
        lotList.forEach(parkingLot -> parkingLot.getParkSlots()
                .stream()
                .filter(Objects::nonNull)
                .filter(parkSlot -> parkSlot.getVehicle()
                        .equals(vehicle))
                .forEachOrdered(parkSlot -> parkingLot.getParkSlots()
                        .set(parkingLot.getParkSlots().indexOf(parkSlot), null)));
        this.notifyObservers();
    }

    public boolean parkStatus(Object vehicle) {
        return lotList.stream()
                .flatMap(parkingLot -> parkingLot.getParkSlots().stream())
                .filter(Objects::nonNull)
                .anyMatch(parkSlot -> parkSlot.getVehicle().equals(vehicle));
    }

    public void notifyObservers() {
        if (areAllLotsFull()) observers.forEach(observer -> observer.isCapacityFull(true));
        if (!areAllLotsFull()) observers.forEach(observer -> observer.isCapacityFull(false));
    }

    public boolean areAllLotsFull() {
        return lotList.stream()
                .flatMap(parkingLot -> parkingLot.getParkSlots().stream())
                .noneMatch(Objects::isNull);
    }

    public String getVehicleLocation(Object vehicle) throws ParkingLotException {
        int START_OF_CHARS = 65;
        for (ParkingLot parkingLot : lotList)
            for (ParkSlot parkSlot : parkingLot.getParkSlots()) {
                if (parkSlot == null)
                    continue;
                if (parkSlot.getVehicle().equals(vehicle))
                    return (char) (lotList.indexOf(parkingLot) + START_OF_CHARS)
                            + " "
                            + parkingLot.getParkSlots().indexOf(parkSlot);
            }
        throw new ParkingLotException(NO_SUCH_VEHICLE);
    }

    public LocalDateTime getParkTime(Object vehicle) throws ParkingLotException {
        return lotList.stream()
                .flatMap(parkingLot -> parkingLot.getParkSlots().stream())
                .filter(Objects::nonNull)
                .filter(parkSlot -> parkSlot.getVehicle().equals(vehicle))
                .findFirst().map(ParkSlot::getParkTime)
                .orElseThrow(() -> new ParkingLotException(NO_SUCH_VEHICLE));
    }
}
