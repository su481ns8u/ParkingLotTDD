package com.parkinglot.enums;

import com.parkinglot.models.ParkingLot;

import java.util.List;

public interface ParkingType {
    ParkingLot getLot(List<ParkingLot> lotList);
}
