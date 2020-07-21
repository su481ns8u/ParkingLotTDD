package com.parkingLot.observers;

public enum Observers {
    OWNER(false), SECURITY(false);

    private boolean status;

    Observers(Boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean isFull) {
        this.status = isFull;
    }
}
