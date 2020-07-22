package com.parkingLot.services;

import com.parkingLot.models.Vehicle;
import com.parkingLot.observers.ParkingLotObserver;
import com.parkingLot.exceptions.ParkingLotException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.parkingLot.exceptions.ParkingLotException.ExceptionType.*;
import static java.time.LocalDateTime.*;

public class ParkingLot {
    private static final int FARE_PER_SECOND = 10;
    private final HashMap<Integer, Vehicle> vehicles;
    private final List<ParkingLotObserver> parkingLotObserver;

    /**
     * Constructor to initialize parking lot
     * Sets all lots to null
     *
     * @param lotSize size of parking lot
     */
    public ParkingLot(int lotSize) {
        this.parkingLotObserver = new ArrayList<>();
        vehicles = new HashMap<>();
        IntStream.range(0, lotSize).forEach(i -> vehicles.put(i, null));
    }


    public void registerObserver(ParkingLotObserver parkingLotObserver) {
        this.parkingLotObserver.add(parkingLotObserver);
    }

    /**
     * Park vehicle by lot number and car object
     *
     * @param lotNum lot number of place to park
     * @param vehicle    object of vehicle
     * @throws ParkingLotException if given car is already parked,
     *                             if vehicle is invalid,
     *                             space is already occupied,
     *                             parking lot is full
     */
    public void park(int lotNum, Vehicle vehicle) throws ParkingLotException {
        if (vehicles.containsValue(vehicle)) throw new ParkingLotException(CAR_ALREADY_PARKED);
        if (vehicle == null) throw new ParkingLotException(INVALID_VEHICLE);
        if (!this.getEmptyLots().contains(lotNum)) throw new ParkingLotException(SPACE_OCCUPIED);
        if (!vehicles.containsValue(null)) throw new ParkingLotException(LOT_FULL);
        vehicles.put(lotNum, vehicle);
        this.notifyObserver();
    }

    /**
     * gets the list of empty lots
     *
     * @return list of lots with null values
     */
    public List<Integer> getEmptyLots() {
        return vehicles.entrySet()
                .stream()
                .filter(v -> Objects.equals(v.getValue(), null))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Gives driver the location of vehicle
     *
     * @param vehicle Vehicle to find location
     * @return location of vehicle
     * @throws ParkingLotException if vehicle not exists
     */
    public int getCarLocation(Vehicle vehicle) throws ParkingLotException {
        try {
            return vehicles.keySet()
                    .stream()
                    .filter(key -> vehicle.equals(vehicles.get(key)))
                    .findFirst()
                    .get();
        } catch (NoSuchElementException e) {
            throw new ParkingLotException(NO_SUCH_VEHICLE);
        }
    }

    /**
     * Un-park car
     *
     * @param vehicle is object of vehicle tobe un-parked
     * @throws ParkingLotException if No vehicles in park space,
     *                             invalid vehicle
     */
    public long unPark(Vehicle vehicle) throws ParkingLotException {
        if (vehicles.values()
                .stream()
                .distinct()
                .limit(2)
                .count() < 2) throw new ParkingLotException(PARK_SPACE_EMPTY);
        if (!vehicles.containsValue(vehicle)) throw new ParkingLotException(NO_SUCH_VEHICLE);
        if (vehicle == null) throw new ParkingLotException(INVALID_VEHICLE);
        vehicles.replace(this.getCarLocation(vehicle), vehicle, null);
        Duration duration = Duration.between(vehicle.getParkTime(), LocalDateTime.now());
        long seconds = duration.getSeconds();
        this.notifyObserver();
        return seconds * FARE_PER_SECOND;
    }

    /**
     * Check the parking status of vehicle
     *
     * @param vehicle is object of vehicle tobe checked
     * @return vehicle parked or not
     */
    public boolean parkStatue(Vehicle vehicle) {
        return vehicles.containsValue(vehicle);
    }

    /**
     * Notify observers when space is available or full
     */
    public void notifyObserver() {
        if (vehicles.containsValue(null)) parkingLotObserver.forEach(observer -> observer.isCapacityFull(false));
        if (!vehicles.containsValue(null)) parkingLotObserver.forEach(observer -> observer.isCapacityFull(true));
    }
}