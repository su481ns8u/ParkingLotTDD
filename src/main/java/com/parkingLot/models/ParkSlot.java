package com.parkingLot.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class ParkSlot {
    private final LocalDateTime parkTime;
    private final Object vehicle;

    public ParkSlot(Object vehicle) {
        this.vehicle = vehicle;
        this.parkTime = LocalDateTime.now();
    }

    public Object getVehicle() {
        return vehicle;
    }

    public LocalDateTime getParkTime() {
        return parkTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkSlot vehicle = (ParkSlot) o;
        return Objects.equals(this.vehicle, vehicle.vehicle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parkTime, vehicle);
    }
}
