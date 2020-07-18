package com.parkingLot.services;

import com.parkingLot.models.Vehicle;
import com.parkingLot.exceptions.ParkingLotException;

import java.util.*;

import static com.parkingLot.exceptions.ParkingLotException.ExceptionType.*;

public class ParkingLot {
    private final int TOTAL_NO_OF_LOTS = 2;
    List<Vehicle> lotList = new ArrayList<>();

    public boolean park(Vehicle car) throws ParkingLotException {
        if (lotList.contains(car)) throw new ParkingLotException(VEHICLE_ALREADY_EXISTS);
        if (lotList.size() < TOTAL_NO_OF_LOTS) {
            lotList.add(car);
            return true;
        }
        throw new ParkingLotException(SPACE_NOT_AVAILABLE);
    }

    public boolean unPark(Vehicle car) throws ParkingLotException {
        if (lotList.size() == 0) throw new ParkingLotException(PARK_SPACE_EMPTY);
        if (lotList.contains(car)) {
            lotList.remove(car);
            return true;
        }
        throw new ParkingLotException(NO_SUCH_VEHICLE);
    }

    public boolean putFullSign() {
        return lotList.size() == TOTAL_NO_OF_LOTS;
    }

    public boolean redirectSecurity() {
        return lotList.size() == TOTAL_NO_OF_LOTS;
    }

    public boolean takeInFullSign() {
        return lotList.size() < TOTAL_NO_OF_LOTS;
    }
}
