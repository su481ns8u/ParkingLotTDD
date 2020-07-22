package com.parkingLot;

import com.parkingLot.exceptions.ParkingLotException;
import com.parkingLot.observers.AirportSecurity;
import com.parkingLot.observers.Owner;
import com.parkingLot.services.ParkingLot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.parkingLot.exceptions.ParkingLotException.ExceptionType.*;

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
