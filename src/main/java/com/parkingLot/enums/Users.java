package com.parkingLot.enums;

public enum Users {
    OWNER(false), SECURITY(false);

    public boolean status;

    Users(Boolean status) {
        this.status = status;
    }
}
