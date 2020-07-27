package com.parkinglot.models;

import java.time.LocalDateTime;

public class ParkSlot {
    private final LocalDateTime parkTime;
    private final Vehicle vehicle;
    private final Attendant attendant;

    public ParkSlot(Attendant attendant, Vehicle vehicle) {
        this.vehicle = vehicle;
        this.attendant = attendant;
        this.parkTime = LocalDateTime.now();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getParkTimeInSecs() {
        return parkTime.getSecond();
    }

    public LocalDateTime getParkTime() {
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
        return parkTime == parkSlot.parkTime;
    }
}
