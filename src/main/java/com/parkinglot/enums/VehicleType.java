package com.parkinglot.enums;

import com.parkinglot.models.ParkingLot;

import java.util.List;

import static com.parkinglot.enums.DriverType.NORMAL;

public enum VehicleType implements ParkingType {
    SMALL {
        @Override
        public ParkingLot getLot(List<ParkingLot> lotList) {
            return NORMAL.getLot(lotList);
        }
    },

    LARGE {
        @Override
        public ParkingLot getLot(List<ParkingLot> lotList) {
            ParkingLot currentLot = lotList.get(0);
            int num = currentLot.getEmptySlots().size();
            for (ParkingLot parkingLot : lotList)
                if (parkingLot.getEmptySlots().size() > num) {
                    currentLot = parkingLot;
                    num = currentLot.getEmptySlots().size();
                }
            return currentLot;
        }
    }
}
