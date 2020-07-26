package com.parkinglot.models;

import com.parkinglot.enums.CarMakes;
import com.parkinglot.enums.DriverType;
import com.parkinglot.enums.VehicleColor;
import com.parkinglot.enums.VehicleType;

import java.util.Objects;

public class Vehicle {
    private final VehicleColor color;
    private final String plateNumber;
    private final VehicleType vehicleType;
    private final DriverType driverType;
    private final CarMakes make;

    public Vehicle(String plateNumber, CarMakes make, VehicleColor vehicleColor, VehicleType vehicleType,
                   DriverType driverType) {
        this.plateNumber = plateNumber;
        this.make = make;
        this.vehicleType = vehicleType;
        this.driverType = driverType;
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

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public DriverType getDriverType() {
        return driverType;
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
