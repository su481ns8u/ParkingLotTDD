package com.parkingLot.observers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LotObservers {
    List<Users> observers;

    public LotObservers() {
        observers = new ArrayList<>();
        Collections.addAll(observers, Users.values());
    }

    public void informLotFullStatus(boolean isFull) {
        observers.forEach(observers -> observers.status = isFull);
    }
}
