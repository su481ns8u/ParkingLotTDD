package com.parkingLot.exceptions;

public class ParkingLotException extends Throwable {
    public enum ExceptionType {
        SPACE_NOT_AVAILABLE("Parking space is not available!"),
        NO_SUCH_VEHICLE("Given vehicle is not present!"),
        PARK_SPACE_EMPTY("No vehicles at parking!"),
        VEHICLE_ALREADY_EXISTS("Entered vehicle is already there in lot");

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
