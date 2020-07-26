package com.parkingLot.enums;

import com.parkingLot.models.ParkingLot;

import java.util.List;

import static com.parkingLot.enums.DriverType.NORMAL;

public enum VehicleType implements ParkingType {
    SMALL {
        public ParkingLot getLot(List<ParkingLot> lotList) {
            return NORMAL.getLot(lotList);
        }
    },

    LARGE {
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
    };

    @Override
    public abstract ParkingLot getLot(List<ParkingLot> lotList);
}
