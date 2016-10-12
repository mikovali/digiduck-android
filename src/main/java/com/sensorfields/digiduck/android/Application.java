package com.sensorfields.digiduck.android;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupTimber();
    }

    // Timber

    protected void setupTimber() {
    }
}
