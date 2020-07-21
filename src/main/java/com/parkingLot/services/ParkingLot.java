package com.parkingLot.services;

import com.parkingLot.observers.LotObservers;
import com.parkingLot.exceptions.ParkingLotException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.parkingLot.exceptions.ParkingLotException.ExceptionType.*;

public class ParkingLot {
    private final LotObservers observers;
    private final HashMap<Integer, Object> vehicles;

    public ParkingLot(int lotSize) {
        observers = new LotObservers();
        vehicles = new HashMap<>();
        IntStream.range(0, lotSize).forEach(i -> vehicles.put(i, null));
    }

    public void park(int lotNum, Object car) throws ParkingLotException {
        if (vehicles.containsValue(car)) throw new ParkingLotException(CAR_ALREADY_PARKED);
        if (car == null) throw new ParkingLotException(INVALID_VEHICLE);
        if (!this.getEmptyLots().contains(lotNum)) throw new ParkingLotException(LOT_NOT_AVAILABLE);
        if (!vehicles.containsValue(null)) throw new ParkingLotException(SPACE_NOT_AVAILABLE);
        vehicles.put(lotNum, car);
        this.notifyObserver();
    }

    public List<Integer> getEmptyLots() {
        return vehicles.entrySet()
                .stream()
                .filter(v -> Objects.equals(v.getValue(), null))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public void unPark(Object car) throws ParkingLotException {
        if (vehicles.size() == 0) throw new ParkingLotException(PARK_SPACE_EMPTY);
        if (!vehicles.containsValue(car)) throw new ParkingLotException(NO_SUCH_VEHICLE);
        vehicles.entrySet()
                .removeIf(entry -> car.equals(entry.getValue()));
        this.notifyObserver();
    }

    public boolean parkStatue(Object car) {
        return vehicles.containsValue(car);
    }

    private void notifyObserver() {
        if (vehicles.containsValue(null)) observers.informLotFullStatus(false);
        if (!vehicles.containsValue(null)) observers.informLotFullStatus(true);
    }
}