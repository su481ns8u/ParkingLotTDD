package com.parkingLot.observers;

import com.parkingLot.exceptions.ParkingLotException;
import com.parkingLot.services.ParkingLot;

public class VehicleDriver {
    public int findVehicle(Object vehicle, ParkingLot parkingLot) throws ParkingLotException {
        return parkingLot.getCarLocation(vehicle);
    }
}
