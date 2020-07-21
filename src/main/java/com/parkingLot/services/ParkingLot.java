package com.parkingLot.services;

import com.parkingLot.enums.LotObservers;
import com.parkingLot.exceptions.ParkingLotException;

import java.util.HashMap;

import static com.parkingLot.exceptions.ParkingLotException.ExceptionType.*;

public class ParkingLot {
    private final int lotSize;
    private final LotObservers observers;
    private final HashMap<Integer, Object> vehicles;
    private int lotCount = 0;

    public ParkingLot(int lotSize) {
        observers = new LotObservers();
        vehicles = new HashMap<>();
        this.lotSize = lotSize;
    }

    public void park(Object car) throws ParkingLotException {
        if (vehicles.containsValue(car)) throw new ParkingLotException(CAR_ALREADY_PARKED);
        if (car == null) throw new ParkingLotException(INVALID_VEHICLE);
        if (vehicles.size() == lotSize) throw new ParkingLotException(SPACE_NOT_AVAILABLE);
        vehicles.put(lotCount++, car);
        this.setStatus();
    }

    public void unPark(Object car) throws ParkingLotException {
        if (vehicles.size() == 0) throw new ParkingLotException(PARK_SPACE_EMPTY);
        if (!vehicles.containsValue(car)) throw new ParkingLotException(NO_SUCH_VEHICLE);
        vehicles.entrySet()
                .removeIf(entry -> car.equals(entry.getValue()));
        this.setStatus();
    }

    public boolean parkStatue(Object car) {
        return vehicles.containsValue(car);
    }

    private void setStatus() {
        if (vehicles.size() < lotSize) observers.informLotFullStatus(false);
        if (vehicles.size() == lotSize) observers.informLotFullStatus(true);
    }
}