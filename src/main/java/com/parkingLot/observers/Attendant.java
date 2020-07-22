package com.parkingLot.observers;

import com.parkingLot.exceptions.ParkingLotException;
import com.parkingLot.models.Vehicle;
import com.parkingLot.services.ParkingLot;

public class Attendant {
    public void park(int lotNum, Vehicle vehicle, ParkingLot parkingLot) throws ParkingLotException {
        parkingLot.park(lotNum, vehicle);
    }
}
