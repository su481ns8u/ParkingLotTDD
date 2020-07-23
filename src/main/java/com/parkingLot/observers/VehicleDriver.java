package com.parkingLot.observers;

import com.parkingLot.exceptions.ParkingLotException;
import com.parkingLot.models.ParkedVehicle;
import com.parkingLot.services.ParkingLot;

public class VehicleDriver {
    public int findVehicle(ParkedVehicle vehicle, ParkingLot parkingLot) throws ParkingLotException {
        return parkingLot.getCarLocation(vehicle);
    }
}
