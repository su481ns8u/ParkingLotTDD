package com.parkingLot.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Vehicle {
    LocalDateTime parkTime;
    String name;

    public Vehicle(LocalDateTime parkTime, String name) {
        this.parkTime = parkTime;
        this.name = name;
    }

    public LocalDateTime getParkTime() {
        return parkTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(parkTime, vehicle.parkTime) &&
                Objects.equals(name, vehicle.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parkTime, name);
    }
}
