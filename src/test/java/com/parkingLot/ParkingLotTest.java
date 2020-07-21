package com.parkingLot;

import com.parkingLot.exceptions.ParkingLotException;
import com.parkingLot.services.ParkingLot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.parkingLot.exceptions.ParkingLotException.ExceptionType.*;
import static com.parkingLot.observers.Observers.OWNER;
import static com.parkingLot.observers.Observers.SECURITY;

public class ParkingLotTest {
    ParkingLot parkingLot;
    Object firstVehicle;
    Object secondVehicle;

    @Before
    public void setUp() {
        parkingLot = new ParkingLot(2);
        firstVehicle = "car1";
        secondVehicle = "car2";
    }

    @Test
    public void givenACarObject_WhenParking_IfAvailableReturnsTrue() throws ParkingLotException {
        parkingLot.park(0, firstVehicle);
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
    public void givenParkedCarObject_WhenUnParked_IfSuccessReturnTrue() throws ParkingLotException {
        parkingLot.park(0, firstVehicle);
        parkingLot.unPark(firstVehicle);
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
    public void givenNotParkedCar_WhenAttemptToUnParkInEmptyLot_ShouldThrowException() {
        try {
            parkingLot.unPark(firstVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(PARK_SPACE_EMPTY, e.type);
        }
    }

    @Test
    public void putLotFullSign_WhenParkingLotFull_ReturnsTrue() throws ParkingLotException {
        parkingLot.park(0, firstVehicle);
        parkingLot.park(1, secondVehicle);
        Assert.assertTrue(OWNER.getStatus());
    }

    @Test
    public void redirectSecurity_WhenParkingLotFull_ReturnsTrue() throws ParkingLotException {
        parkingLot.park(0, firstVehicle);
        parkingLot.park(1, secondVehicle);
        Assert.assertTrue(SECURITY.getStatus());
    }

    @Test
    public void putLotFullSign_WhenParkingLotHasSpace_ReturnsFalse() throws ParkingLotException {
        parkingLot.park(0, firstVehicle);
        parkingLot.unPark(firstVehicle);
        Assert.assertFalse(OWNER.getStatus());
    }

    @Test
    public void givenVehicleToPark_ifLotNotExists_ThrowsException() {
        try {
            parkingLot.park(3, firstVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(SPACE_OCCUPIED, e.type);
        }
    }

    @Test
    public void givenVehicleToPark_ifLotAlreadyTaken_ThrowsException() {
        try {
            parkingLot.park(0, firstVehicle);
            parkingLot.park(0, secondVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(SPACE_OCCUPIED, e.type);
        }
    }

    @Test
    public void givenParkedCar_WhenSearchedForLocation_ShouldReturnIntLocation() throws ParkingLotException {
        parkingLot.park(1, firstVehicle);
        int vehicleLocation = parkingLot.getCarLocation(firstVehicle);
        Assert.assertEquals(1, vehicleLocation);
    }

    @Test
    public void givenNotParkedCar_WhenSearchedForLocation_ShouldThrowException() {
        try {
            parkingLot.getCarLocation(firstVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(NO_SUCH_VEHICLE, e.type);
        }
    }
}
