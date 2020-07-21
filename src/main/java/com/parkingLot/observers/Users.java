package com.parkingLot.observers;

public enum Users {
    OWNER(false), SECURITY(false);

    public boolean status;

    Users(Boolean status) {
        this.status = status;
    }
}
