package com.parkinglot.enums;

import com.parkinglot.models.ParkSlot;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Predicate;

import static com.parkinglot.enums.CarMakes.BMW;
import static com.parkinglot.enums.CarMakes.TOYOTA;
import static com.parkinglot.enums.VehicleColor.BLUE;
import static com.parkinglot.enums.VehicleColor.WHITE;

public enum InvestigationPredicates {
    BOMB_THREAT_INVESTIGATION(parkSlot -> parkSlot.getVehicle().getColor().equals(WHITE)),
    INVESTIGATE_ROBBERY(parkSlot -> parkSlot.getVehicle().getColor().equals(BLUE)
            && parkSlot.getVehicle().getMake().equals(TOYOTA)),
    INCREASE_SECURITY(parkSlot -> parkSlot.getVehicle().getMake().equals(BMW)),
    BOMB_THREAT_BASED_ON_TIME(parkSlot -> Duration.between(parkSlot.getParkTime(),
            LocalDateTime.now()).getSeconds() <= 3);

    public final Predicate<? super ParkSlot> comparisionType;

    InvestigationPredicates(Predicate<? super ParkSlot> comparisionType) {
        this.comparisionType = comparisionType;
    }
}
