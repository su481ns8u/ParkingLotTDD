package com.parkingLot;

import com.parkingLot.exceptions.ParkingLotException;
import com.parkingLot.models.Vehicle;
import com.parkingLot.observers.AirportSecurity;
import com.parkingLot.observers.Attendant;
import com.parkingLot.observers.Owner;
import com.parkingLot.observers.VehicleDriver;
import com.parkingLot.services.ParkingLot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Driver;
import java.time.LocalDateTime;

import static com.parkingLot.exceptions.ParkingLotException.ExceptionType.*;

public class ParkingLotTest {
    ParkingLot parkingLot;
    Vehicle firstVehicle;
    Vehicle secondVehicle;

    @Before
    public void setUp() {
        parkingLot = new ParkingLot(2);
        firstVehicle = new Vehicle(LocalDateTime.now(),"car1");
        secondVehicle = new Vehicle(LocalDateTime.now(), "car2");
    }

    @Test
    public void givenACarObject_WhenParking_IfAvailableReturnsTrue() {
        try {
            parkingLot.park(0, firstVehicle);
        } catch (ParkingLotException e) {}
        Assert.assertTrue(parkingLot.parkStatue(firstVehicle));
    }

    @Test
    public void givenCarObject_WhenParking_IfNotAvailableThrowsException() {
        try {
            parkingLot.park(0, firstVehicle);
            parkingLot.park(1, secondVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(LOT_FULL, e.type);
        }
    }

    @Test
    public void givenParkedCarObject_WhenUnParked_IfSuccessReturnTrue() {
        try {
            parkingLot.park(0, firstVehicle);
            parkingLot.unPark(firstVehicle);
        } catch (ParkingLotException e) {}
        Assert.assertFalse(parkingLot.parkStatue(firstVehicle));
    }

    @Test
    public void givenSameVehicleTwice_WhenParked_ShouldThrowException() {
        try {
            parkingLot.park(0, firstVehicle);
            parkingLot.park(1, firstVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(CAR_ALREADY_PARKED, e.type);
        }
    }

    @Test
    public void givenParkedCar_WhenAttemptedToUnParkFor2Times_ShouldThrowException() {
        try {
            parkingLot.park(1, firstVehicle);
            parkingLot.unPark(secondVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(NO_SUCH_VEHICLE, e.type);
        }
    }

    @Test
    public void givenNotParkedCar_WhenAttemptToUnPark_ShouldThrowException() {
        try {
            parkingLot.park(0, secondVehicle);
            parkingLot.unPark(firstVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(NO_SUCH_VEHICLE, e.type);
        }
    }

    @Test
    public void givenNotParkedCar_WhenSearchedForLocation_ShouldThrowException() {
        try {
            parkingLot.getCarLocation(firstVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(NO_SUCH_VEHICLE, e.type);
        }
    }

    @Test
    public void givenNotParkedCar_WhenAttemptToUnParkInEmptyLot_ShouldThrowException() {
        try {
            parkingLot.unPark(firstVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(PARK_SPACE_EMPTY, e.type);
        }
    }

    @Test
    public void givenWhenParkingLotIsFull_ShouldInformOwner() {
        Owner owner = new Owner();
        parkingLot.registerObserver(owner);
        try {
            parkingLot.park(0, firstVehicle);
            parkingLot.park(1, secondVehicle);
        } catch (ParkingLotException e) {}
        boolean capacityFull = owner.capacityFullStatus();
        Assert.assertTrue(capacityFull);
    }

    @Test
    public void givenWhenParkingLotIsFull_ShouldInformAirPortSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLot.registerObserver(airportSecurity);
        try {
            parkingLot.park(0, firstVehicle);
            parkingLot.park(1, secondVehicle);
        } catch (ParkingLotException e) {}
        boolean capacityFull = airportSecurity.capacityFullStatus();
        Assert.assertTrue(capacityFull);
    }

    @Test
    public void givenWhenParkingLotSpaceIsAvailableAfterFull_ShouldReturnTrue() {
        Owner owner = new Owner();
        parkingLot.registerObserver(owner);
        try {
            parkingLot.park(0, firstVehicle);
            parkingLot.park(1, secondVehicle);
            parkingLot.unPark(firstVehicle);
            boolean capacityFull1 = owner.capacityFullStatus();
            Assert.assertFalse(capacityFull1);
        } catch (ParkingLotException e) {}
    }

    @Test
    public void givenCarToParkToAttendant_SelectLocationByOwner_ShouldPark() {
        Owner owner = new Owner();
        parkingLot.registerObserver(owner);
        Attendant attendant = new Attendant();
        try {
            attendant.park(owner.selectPosition(parkingLot), firstVehicle, parkingLot);
            boolean isParked = parkingLot.parkStatue(firstVehicle);
            Assert.assertTrue(isParked);
        } catch (ParkingLotException e) {}
    }

    @Test
    public void givenVehicleObject_ByDriverToParkingLot_ShouldReturnParkPosition() {
        VehicleDriver vehicleDriver = new VehicleDriver();
        try {
            parkingLot.park(1, firstVehicle);
            int location = vehicleDriver.findVehicle(firstVehicle, parkingLot);
            Assert.assertEquals(1, location);
        } catch (ParkingLotException e) {}
    }

    @Test
    public void givenParkedVehicle_WhenUnParked_ShouldReturnFare() {
        try {
            parkingLot.park(1, firstVehicle);
            Thread.sleep(2000);
            long fare = parkingLot.unPark(firstVehicle);
            Assert.assertEquals(20, fare);
        } catch (ParkingLotException | InterruptedException e) {}
    }

    @Test
    public void demoTest() {
        Owner owner = new Owner();
        VehicleDriver driver = new VehicleDriver();
        parkingLot.registerObserver(owner);
        ParkingLot parkingLot1 = new ParkingLot(2);
        parkingLot1.registerObserver(owner);
        Attendant attendant = new Attendant();
        try {
            attendant.park(owner.selectPosition(owner.selectLot(parkingLot1, parkingLot)),
                    firstVehicle,
                    owner.selectLot(parkingLot1, parkingLot));
            attendant.park(owner.selectPosition(owner.selectLot(parkingLot1, parkingLot)),
                    secondVehicle,
                    owner.selectLot(parkingLot1, parkingLot));
            int location2 = driver.findVehicle(secondVehicle, parkingLot);

            Assert.assertEquals(0, location2);
        } catch (ParkingLotException e) { }
    }
}
