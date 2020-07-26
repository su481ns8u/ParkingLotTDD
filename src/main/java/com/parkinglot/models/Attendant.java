package com.parkinglot.models;

import java.util.Objects;

public class Attendant {
    private final String name;

    public Attendant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attendant attendant = (Attendant) o;
        return Objects.equals(name, attendant.name);
    }
}
