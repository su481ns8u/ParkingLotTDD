package com.parkinglot.models;

import com.parkinglot.enums.CarMakes;
import com.parkinglot.enums.VehicleColor;

import java.util.Objects;

public class Vehicle {
    VehicleColor color;
    String plateNumber;
    CarMakes make;

    public Vehicle(String plateNumber, CarMakes make, VehicleColor vehicleColor) {
        this.plateNumber = plateNumber;
        this.make = make;
        this.color = vehicleColor;
    }

    public VehicleColor getColor() {
        return color;
    }

    public CarMakes getMake() {
        return make;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return color == vehicle.color &&
                Objects.equals(plateNumber, vehicle.plateNumber) &&
                Objects.equals(make, vehicle.make);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, plateNumber, make);
    }
}
