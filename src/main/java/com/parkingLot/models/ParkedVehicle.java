package com.parkingLot.models;

import com.parkingLot.exceptions.ParkingLotException;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.parkingLot.exceptions.ParkingLotException.ExceptionType.NO_SUCH_VEHICLE;

public class ParkedVehicle {
    LocalDateTime parkTime;
    String name;

    public ParkedVehicle(String name) {
        this.name = name;
    }

    public void setParkTime() {
        this.parkTime = LocalDateTime.now();
    }

    public LocalDateTime getParkTime() throws ParkingLotException {
        if (parkTime == null)
            throw new ParkingLotException(NO_SUCH_VEHICLE);
        return parkTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkedVehicle vehicle = (ParkedVehicle) o;
        return Objects.equals(parkTime, vehicle.parkTime) &&
                Objects.equals(name, vehicle.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parkTime, name);
    }
}
