package com.parkingLot.observers;

public enum Observers {
    OWNER(false), SECURITY(false);

    public boolean status;

    Observers(Boolean status) {
        this.status = status;
    }
}
