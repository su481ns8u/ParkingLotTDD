package com.parkingLot;

public enum Users {
    OWNER(false), SECURITY(false);

    boolean status;

    Users(Boolean status) {
        this.status = status;
    }
}
