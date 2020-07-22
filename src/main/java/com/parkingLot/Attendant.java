package com.parkingLot;

import com.parkingLot.exceptions.ParkingLotException;
import com.parkingLot.services.ParkingLot;

public class Attendant {
    public void park(int lotNum, Object car, ParkingLot parkingLot) throws ParkingLotException {
        parkingLot.park(lotNum, car);
    }
}
