package com.parkingLot;

import com.parkingLot.exceptions.ParkingLotException;
import com.parkingLot.models.ParkingLot;
import com.parkingLot.observers.AirportSecurity;
import com.parkingLot.observers.Owner;
import com.parkingLot.services.ParkingLotService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static com.parkingLot.exceptions.ParkingLotException.ExceptionType.*;

public class ParkingLotTest {
    ParkingLotService parkingLotService;
    ParkingLot parkingLot1;
    ParkingLot parkingLot2;
    Object firstVehicle;
    Object secondVehicle;

    @Before
    public void setUp() {
        parkingLot1 = new ParkingLot(2);
        parkingLot2 = new ParkingLot(2);
        parkingLotService = new ParkingLotService();
        parkingLotService.addLot(parkingLot1);
        parkingLotService.addLot(parkingLot2);
        firstVehicle = "car1";
        secondVehicle = "car2";
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
            parkingLotService.park("car3");
            parkingLotService.park("car4");
            parkingLotService.park("car5");
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
            parkingLotService.park("car3");
            parkingLotService.park("car4");
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
            parkingLotService.park("car3");
            parkingLotService.park("car4");
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
            parkingLotService.park("car3");
            parkingLotService.park("car4");
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
            Assert.assertEquals(1, parkingLotService.getSlotToPark());
        } catch (ParkingLotException ignored) {
        }
    }

    @Test
    public void givenVehicleObject_ByDriverToParkingLot_ShouldReturnParkPositionOfVehicle() {
        try {
            parkingLotService.park(firstVehicle);
            String location = parkingLotService.getVehicleLocation(firstVehicle);
            Assert.assertEquals("A 0", location);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkedVehicle_WhenTriedToGetParkTime_ShouldReturnParkTime() {
        try {
            parkingLotService.park(firstVehicle);
            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime parkTime = parkingLotService.getParkTime(firstVehicle);
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

    //
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
}
