package com.parkingLot.services;

import com.parkingLot.enums.LotObservers;
import com.parkingLot.exceptions.ParkingLotException;

import java.util.ArrayList;
import java.util.List;

import static com.parkingLot.exceptions.ParkingLotException.ExceptionType.*;
import static com.parkingLot.enums.Users.*;

public class ParkingLot {
    private final int lotSize;
    private final LotObservers observers;
    private final List<Object> vehicles;

    public ParkingLot(int lotSize) {
        observers = new LotObservers();
        vehicles = new ArrayList<>();
        this.lotSize = lotSize;
    }

    public void park(Object car) throws ParkingLotException {
        if (vehicles.contains(car)) throw new ParkingLotException(CAR_ALREADY_PARKED);
        if (car == null) throw new ParkingLotException(INVALID_VEHICLE);
        if (vehicles.size() == lotSize) throw new ParkingLotException(SPACE_NOT_AVAILABLE);
        vehicles.add(car);
        this.setStatus();
    }

    public void unPark(Object car) throws ParkingLotException {
        if (vehicles.size() == 0) throw new ParkingLotException(PARK_SPACE_EMPTY);
        if (!vehicles.contains(car)) throw new ParkingLotException(NO_SUCH_VEHICLE);
        vehicles.remove(car);
        this.setStatus();
    }

    public boolean parkStatue(Object car) {
        return vehicles.contains(car);
    }

    private void setStatus() {
        if (vehicles.size() < lotSize) observers.informLotFullStatus(false);
        if (vehicles.size() == lotSize) observers.informLotFullStatus(true);
    }

    public boolean fullSignStatus() {
        return OWNER.status;
    }

    public boolean securityStatus() {
        return SECURITY.status;
    }
}