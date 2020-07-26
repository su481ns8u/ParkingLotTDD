package com.parkingLot.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        List<Integer> emptyLotList = new ArrayList<>();
        int count = 0;
        for (ParkSlot parkSlot : parkSlots) {
            if (parkSlot == null)
                emptyLotList.add(count);
            count++;
        }
        return emptyLotList;
//        return IntStream.range(0, parkSlots.size())
//                .filter(index -> parkSlots.get(index) == null)
//                .boxed()
//                .collect(Collectors.toList());
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
