package com.parkingLot;

public enum Users {
    OWNER(false);

    boolean status;

    Users(Boolean status) {
        this.status = status;
    }
}
