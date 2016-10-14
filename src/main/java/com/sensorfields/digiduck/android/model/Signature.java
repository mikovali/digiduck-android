package com.sensorfields.digiduck.android.model;

public class Signature {

    public final String name;

    public Signature(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Signature{" +
                "name='" + name + '\'' +
                '}';
    }
}
