package com.parkingLot;

import com.parkingLot.exceptions.ParkingLotException;
import com.parkingLot.services.ParkingLot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.parkingLot.enums.Users.OWNER;
import static com.parkingLot.enums.Users.SECURITY;
import static com.parkingLot.exceptions.ParkingLotException.ExceptionType.*;

public class ParkingLotTest {
    ParkingLot parkingLot;

    @Before
    public void setUp() {
        parkingLot = new ParkingLot(2);
    }

    @Test
    public void givenACarObject_WhenParking_IfAvailableReturnsTrue() throws ParkingLotException {
        Object car = new Object();
        parkingLot.park(car);
        Assert.assertTrue(parkingLot.parkStatue(car));
    }

    @Test
    public void givenCarObject_WhenParking_IfNotAvailableThrowsException() {
        try {
            Object car1 = new Object();
            Object car2 = new Object();
            parkingLot.park(car1);
            parkingLot.park(car2);
        } catch (ParkingLotException e) {
            Assert.assertEquals(SPACE_NOT_AVAILABLE, e.type);
        }
    }

    @Test
    public void givenParkedCarObject_WhenUnParked_IfSuccessReturnTrue() throws ParkingLotException {
        Object car1 = new Object();
        parkingLot.park(car1);
        parkingLot.unPark(car1);
        Assert.assertFalse(parkingLot.parkStatue(car1));
    }

    @Test
    public void givenSameVehicleTwice_WhenParked_ShouldThrowException() {
        try {
            Object car = new Object();
            parkingLot.park(car);
            parkingLot.park(car);
        } catch (ParkingLotException e) {
            Assert.assertEquals(CAR_ALREADY_PARKED, e.type);
        }
    }

    @Test
    public void givenParkedCar_WhenAttemptedToUnParkFor2Times_ShouldThrowException() {
        try {
            Object car1 = new Object();
            Object car2 = new Object();
            parkingLot.park(car1);
            parkingLot.unPark(car2);
        } catch (ParkingLotException e) {
            Assert.assertEquals(NO_SUCH_VEHICLE, e.type);
        }
    }

    @Test
    public void givenNotParkedCar_WhenAttemptToUnPark_ShouldThrowException() {
        try {
            Object car1 = new Object();
            parkingLot.unPark(car1);
        } catch (ParkingLotException e) {
            Assert.assertEquals(PARK_SPACE_EMPTY, e.type);
        }
    }

    @Test
    public void putLotFullSign_WhenParkingLotFull_ReturnsTrue() throws ParkingLotException {
        Object car1 = new Object();
        parkingLot.park(car1);
        Object car2 = new Object();
        parkingLot.park(car2);
        Assert.assertTrue(OWNER.status);
    }

    @Test
    public void redirectSecurity_WhenParkingLotFull_ReturnsTrue() throws ParkingLotException {
        Object car1 = new Object();
        parkingLot.park(car1);
        Object car2 = new Object();
        parkingLot.park(car2);
        Assert.assertTrue(SECURITY.status);
    }

    @Test
    public void putLotFullSign_WhenParkingLotHasSpace_ReturnsFalse() throws ParkingLotException {
        Object car1 = new Object();
        parkingLot.park(car1);
        parkingLot.unPark(car1);
        Assert.assertFalse(OWNER.status);
    }
}
