package com.sensorfields.digiduck.android.model;

public class File {

    public final String name;

    public File(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "File{" +
                "name='" + name + '\'' +
                '}';
    }
}
