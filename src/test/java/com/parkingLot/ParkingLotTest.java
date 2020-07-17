package com.parkingLot;

import org.junit.Assert;
import org.junit.Test;

import static com.parkingLot.ParkingLotException.ExceptionType.SPACE_NOT_AVAILABLE;

public class ParkingLotTest {
    ParkingLot parkingLot = new ParkingLot();

    @Test
    public void givenACarObject_WhenCheckedForParking_IfAvailableReturnsTrue() throws ParkingLotException {
        Vehicle car = new Vehicle("RED", "MH22QW2991", "SUZUKI");
        Assert.assertTrue(parkingLot.park(car));
    }

    @Test
    public void givenCarObject_WhenCheckedForParking_IfNotAvailableThrowsException() {
        try {
            Vehicle car1 = new Vehicle("WHITE", "MH51QE8520", "TOYOTA");
            Vehicle car2 = new Vehicle("RED", "MH22QW2991", "SUZUKI");
            parkingLot.park(car1);
            parkingLot.park(car2);
        } catch (ParkingLotException e) {
            Assert.assertEquals(SPACE_NOT_AVAILABLE, e.type);
        }
    }
}
