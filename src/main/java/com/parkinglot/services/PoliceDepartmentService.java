package com.parkinglot.services;

import com.parkinglot.enums.InvestigationPredicates;
import com.parkinglot.models.ParkSlot;
import com.parkinglot.models.ParkingLot;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.parkinglot.enums.DriverType.HANDICAP;
import static com.parkinglot.enums.VehicleType.SMALL;

public class PoliceDepartmentService {
    ParkingLotService parkingLotService;
    List<ParkingLot> lotList;

    public PoliceDepartmentService(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    public List<ParkSlot> investigation(InvestigationPredicates investigationPredicates) {
        return parkingLotService.getLotList()
                .stream()
                .flatMap(parkingLot -> parkingLot.getParkSlots().stream())
                .filter(Objects::nonNull)
                .filter(investigationPredicates.comparisionType)
                .collect(Collectors.toList());
    }

    public List<ParkSlot> handicapPermitFraud() {
        lotList = parkingLotService.getLotList();
        return lotList.stream()
                .filter(parkingLot -> lotList.indexOf(parkingLot) % 2 != 0)
                .flatMap(parkingLot -> parkingLot.getParkSlots()
                        .stream())
                .filter(Objects::nonNull)
                .filter(parkSlot -> parkSlot.getVehicle()
                        .getDriverType().equals(HANDICAP)
                        && parkSlot.getVehicle().getVehicleType().equals(SMALL))
                .collect(Collectors.toList());
    }

    public List<String> getAllParkedVehicleNumberPlates() {
        return parkingLotService.getLotList()
                .stream()
                .flatMap(parkingLot -> parkingLot.getParkSlots().stream())
                .filter(Objects::nonNull)
                .map(parkSlot -> parkSlot.getVehicle().getPlateNumber())
                .collect(Collectors.toList());
    }
}
