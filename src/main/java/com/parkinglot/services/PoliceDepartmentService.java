package com.parkinglot.services;

import com.parkinglot.exceptions.ParkingLotException;
import com.parkinglot.models.ParkSlot;
import com.parkinglot.models.ParkingLot;
import com.parkinglot.services.ParkingLotService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.parkinglot.enums.CarMakes.BMW;
import static com.parkinglot.enums.CarMakes.TOYOTA;
import static com.parkinglot.enums.DriverType.HANDICAP;
import static com.parkinglot.enums.VehicleColor.BLUE;
import static com.parkinglot.enums.VehicleColor.WHITE;
import static com.parkinglot.enums.VehicleType.SMALL;

public class PoliceDepartmentService {
    ParkingLotService parkingLotService;
    List<ParkingLot> lotList;

    public PoliceDepartmentService(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    public List<String> investigateBombThreat() throws ParkingLotException {
        lotList = parkingLotService.getLotList();
        List<String> locations = new ArrayList<>();
        for (ParkingLot parkingLot : lotList)
            for (ParkSlot parkSlot : parkingLot.getParkSlots()) {
                if (parkSlot == null)
                    continue;
                if (parkSlot.getVehicle().getColor().equals(WHITE))
                    locations.add(parkingLotService.getVehicleLocation(parkSlot.getVehicle()));
            }
        return locations;
    }

    public List<String> investigateRobbery() throws ParkingLotException {
        lotList = parkingLotService.getLotList();
        List<String> vehicleInfo = new ArrayList<>();
        for (ParkingLot parkingLot : lotList)
            for (ParkSlot parkSlot : parkingLot.getParkSlots()) {
                if (parkSlot == null)
                    continue;
                if (parkSlot.getVehicle().getColor().equals(BLUE) && parkSlot.getVehicle().getMake().equals(TOYOTA))
                    vehicleInfo.add(parkingLotService.getVehicleLocation(parkSlot.getVehicle())
                            + " " + parkSlot.getVehicle().getPlateNumber()
                            + " " + parkSlot.getAttendant().getName());
            }
        return vehicleInfo;
    }

    public List<String> increaseSecurity() throws ParkingLotException {
        lotList = parkingLotService.getLotList();
        List<String> vehicleInfo = new ArrayList<>();
        for (ParkingLot parkingLot : lotList)
            for (ParkSlot parkSlot : parkingLot.getParkSlots()) {
                if (parkSlot == null)
                    continue;
                if (parkSlot.getVehicle().getMake().equals(BMW))
                    vehicleInfo.add(parkingLotService.getVehicleLocation(parkSlot.getVehicle()));
            }
        return vehicleInfo;
    }

    public List<String> investigateBombThreatBasedOnTime() throws ParkingLotException {
        lotList = parkingLotService.getLotList();
        List<String> vehicleInfo = new ArrayList<>();
        for (ParkingLot parkingLot : lotList)
            for (ParkSlot parkSlot : parkingLot.getParkSlots()) {
                if (parkSlot == null)
                    continue;
                if (Duration.between(parkSlot.getParkTime(), LocalDateTime.now()).getSeconds() <= 3)
                    vehicleInfo.add(parkingLotService.getVehicleLocation(parkSlot.getVehicle()));
            }
        return vehicleInfo;
    }

    public List<String> handicapPermitFraud() throws ParkingLotException {
        lotList = parkingLotService.getLotList();
        List<String> vehicleInfo = new ArrayList<>();
        for (ParkingLot parkingLot : lotList)
            if (lotList.indexOf(parkingLot) % 2 != 0)
                for (ParkSlot parkSlot : parkingLot.getParkSlots()) {
                    if (parkSlot == null)
                        continue;
                    if (parkSlot.getVehicle().getDriverType().equals(HANDICAP)
                            && parkSlot.getVehicle().getVehicleType().equals(SMALL))
                        vehicleInfo.add(parkingLotService.getVehicleLocation(parkSlot.getVehicle()));
                }
        return vehicleInfo;
    }

    public List<String> getAllParkedVehicleNumberPlates() {
        lotList = parkingLotService.getLotList();
        return lotList.stream()
                .flatMap(parkingLot -> parkingLot.getParkSlots().stream())
                .filter(Objects::nonNull)
                .map(parkSlot -> parkSlot.getVehicle().getPlateNumber())
                .collect(Collectors.toList());
    }
}
