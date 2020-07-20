package com.parkingLot.services;

import com.parkingLot.exceptions.ParkingLotException;

import static com.parkingLot.exceptions.ParkingLotException.ExceptionType.*;
import static com.parkingLot.enums.Users.*;

public class ParkingLot {
    private Object parkSpace = null;

    public ParkingLot() {
        this.setStatus();
    }

    public void park(Object car) throws ParkingLotException {
        if (parkSpace == car) throw new ParkingLotException(CAR_ALREADY_PARKED);
        if (car == null) throw new ParkingLotException(INVALID_VEHICLE);
        if (parkSpace != null) throw new ParkingLotException(SPACE_NOT_AVAILABLE);
        parkSpace = car;
        this.setStatus();
    }

    public void unPark(Object car) throws ParkingLotException {
        if (parkSpace == null) throw new ParkingLotException(PARK_SPACE_EMPTY);
        if (parkSpace != car) throw new ParkingLotException(NO_SUCH_VEHICLE);
        parkSpace = null;
        this.setStatus();
    }

    public boolean parkStatue(Object car) {
        return parkSpace == car;
    }

    private void setStatus() {
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