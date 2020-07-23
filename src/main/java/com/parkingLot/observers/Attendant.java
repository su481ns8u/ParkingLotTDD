package com.parkingLot.observers;

import com.parkingLot.exceptions.ParkingLotException;
import com.parkingLot.models.ParkedVehicle;
import com.parkingLot.services.ParkingLot;

public class Attendant {
    public void park(int lotNum, ParkedVehicle vehicle, ParkingLot parkingLot) throws ParkingLotException {
        parkingLot.park(lotNum, vehicle);
    }
}
