package com.parkingLot;

import static com.parkingLot.ParkingLotException.ExceptionType.*;

public class ParkingLot {
    private Object parkSpace = null;

    public boolean park(Vehicle car) throws ParkingLotException {
        if (parkSpace == null) {
            parkSpace = car;
            return true;
        }
        throw new ParkingLotException(SPACE_NOT_AVAILABLE);
    }

    public boolean unPark(Vehicle car) throws ParkingLotException {
        if (parkSpace == null) throw new ParkingLotException(PARK_SPACE_EMPTY);
        if (parkSpace == car) {
            parkSpace = null;
            return true;
        }
        throw new ParkingLotException(NO_SUCH_VEHICLE);
    }

    public boolean putFullSign() {
        return parkSpace != null;
    }

    public boolean redirectSecurity() {
        return parkSpace != null;
    }

    public boolean takeInFullSign() {
        return parkSpace == null;
    }
}
