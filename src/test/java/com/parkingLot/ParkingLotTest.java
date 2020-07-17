package com.parkingLot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.parkingLot.ParkingLotException.ExceptionType.*;

public class ParkingLotTest {
    ParkingLot parkingLot;

    @Before
    public void setUp() {
         parkingLot = new ParkingLot();
    }

    @Test
    public void givenACarObject_WhenParking_IfAvailableReturnsTrue() throws ParkingLotException {
        Vehicle car = new Vehicle("RED", "MH22QW2991", "SUZUKI");
        Assert.assertTrue(parkingLot.park(car));
    }

    @Test
    public void givenCarObject_WhenParking_IfNotAvailableThrowsException() {
        try {
            Vehicle car1 = new Vehicle("WHITE", "MH51QE8520", "TOYOTA");
            Vehicle car2 = new Vehicle("RED", "MH22QW2991", "SUZUKI");
            parkingLot.park(car1);
            parkingLot.park(car2);
        } catch (ParkingLotException e) {
            Assert.assertEquals(SPACE_NOT_AVAILABLE, e.type);
        }
    }

    @Test
    public void givenParkedCarObject_WhenUnParked_IfSuccessReturnTrue() throws ParkingLotException {
        Vehicle car1 = new Vehicle("WHITE", "MH51QE8520", "TOYOTA");
        parkingLot.park(car1);
        Assert.assertTrue(parkingLot.unPark(car1));
    }

    @Test
    public void givenParkedCar_WhenAttemptedToUnParkFor2Times_ShouldThrowException() {
        try {
            Vehicle car1 = new Vehicle("WHITE", "MH51QE8520", "TOYOTA");
            Vehicle car2 = new Vehicle("WHITE", "MH51QE8520", "TOYOTA");
            parkingLot.park(car1);
            parkingLot.unPark(car2);
        } catch (ParkingLotException e) {
            Assert.assertEquals(NO_SUCH_VEHICLE, e.type);
        }
    }

    @Test
    public void givenNotParkedCar_WhenAttemptToUnPark_ShouldThrowException() {
        try {
            Vehicle car1 = new Vehicle("WHITE", "MH51QE8520", "TOYOTA");
            parkingLot.unPark(car1);
        } catch (ParkingLotException e) {
            Assert.assertEquals(PARK_SPACE_EMPTY, e.type);
        }
    }

    @Test
    public void putLotFullSign_WhenParkingLotFull_ReturnsTrue() throws ParkingLotException {
        Vehicle car1 = new Vehicle("WHITE", "MH51QE8520", "TOYOTA");
        parkingLot.park(car1);
        Assert.assertTrue(parkingLot.putFullSign());
    }

    @Test
    public void putLotFullSign_WhenParkingLotNotFull_ReturnsFalse() throws ParkingLotException {
        Vehicle car1 = new Vehicle("WHITE", "MH51QE8520", "TOYOTA");
        parkingLot.park(car1);
        parkingLot.unPark(car1);
        Assert.assertFalse(parkingLot.putFullSign());
    }

    @Test
    public void redirectSecurity_WhenParkingLotFull_ReturnsTrue() throws ParkingLotException {
        Vehicle car1 = new Vehicle("WHITE", "MH51QE8520", "TOYOTA");
        parkingLot.park(car1);
        Assert.assertTrue(parkingLot.redirectSecurity());
    }

    @Test
    public void redirectSecurity_WhenParkingLotNotFull_ReturnsFalse() throws ParkingLotException {
        Vehicle car1 = new Vehicle("WHITE", "MH51QE8520", "TOYOTA");
        parkingLot.park(car1);
        parkingLot.unPark(car1);
        Assert.assertFalse(parkingLot.redirectSecurity());
    }

    @Test
    public void takeInLotFullSign_WhenParkingLotNotFull_ReturnsTrue() throws ParkingLotException {
        Vehicle car1 = new Vehicle("WHITE", "MH51QE8520", "TOYOTA");
        parkingLot.park(car1);
        parkingLot.unPark(car1);
        Assert.assertTrue(parkingLot.takeInFullSign());
    }

    @Test
    public void takeInLotFullSign_WhenParkingLotFull_ReturnsFalse() throws ParkingLotException {
        Vehicle car1 = new Vehicle("WHITE", "MH51QE8520", "TOYOTA");
        parkingLot.park(car1);
        Assert.assertFalse(parkingLot.takeInFullSign());
    }
}
