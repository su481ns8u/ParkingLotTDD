package com.parkinglot;

import com.parkinglot.exceptions.ParkingLotException;
import com.parkinglot.models.ParkingLot;
import com.parkinglot.models.Vehicle;
import com.parkinglot.observers.AirportSecurity;
import com.parkinglot.observers.Owner;
import com.parkinglot.observers.PoliceDept;
import com.parkinglot.services.ParkingLotService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static com.parkinglot.enums.DriverType.HANDICAP;
import static com.parkinglot.enums.DriverType.NORMAL;
import static com.parkinglot.enums.VehicleColor.BLUE;
import static com.parkinglot.enums.VehicleColor.WHITE;
import static com.parkinglot.enums.VehicleType.LARGE;
import static com.parkinglot.exceptions.ParkingLotException.ExceptionType.*;

public class ParkingLotTest {
    ParkingLotService parkingLotService;
    ParkingLot parkingLot1;
    ParkingLot parkingLot2;
    Vehicle firstVehicle;
    Vehicle secondVehicle;
    PoliceDept policeDept;

    @Before
    public void setUp() {
        parkingLot1 = new ParkingLot(2);
        parkingLot2 = new ParkingLot(2);
        parkingLotService = new ParkingLotService();
        parkingLotService.addLot(parkingLot1);
        parkingLotService.addLot(parkingLot2);
        policeDept = new PoliceDept(parkingLotService);
        firstVehicle = new Vehicle("car1", BLUE);
        secondVehicle = new Vehicle("car2", WHITE);
    }

    @Test
    public void givenACarObject_WhenParking_IfAvailableReturnsTrue() {
        try {
            parkingLotService.park(firstVehicle);
            Assert.assertTrue(parkingLotService.parkStatus(firstVehicle));
        } catch (ParkingLotException ignored) {
        }
    }

    @Test
    public void givenCarObject_WhenParking_IfNotAvailableThrowsException() {
        try {
            parkingLotService.park(firstVehicle);
            parkingLotService.park(secondVehicle);
            parkingLotService.park(new Vehicle("car3", WHITE));
            parkingLotService.park(new Vehicle("car4", WHITE));
            parkingLotService.park(new Vehicle("car5", WHITE));
        } catch (ParkingLotException e) {
            Assert.assertEquals(LOT_FULL, e.type);
        }
    }

    @Test
    public void givenParkedCarObject_WhenUnParked_IfSuccessReturnTrue() {
        try {
            parkingLotService.park(firstVehicle);
            parkingLotService.unPark(firstVehicle);
            Assert.assertFalse(parkingLotService.parkStatus(firstVehicle));
        } catch (ParkingLotException ignored) {
        }
    }

    @Test
    public void givenSameVehicleTwice_WhenParked_ShouldThrowException() {
        try {
            parkingLotService.park(firstVehicle);
            parkingLotService.park(firstVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(CAR_ALREADY_PARKED, e.type);
        }
    }

    @Test
    public void givenParkedCar_WhenAttemptedToUnParkFor2Times_ShouldThrowException() {
        try {
            parkingLotService.park(firstVehicle);
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
            parkingLotService.park(firstVehicle);
            parkingLotService.park(secondVehicle);
            parkingLotService.park(new Vehicle("car3", WHITE));
            parkingLotService.park(new Vehicle("car4", WHITE));
            Assert.assertTrue(owner.capacityFullStatus());
        } catch (ParkingLotException ignored) {
        }
    }

    @Test
    public void givenWhenParkingLotIsFull_ShouldInformAirPortSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLotService.addObserver(airportSecurity);
        try {
            parkingLotService.park(firstVehicle);
            parkingLotService.park(secondVehicle);
            parkingLotService.park(new Vehicle("car4", WHITE));
            parkingLotService.park(new Vehicle("car3", WHITE));
            Assert.assertTrue(airportSecurity.capacityFullStatus());
        } catch (ParkingLotException ignored) {
        }
    }

    @Test
    public void givenWhenParkingLotSpaceIsAvailableAfterFull_ShouldReturnTrue() {
        Owner owner = new Owner();
        parkingLotService.addObserver(owner);
        try {
            parkingLotService.park(firstVehicle);
            parkingLotService.park(secondVehicle);
            parkingLotService.park(new Vehicle("car3", WHITE));
            parkingLotService.park(new Vehicle("car4", WHITE));
            Assert.assertTrue(owner.capacityFullStatus());
            parkingLotService.unPark(firstVehicle);
            Assert.assertFalse(owner.capacityFullStatus());
        } catch (ParkingLotException ignored) {
        }
    }

    @Test
    public void givenVehicleObject_WhenSearchedForLocation_ShouldGiveLocation() {
        try {
            parkingLotService.park(firstVehicle);
            parkingLotService.park(secondVehicle);
            Assert.assertEquals(1, parkingLotService.getSlotToPark(NORMAL));
        } catch (ParkingLotException ignored) {
        }
    }

    @Test
    public void givenVehicleObject_ByDriverToParkingLot_ShouldReturnParkPositionOfVehicle() {
        try {
            parkingLotService.park(firstVehicle);
            parkingLotService.park(secondVehicle);
            parkingLotService.park(new Vehicle("car3", WHITE));
            String location = parkingLotService.getVehicleLocation(new Vehicle("car3", WHITE));
            Assert.assertEquals("A 1", location);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkedVehicle_WhenTriedToGetParkTime_ShouldReturnParkTime() {
        try {
            parkingLotService.park(firstVehicle);
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
            parkingLotService.park(firstVehicle);
            parkingLotService.park(secondVehicle);
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
            parkingLotService.park(firstVehicle);
            parkingLotService.park(secondVehicle);
            parkingLotService.park(new Vehicle("car3", WHITE));
            parkingLotService.unPark(firstVehicle);
            parkingLotService.park(new Vehicle("car4", WHITE), HANDICAP);
            Assert.assertEquals("A 0", parkingLotService.getVehicleLocation(new Vehicle("car4", WHITE)));
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenLargeVehicleToPark_ShouldPark_AtPositionWithMostFreeSpace() {
        try {
            parkingLotService.park(firstVehicle);
            parkingLotService.park(secondVehicle, LARGE);
            Assert.assertEquals("B 0", parkingLotService.getVehicleLocation(secondVehicle));
        } catch (ParkingLotException ignored) {
        }
    }

    @Test
    public void givenWhiteVehicleParked_WhenPoliceDeptAsksForLocation_ShouldReturnLocation() {
        try {
            parkingLotService.park(firstVehicle);
            parkingLotService.park(secondVehicle);
            Assert.assertEquals("B 0", policeDept.getWhiteVehicleLocations().get(0));
        } catch (ParkingLotException ignored) {
        }
    }
}
