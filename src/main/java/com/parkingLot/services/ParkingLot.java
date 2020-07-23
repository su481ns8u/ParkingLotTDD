package com.parkingLot.services;

import com.parkingLot.exceptions.ParkingLotException;
import com.parkingLot.models.ParkedVehicle;
import com.parkingLot.observers.ParkingLotObserver;
import com.parkingLot.observers.VehicleDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.parkingLot.exceptions.ParkingLotException.ExceptionType.*;

public class ParkingLot {
    private final List<ParkedVehicle> vehicleList;
    private final List<ParkingLotObserver> parkingLotObserver;

    public ParkingLot(int lotSize) {
        this.parkingLotObserver = new ArrayList<>();
        vehicleList = new ArrayList<>();
        IntStream.range(0, lotSize).forEach(parkSpace -> vehicleList.add(null));
    }

    public void registerObserver(ParkingLotObserver parkingLotObserver) {
        this.parkingLotObserver.add(parkingLotObserver);
    }

    public void park(int lotNum, ParkedVehicle vehicle, VehicleDriver.DriverType... driverType) throws ParkingLotException {
        if (vehicle == null) throw new ParkingLotException(INVALID_VEHICLE);
        if (vehicleList.contains(vehicle)) throw new ParkingLotException(CAR_ALREADY_PARKED);
        if (!vehicleList.contains(null)) throw new ParkingLotException(LOT_FULL);
        if (!this.getEmptyLots().contains(lotNum)) throw new ParkingLotException(SPACE_OCCUPIED);
        vehicleList.set(lotNum, vehicle);
        vehicle.setParkTime();
        this.notifyObserver();
    }

    public List<Integer> getEmptyLots() {
        return IntStream.range(0, vehicleList.size())
                .filter(index -> vehicleList.get(index) == null)
                .boxed()
                .collect(Collectors.toList());
    }

    public int getCarLocation(ParkedVehicle vehicle) throws ParkingLotException {
        try {
            return vehicleList
                    .stream()
                    .filter(v -> v.equals(vehicle))
                    .map(vehicleList::indexOf)
                    .findFirst()
                    .get();
        } catch (NoSuchElementException | NullPointerException e) {
            throw new ParkingLotException(NO_SUCH_VEHICLE);
        }
    }

    public void unPark(ParkedVehicle vehicle) throws ParkingLotException {
        if (vehicleList.stream().distinct().limit(2).count() < 2) throw new ParkingLotException(PARK_SPACE_EMPTY);
        if (!vehicleList.contains(vehicle)) throw new ParkingLotException(NO_SUCH_VEHICLE);
        if (vehicle == null) throw new ParkingLotException(INVALID_VEHICLE);
        vehicleList.set(vehicleList.indexOf(vehicle), null);
        this.notifyObserver();
    }

    public boolean parkStatue(ParkedVehicle vehicle) {
        return vehicleList.contains(vehicle);
    }

    public void notifyObserver() {
        if (vehicleList.contains(null)) parkingLotObserver.forEach(observer -> observer.isCapacityFull(false));
        if (!vehicleList.contains(null)) parkingLotObserver.forEach(observer -> observer.isCapacityFull(true));
    }
}