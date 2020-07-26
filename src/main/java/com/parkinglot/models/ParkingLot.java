package com.parkinglot.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;

public class ParkingLot {
    private final int lotSize;
    private List<ParkSlot> parkSlots;

    public ParkingLot(int lotSize) {
        parkSlots = new ArrayList<>();
        range(0, lotSize).forEach(slot -> parkSlots.add(null));
        this.lotSize = lotSize;
    }

    public List<ParkSlot> getParkSlots() {
        return parkSlots;
    }

    public void setParkSlots(List<ParkSlot> parkSlots) {
        this.parkSlots = parkSlots;
    }

    public List<Integer> getEmptySlots() {
        List<Integer> emptyLotList;
        emptyLotList = parkSlots.stream()
                .filter(Objects::isNull)
                .map(parkSlot -> parkSlots.indexOf(null))
                .collect(Collectors.toList());
        return emptyLotList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingLot that = (ParkingLot) o;
        return lotSize == that.lotSize &&
                Objects.equals(parkSlots, that.parkSlots);
    }
}
