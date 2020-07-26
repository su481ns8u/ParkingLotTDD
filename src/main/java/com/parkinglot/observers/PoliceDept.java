package com.parkinglot.observers;

import com.parkinglot.exceptions.ParkingLotException;
import com.parkinglot.models.ParkSlot;
import com.parkinglot.models.ParkingLot;
import com.parkinglot.services.ParkingLotService;

import java.util.ArrayList;
import java.util.List;

import static com.parkinglot.enums.CarMakes.BMW;
import static com.parkinglot.enums.CarMakes.TOYOTA;
import static com.parkinglot.enums.VehicleColor.BLUE;
import static com.parkinglot.enums.VehicleColor.WHITE;

public class PoliceDept {
    ParkingLotService parkingLotService;
    List<ParkingLot> lotList;

    public PoliceDept(ParkingLotService parkingLotService) {
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
                            + " " +parkSlot.getVehicle().getPlateNumber()
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
}
