package com.parkinglot.models;

import com.parkinglot.enums.VehicleColor;

import java.util.Objects;

public class Vehicle {
    String name;
    VehicleColor color;

    public Vehicle(String name, VehicleColor vehicleColor) {
        this.name = name;
        this.color = vehicleColor;
    }

    public VehicleColor getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(name, vehicle.name) &&
                color == vehicle.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color);
    }
}
