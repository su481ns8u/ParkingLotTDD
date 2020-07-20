package com.parkingLot;

import static com.parkingLot.ParkingLotException.ExceptionType.*;
import static com.parkingLot.Users.*;

public class ParkingLot {
    private Object parkSpace = null;

    ParkingLot() {
        this.changeStatus();
    }

    public void park(Object car) throws ParkingLotException {
        if (car == null) throw new ParkingLotException(INVALID_VEHICLE);
        if (parkSpace != null) throw new ParkingLotException(SPACE_NOT_AVAILABLE);
        parkSpace = car;
        this.changeStatus();
    }

    public void unPark(Object car) throws ParkingLotException {
        if (parkSpace == null) throw new ParkingLotException(PARK_SPACE_EMPTY);
        if (parkSpace != car) throw new ParkingLotException(NO_SUCH_VEHICLE);
        parkSpace = null;
        this.changeStatus();
    }

    public boolean parkStatue(Object car) {
        return parkSpace == car;
    }

    private void changeStatus() {
        if (parkSpace == null) OWNER.status = false;
        if (parkSpace != null) {
            OWNER.status = true;
            SECURITY.status = true;
        }
    }

    public boolean fullSignStatus() {
        return OWNER.status;
    }

    public boolean securityStatus() {
        return SECURITY.status;
    }
}