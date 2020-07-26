package com.parkinglot.exceptions;

public class ParkingLotException extends Throwable {
    public ExceptionType type;

    public ParkingLotException(ExceptionType type) {
        super(type.message);
        this.type = type;
    }

    public enum ExceptionType {
        LOT_FULL("Parking space is not available!"),
        NO_SUCH_VEHICLE("Given vehicle is not present!"),
        INVALID_VEHICLE("Vehicle must not be null!"),
        CAR_ALREADY_PARKED("Car already parked in lot!"),
        ATTENDANT_NOT_REGISTERED("Given attendant is not registered with parking lots!");

        String message;

        ExceptionType(String message) {
            this.message = message;
        }
    }
}