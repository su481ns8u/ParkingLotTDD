package com.parkingLot.observers;

import com.parkingLot.services.ParkingLot;

import java.util.*;

public class Owner implements ParkingLotObserver {
    private boolean isFull;
//    Map<Integer, ParkingLot> parkLotMap;

//    public Owner() {
//        parkLotMap = new LinkedHashMap<>();
//        IntStream.range(0, 2).forEach(parkLot -> parkLotMap.put(parkLot, new ParkingLot(2)));
//    }

    @Override
    public void isCapacityFull(boolean isFull) {
        this.isFull = isFull;
    }

    @Override
    public boolean capacityFullStatus() {
        return this.isFull;
    }

    public int selectPosition(ParkingLot parkingLot) {
        List<Integer> emptyLots = parkingLot.getEmptyLots();
        return emptyLots.get(new Random().nextInt(emptyLots.size()));
    }

    public ParkingLot selectLot(ParkingLot... parkingLots) {
        Map<Integer, ParkingLot> lotMap = new TreeMap<>();
        Arrays.asList(parkingLots)
                .forEach(parkingLot -> lotMap
                        .put(parkingLot
                                .getEmptyLots()
                                .get(0), parkingLot
                        ));
        return lotMap.get(lotMap.keySet().toArray()[0]);
//        ParkingLot currentLot = parkLotMap.get(0);
//        for (ParkingLot parkingLot : parkLotMap.values()) {
//            if (parkingLot.getEmptyLots().get(0) <= currentLot.getEmptyLots().get(0))
//                currentLot = parkingLot;
//        }
//        return currentLot;
    }
}
