package com.parkingLot;

public class ParkingLotException extends Throwable {
    public enum ExceptionType {
        SPACE_NOT_AVAILABLE("Parking space is not available!");

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
