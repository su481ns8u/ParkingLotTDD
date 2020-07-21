package com.parkingLot.services;

import com.parkingLot.exceptions.ParkingLotException;
import com.parkingLot.observers.NotifyObservers;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.parkingLot.exceptions.ParkingLotException.ExceptionType.*;

public class ParkingLot {
    private final NotifyObservers notify;
    private final HashMap<Integer, Object> vehicles;

    /**
     * Constructor to initialize parking lot
     * Sets all lots to null
     *
     * @param lotSize size of parking lot
     */
    public ParkingLot(int lotSize) {
        notify = new NotifyObservers();
        vehicles = new HashMap<>();
        IntStream.range(0, lotSize).forEach(i -> vehicles.put(i, null));
    }

    /**
     * Park vehicle by lot number and car object
     *
     * @param lotNum lot number of place to park
     * @param car    object of vehicle
     * @throws ParkingLotException if given car is already parked,
     *                             if vehicle is invalid,
     *                             space is already occupied,
     *                             parking lot is full
     */
    public void park(int lotNum, Object car) throws ParkingLotException {
        if (vehicles.containsValue(car)) throw new ParkingLotException(CAR_ALREADY_PARKED);
        if (car == null) throw new ParkingLotException(INVALID_VEHICLE);
        if (!this.getEmptyLots().contains(lotNum)) throw new ParkingLotException(SPACE_OCCUPIED);
        if (!vehicles.containsValue(null)) throw new ParkingLotException(LOT_FULL);
        vehicles.put(lotNum, car);
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
     * @param car Vehicle to find location
     * @return location of vehicle
     * @throws ParkingLotException if vehicle not exists
     */
    public int getCarLocation(Object car) throws ParkingLotException {
        try {
            return vehicles.keySet()
                    .stream()
                    .filter(key -> car.equals(vehicles.get(key)))
                    .findFirst()
                    .get();
        } catch (NoSuchElementException e) {
            throw new ParkingLotException(NO_SUCH_VEHICLE);
        }
    }

    /**
     * Un-park car
     *
     * @param car is object of vehicle tobe un-parked
     * @throws ParkingLotException if No vehicles in park space,
     *                             invalid vehicle
     */
    public void unPark(Object car) throws ParkingLotException {
        if (vehicles.values()
                .stream()
                .distinct()
                .limit(2)
                .count() < 2) throw new ParkingLotException(PARK_SPACE_EMPTY);
        if (!vehicles.containsValue(car)) throw new ParkingLotException(NO_SUCH_VEHICLE);
        if (car == null) throw new ParkingLotException(INVALID_VEHICLE);
        vehicles.replace(this.getCarLocation(car), car, null);
        this.notifyObserver();
    }

    /**
     * Check the parking status of vehicle
     *
     * @param car is object of vehicle tobe checked
     * @return vehicle parked or not
     */
    public boolean parkStatue(Object car) {
        return vehicles.containsValue(car);
    }

    /**
     * Notify observers when space is available or full
     */
    private void notifyObserver() {
        if (vehicles.containsValue(null)) notify.informLotFullStatus(false);
        if (!vehicles.containsValue(null)) notify.informLotFullStatus(true);
    }
}