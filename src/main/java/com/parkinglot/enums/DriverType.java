package com.parkinglot.enums;

import com.parkinglot.models.ParkingLot;

import java.util.List;

public enum DriverType implements ParkingType {
    HANDICAP {
        public ParkingLot getLot(List<ParkingLot> lotList) {
            ParkingLot currentLot = lotList.get(0);
            int num = currentLot.getEmptySlots().get(0);
            for (ParkingLot parkingLot : lotList)
                if (parkingLot.getEmptySlots().get(0) < num) {
                    currentLot = parkingLot;
                    num = currentLot.getEmptySlots().get(0);
                }
            return currentLot;
        }
    },

    NORMAL {
        public ParkingLot getLot(List<ParkingLot> lotList) {
            int num = lotList.get(0).getEmptySlots().size();
            ParkingLot currentLot = lotList.get(0);
            for (ParkingLot parkingLot : lotList)
                if (parkingLot.getEmptySlots().size() > num) {
                    num = parkingLot.getEmptySlots().size();
                    currentLot = parkingLot;
                }
            return currentLot;
        }
    };

    @Override
    public abstract ParkingLot getLot(List<ParkingLot> lotList);
}
