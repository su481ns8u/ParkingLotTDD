package com.parkingLot.observers;

import com.parkingLot.exceptions.ParkingLotException;
import com.parkingLot.models.Vehicle;
import com.parkingLot.services.ParkingLot;

public class VehicleDriver {
    public int findVehicle(Vehicle vehicle, ParkingLot parkingLot) throws ParkingLotException {
        return parkingLot.getCarLocation(vehicle);
    }
}
