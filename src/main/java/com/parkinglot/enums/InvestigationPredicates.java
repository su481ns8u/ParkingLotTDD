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
    BOMB_THREAT_INVESTIGATION {
        @Override
        public Predicate<? super ParkSlot> getPredicate() {
            return parkSlot -> parkSlot.getVehicle()
                    .getColor()
                    .equals(WHITE);
        }
    },

    INVESTIGATE_ROBBERY {
        @Override
        public Predicate<? super ParkSlot> getPredicate() {
            return parkSlot -> parkSlot.getVehicle().getColor().equals(BLUE)
                    && parkSlot.getVehicle().getMake().equals(TOYOTA);
        }
    },

    INCREASE_SECURITY {
        @Override
        public Predicate<? super ParkSlot> getPredicate() {
            return parkSlot -> parkSlot.getVehicle().getMake().equals(BMW);
        }
    },

    BOMB_THREAT_BASED_ON_TIME {
        @Override
        public Predicate<? super ParkSlot> getPredicate() {
            return parkSlot -> Duration.between(parkSlot.getParkTime(), LocalDateTime.now()).getSeconds() <= 3;
        }
    };

    public abstract Predicate<? super ParkSlot> getPredicate();
}
