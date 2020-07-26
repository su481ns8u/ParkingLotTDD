package com.parkingLot.enums;

import com.parkingLot.models.ParkingLot;

import java.util.List;

public interface ParkingType {
    ParkingLot getLot(List<ParkingLot> lotList);
}
