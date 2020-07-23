package com.parkingLot.exceptions;

public class ParkingLotException extends Throwable {
    public enum ExceptionType {
        LOT_FULL("Parking space is not available!"),
        NO_SUCH_VEHICLE("Given vehicle is not present!"),
        PARK_SPACE_EMPTY("No vehicles at parking!"),
        INVALID_VEHICLE("Vehicle must not be null!"),
        CAR_ALREADY_PARKED("Car already parked in lot!"),
        SPACE_OCCUPIED("Lot is already taken or not available!"),
        NO_PARKING_LOT_ASSIGNED("No parking lot is assigned to owner!");

        String message;

        ExceptionType(String message) {
            this.message = message;
        }
    }

    public ExceptionType type;

    public ParkingLotException(ExceptionType type) {
        super(type.message);
        this.type = type;
    }
}