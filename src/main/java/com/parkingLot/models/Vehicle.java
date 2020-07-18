package com.parkingLot.models;

import java.util.Objects;

public class Vehicle {
    String color;
    String numberPlate;
    String make;

    public Vehicle(String color, String numberPlate, String make) {
        this.color = color;
        this.numberPlate = numberPlate;
        this.make = make;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(color, vehicle.color) &&
                Objects.equals(numberPlate, vehicle.numberPlate) &&
                Objects.equals(make, vehicle.make);
    }
}
