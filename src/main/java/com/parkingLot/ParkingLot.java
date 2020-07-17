package com.parkingLot;

import static com.parkingLot.ParkingLotException.ExceptionType.SPACE_NOT_AVAILABLE;

public class ParkingLot {
    private static Object parkSpace = null;

    public boolean park(Vehicle car) throws ParkingLotException {
        if (parkSpace == null) {
            parkSpace = car;
            return true;
        }
        throw new ParkingLotException(SPACE_NOT_AVAILABLE);
    }
}
