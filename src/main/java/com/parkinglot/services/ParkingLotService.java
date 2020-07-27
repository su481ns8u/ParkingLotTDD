package com.parkinglot.services;

import com.parkinglot.enums.ParkingType;
import com.parkinglot.exceptions.ParkingLotException;
import com.parkinglot.models.Attendant;
import com.parkinglot.models.ParkSlot;
import com.parkinglot.models.ParkingLot;
import com.parkinglot.models.Vehicle;
import com.parkinglot.observers.ParkingLotObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.parkinglot.enums.DriverType.NORMAL;
import static com.parkinglot.exceptions.ParkingLotException.ExceptionType.*;

public class ParkingLotService {
    private final List<ParkingLotObserver> observers;
    private final List<ParkingLot> lotList;
    private final List<Attendant> lotAttendants;
    private List<ParkSlot> currentSlotList;
    private ParkingLot currentLot;

    public ParkingLotService() {
        lotList = new ArrayList<>();
        currentSlotList = new ArrayList<>();
        observers = new ArrayList<>();
        lotAttendants = new ArrayList<>();
    }

    public void registerAttendant(Attendant attendant) {
        lotAttendants.add(attendant);
    }

    public void addLot(ParkingLot parkingLot) {
        lotList.add(parkingLot);
    }

    public void addObserver(ParkingLotObserver parkingLotObservers) {
        observers.add(parkingLotObservers);
    }

    public int getSlotToPark(ParkingType parkingType) {
        currentLot = parkingType.getLot(lotList);
        currentSlotList = currentLot.getParkSlots();
        return currentLot.getEmptySlots().get(0);
    }

    public void park(Vehicle vehicle, Attendant attendant, ParkingType... parkingTypes) throws ParkingLotException {
        if (vehicle == null) throw new ParkingLotException(INVALID_VEHICLE);
        if (this.parkStatus(vehicle)) throw new ParkingLotException(CAR_ALREADY_PARKED);
        if (areAllLotsFull()) throw new ParkingLotException(LOT_FULL);
        if (!this.lotAttendants.contains(attendant)) throw new ParkingLotException(ATTENDANT_NOT_REGISTERED);
        int validSlot;
        if (parkingTypes.length == 1) validSlot = this.getSlotToPark(parkingTypes[0]);
        else validSlot = this.getSlotToPark(NORMAL);
        currentSlotList.set(validSlot, new ParkSlot(attendant, vehicle));
        currentLot.setParkSlots(currentSlotList);
        this.notifyObservers();
    }

    public void unPark(Vehicle vehicle) throws ParkingLotException {
        if (vehicle == null) throw new ParkingLotException(INVALID_VEHICLE);
        if (!this.parkStatus(vehicle)) throw new ParkingLotException(NO_SUCH_VEHICLE);
        lotList.forEach(parkingLot -> parkingLot.getParkSlots()
                .stream()
                .filter(Objects::nonNull)
                .filter(parkSlot -> parkSlot.getVehicle().equals(vehicle))
                .forEachOrdered(parkSlot -> parkingLot.getParkSlots()
                        .set(parkingLot.getParkSlots().indexOf(parkSlot), null)));
        this.notifyObservers();
    }

    public boolean parkStatus(Vehicle vehicle) {
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

    public String getVehicleLocation(Vehicle vehicle) throws ParkingLotException {
        int START_OF_CHARS = 65;
        ParkSlot refParkSlot = lotList.stream()
                .flatMap(parkingLot -> parkingLot.getParkSlots().stream())
                .filter(Objects::nonNull)
                .filter(parkSlot -> parkSlot.getVehicle().equals(vehicle))
                .findFirst()
                .orElseThrow(() -> new ParkingLotException(NO_SUCH_VEHICLE));
        ParkingLot refParkLot = lotList.stream()
                .filter(parkingLot -> parkingLot.getParkSlots().contains(refParkSlot))
                .findFirst()
                .orElseThrow();
        return (char) (lotList.indexOf(refParkLot) + START_OF_CHARS)
                + " "
                + refParkLot.getParkSlots().indexOf(refParkSlot);
    }

    public int getParkTime(Vehicle vehicle) throws ParkingLotException {
        return lotList.stream()
                .flatMap(parkingLot -> parkingLot.getParkSlots().stream())
                .filter(Objects::nonNull)
                .filter(parkSlot -> parkSlot.getVehicle().equals(vehicle))
                .findFirst().map(ParkSlot::getParkTimeInSecs)
                .orElseThrow(() -> new ParkingLotException(NO_SUCH_VEHICLE));
    }

    public List<ParkingLot> getLotList() {
        return lotList;
    }
}
