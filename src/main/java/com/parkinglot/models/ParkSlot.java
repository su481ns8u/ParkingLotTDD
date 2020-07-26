package com.parkinglot.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class ParkSlot {
    private final int parkTime;
    private final Vehicle vehicle;
    private final Attendant attendant;

    public ParkSlot(Attendant attendant, Vehicle vehicle) {
        this.vehicle = vehicle;
        this.attendant = attendant;
        this.parkTime = LocalDateTime.now().getSecond();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getParkTime() {
        return parkTime;
    }

    public Attendant getAttendant() {
        return attendant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkSlot parkSlot = (ParkSlot) o;
        return parkTime == parkSlot.parkTime &&
                Objects.equals(vehicle, parkSlot.vehicle) &&
                Objects.equals(attendant, parkSlot.attendant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parkTime, vehicle, attendant);
    }
}
