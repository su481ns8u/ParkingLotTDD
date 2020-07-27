package com.parkinglot;

import com.parkinglot.exceptions.ParkingLotException;
import com.parkinglot.models.Attendant;
import com.parkinglot.models.ParkSlot;
import com.parkinglot.models.ParkingLot;
import com.parkinglot.models.Vehicle;
import com.parkinglot.observers.AirportSecurity;
import com.parkinglot.observers.Owner;
import com.parkinglot.services.ParkingLotService;
import com.parkinglot.services.PoliceDepartmentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.parkinglot.enums.CarMakes.BMW;
import static com.parkinglot.enums.CarMakes.TOYOTA;
import static com.parkinglot.enums.DriverType.HANDICAP;
import static com.parkinglot.enums.DriverType.NORMAL;
import static com.parkinglot.enums.InvestigationPredicates.*;
import static com.parkinglot.enums.VehicleColor.BLUE;
import static com.parkinglot.enums.VehicleColor.WHITE;
import static com.parkinglot.enums.VehicleType.LARGE;
import static com.parkinglot.enums.VehicleType.SMALL;
import static com.parkinglot.exceptions.ParkingLotException.ExceptionType.*;

public class ParkingLotTest {
    ParkingLotService parkingLotService;
    ParkingLot parkingLot1;
    ParkingLot parkingLot2;
    Vehicle firstVehicle;
    Vehicle secondVehicle;
    Vehicle thirdVehicle;
    PoliceDepartmentService policeDepartmentService;
    Attendant attendant;

    @Before
    public void setUp() {
        parkingLot1 = new ParkingLot(2);
        parkingLot2 = new ParkingLot(2);
        parkingLotService = new ParkingLotService();
        parkingLotService.addLot(parkingLot1);
        parkingLotService.addLot(parkingLot2);
        policeDepartmentService = new PoliceDepartmentService(parkingLotService);
        attendant = new Attendant("Ramesh");
        parkingLotService.registerAttendant(attendant);
        firstVehicle = new Vehicle("MH02E4957", TOYOTA, BLUE, SMALL, NORMAL);
        secondVehicle = new Vehicle("MH02E5689", BMW, WHITE, LARGE, HANDICAP);
        thirdVehicle = new Vehicle("MH02E5658", TOYOTA, WHITE, SMALL, HANDICAP);
    }

    @Test
    public void givenACarObject_WhenParking_IfAvailableReturnsTrue() {
        try {
            parkingLotService.park(firstVehicle, attendant);
            Assert.assertTrue(parkingLotService.parkStatus(firstVehicle));
        } catch (ParkingLotException ignored) {
        }
    }

    @Test
    public void givenCarObject_WhenParking_IfNotAvailableThrowsException() {
        try {
            parkingLotService.park(firstVehicle, attendant);
            parkingLotService.park(secondVehicle, attendant);
            parkingLotService.park(new Vehicle("car3", BMW, WHITE, SMALL, NORMAL), attendant);
            parkingLotService.park(new Vehicle("car4", BMW, WHITE, SMALL, NORMAL), attendant);
            parkingLotService.park(new Vehicle("car5", BMW, WHITE, SMALL, NORMAL), attendant);
        } catch (ParkingLotException e) {
            Assert.assertEquals(LOT_FULL, e.type);
        }
    }

    @Test
    public void givenParkedCarObject_WhenUnParked_IfSuccessReturnTrue() {
        try {
            parkingLotService.park(firstVehicle, attendant);
            parkingLotService.unPark(firstVehicle);
            Assert.assertFalse(parkingLotService.parkStatus(firstVehicle));
        } catch (ParkingLotException ignored) {
        }
    }

    @Test
    public void givenSameVehicleTwice_WhenParked_ShouldThrowException() {
        try {
            parkingLotService.park(firstVehicle, attendant);
            parkingLotService.park(firstVehicle, attendant);
        } catch (ParkingLotException e) {
            Assert.assertEquals(CAR_ALREADY_PARKED, e.type);
        }
    }

    @Test
    public void givenParkedCar_WhenAttemptedToUnParkFor2Times_ShouldThrowException() {
        try {
            parkingLotService.park(firstVehicle, attendant);
            parkingLotService.unPark(firstVehicle);
            parkingLotService.unPark(firstVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(NO_SUCH_VEHICLE, e.type);
        }
    }

    @Test
    public void givenNotParkedCar_WhenAttemptToUnPark_ShouldThrowException() {
        try {
            parkingLotService.unPark(firstVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(NO_SUCH_VEHICLE, e.type);
        }
    }

    @Test
    public void givenNotParkedCar_WhenSearchedForLocation_ShouldThrowException() {
        try {
            parkingLotService.getVehicleLocation(firstVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(NO_SUCH_VEHICLE, e.type);
        }
    }

    @Test
    public void givenVehicles_WhenParkingLotIsFull_ShouldInformOwner() {
        Owner owner = new Owner();
        parkingLotService.addObserver(owner);
        try {
            parkingLotService.park(firstVehicle, attendant);
            parkingLotService.park(secondVehicle, attendant);
            parkingLotService.park(new Vehicle("car3", BMW, WHITE, SMALL, NORMAL), attendant);
            parkingLotService.park(new Vehicle("car4", BMW, WHITE, SMALL, NORMAL), attendant);
            Assert.assertTrue(owner.capacityFullStatus());
        } catch (ParkingLotException ignored) {
        }
    }

    @Test
    public void givenWhenParkingLotIsFull_ShouldInformAirPortSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLotService.addObserver(airportSecurity);
        try {
            parkingLotService.park(firstVehicle, attendant);
            parkingLotService.park(secondVehicle, attendant);
            parkingLotService.park(new Vehicle("car4", BMW, WHITE, SMALL, NORMAL), attendant);
            parkingLotService.park(new Vehicle("car3", BMW, WHITE, SMALL, NORMAL), attendant);
            Assert.assertTrue(airportSecurity.capacityFullStatus());
        } catch (ParkingLotException ignored) {
        }
    }

    @Test
    public void givenWhenParkingLotSpaceIsAvailableAfterFull_ShouldReturnTrue() {
        Owner owner = new Owner();
        parkingLotService.addObserver(owner);
        try {
            parkingLotService.park(firstVehicle, attendant);
            parkingLotService.park(secondVehicle, attendant);
            parkingLotService.park(new Vehicle("car3", BMW, WHITE, SMALL, NORMAL), attendant);
            parkingLotService.park(new Vehicle("car4", BMW, WHITE, SMALL, NORMAL), attendant);
            Assert.assertTrue(owner.capacityFullStatus());
            parkingLotService.unPark(firstVehicle);
            Assert.assertFalse(owner.capacityFullStatus());
        } catch (ParkingLotException ignored) {
        }
    }

    @Test
    public void givenVehicleObject_WhenSearchedForLocation_ShouldGiveLocation() {
        try {
            parkingLotService.park(firstVehicle, attendant);
            parkingLotService.park(secondVehicle, attendant);
            Assert.assertEquals(1, parkingLotService.getSlotToPark(NORMAL));
        } catch (ParkingLotException ignored) {
        }
    }

    @Test
    public void givenVehicleObject_ByDriverToParkingLot_ShouldReturnParkPositionOfVehicle() {
        try {
            parkingLotService.park(firstVehicle, attendant);
            parkingLotService.park(secondVehicle, attendant);
            parkingLotService.park(new Vehicle("car3", BMW, WHITE, SMALL, NORMAL), attendant);
            String location = parkingLotService.getVehicleLocation(new Vehicle("car3", BMW, WHITE, SMALL, NORMAL));
            Assert.assertEquals("A 1", location);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkedVehicle_WhenTriedToGetParkTime_ShouldReturnParkTime() {
        try {
            parkingLotService.park(firstVehicle, attendant);
            int currentTime = LocalDateTime.now().getSecond();
            int parkTime = parkingLotService.getParkTime(firstVehicle);
            Assert.assertEquals(currentTime, parkTime);
        } catch (ParkingLotException ignored) {
        }
    }

    @Test
    public void givenVehicle_WhenTriedToGetTimeIfNotPresent_ThrowsException() {
        try {
            parkingLotService.getParkTime(secondVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(NO_SUCH_VEHICLE, e.type);
        }
    }

    @Test
    public void givenMultipleParkingLots_WhenVehiclesParkedEvenly_ShouldReturnTrue() {
        try {
            parkingLotService.park(firstVehicle, attendant);
            parkingLotService.park(secondVehicle, attendant);
            int emptyLotsInFirst = parkingLot1.getEmptySlots().size();
            int emptyLotsInSecond = parkingLot1.getEmptySlots().size();
            //noinspection SimplifiableJUnitAssertion
            Assert.assertTrue(emptyLotsInFirst == emptyLotsInSecond);
        } catch (ParkingLotException ignored) {
        }
    }

    @Test
    public void givenVehicleToParkByHandicapPerson_ShouldPark_AtNearestPosition() {
        try {
            parkingLotService.park(firstVehicle, attendant);
            parkingLotService.park(secondVehicle, attendant);
            parkingLotService.park(new Vehicle("car3", BMW, WHITE, SMALL, NORMAL), attendant);
            parkingLotService.unPark(firstVehicle);
            parkingLotService.park(new Vehicle("car4", BMW, WHITE, SMALL, HANDICAP), attendant, HANDICAP);
            Assert.assertEquals("A 0",
                    parkingLotService.getVehicleLocation(new Vehicle("car4", BMW, WHITE, SMALL, HANDICAP)));
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenLargeVehicleToPark_ShouldPark_AtPositionWithMostFreeSpace() {
        try {
            parkingLotService.park(firstVehicle, attendant);
            parkingLotService.park(secondVehicle, attendant, LARGE);
            Assert.assertEquals("B 0", parkingLotService.getVehicleLocation(secondVehicle));
        } catch (ParkingLotException ignored) {
        }
    }

    @Test
    public void givenWhiteVehicleParked_WhenPoliceDeptAsksForLocation_ShouldReturnLocation() {
        try {
            parkingLotService.park(firstVehicle, attendant);
            parkingLotService.park(secondVehicle, attendant);
            List<ParkSlot> investigationData = policeDepartmentService.investigation(BOMB_THREAT_INVESTIGATION);
            Assert.assertEquals("B 0", parkingLotService.getVehicleLocation(investigationData.get(0).getVehicle()));
        } catch (ParkingLotException ignored) {
        }
    }

    @Test
    public void givenParkedVehicles_WhenPoliceDeptAskToInvestigate_ShouldReturnBlueToyotaVehicles() {
        try {
            parkingLotService.park(firstVehicle, attendant);
            List<ParkSlot> investigationData = policeDepartmentService.investigation(INVESTIGATE_ROBBERY);
            Assert.assertEquals("A 0 MH02E4957 Ramesh",
                    parkingLotService.getVehicleLocation(investigationData.get(0).getVehicle()) + " " +
                    investigationData.get(0).getVehicle().getPlateNumber() + " " +
                    investigationData.get(0).getAttendant().getName());
        } catch (ParkingLotException ignored) {
        }
    }

    @Test
    public void givenParkedVehicles_WhenPoliceAsksToIncreaseSecurity_ShouldReturnAllBMWLocations() {
        try {
            parkingLotService.park(firstVehicle, attendant);
            parkingLotService.park(secondVehicle, attendant);
            List<ParkSlot> investigationData = policeDepartmentService.investigation(INCREASE_SECURITY);
            Assert.assertEquals("B 0", parkingLotService.getVehicleLocation(investigationData.get(0).getVehicle()));
        } catch (ParkingLotException ignored) {
        }
    }

    @Test
    public void givenParkedVehicles_WhenPoliceAsksToInvestigateLast30MinCars_ShouldReturnParkedInLast30min() {
        try {
            parkingLotService.park(firstVehicle, attendant);
            Thread.sleep(4000);
            parkingLotService.park(secondVehicle, attendant);
            List<ParkSlot> investigationData = policeDepartmentService.investigation(BOMB_THREAT_BASED_ON_TIME);
            System.out.println(investigationData);
            Assert.assertEquals("B 0", parkingLotService.getVehicleLocation(investigationData.get(0).getVehicle()));
        } catch (ParkingLotException | InterruptedException ignored) {
        }
    }

    @Test
    public void givenParkedVehicles_WhenPoliceAsksForVehicleInfoInBAndD_ShouldReturnParkedLocations() {
        try {
            parkingLotService.park(firstVehicle, attendant, NORMAL);
            parkingLotService.park(thirdVehicle, attendant, HANDICAP);
            List<ParkSlot> investigationData = policeDepartmentService.handicapPermitFraud();
            Assert.assertEquals("B 0", parkingLotService.getVehicleLocation(investigationData.get(0).getVehicle()));
        } catch (ParkingLotException ignored) {
        }
    }

    @Test
    public void givenParkedVehicle_WhenPoliceAsksForAllPlateNumbers_ShouldReturnListOfSo() {
        try {
            parkingLotService.park(firstVehicle, attendant);
            parkingLotService.park(secondVehicle, attendant);
            parkingLotService.park(thirdVehicle, attendant, HANDICAP);
            List<String> investigationData = policeDepartmentService.getAllParkedVehicleNumberPlates();
            Assert.assertEquals("MH02E5689", investigationData.get(2));
        } catch (ParkingLotException ignored) {
        }
    }
}
