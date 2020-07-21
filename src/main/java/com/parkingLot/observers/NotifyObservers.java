package com.parkingLot.observers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotifyObservers {
    List<Observers> observers;

    /**
     * Constructor to declare observers array
     */
    public NotifyObservers() {
        observers = new ArrayList<>();
        Collections.addAll(observers, Observers.values());
    }

    /**
     * Notify observers if lot full or not
     *
     * @param isFull is current status of lot
     */
    public void informLotFullStatus(boolean isFull) {
        observers.forEach(observers -> observers.status = isFull);
    }
}
