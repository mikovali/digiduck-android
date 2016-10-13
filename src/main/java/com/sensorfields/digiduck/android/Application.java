package com.sensorfields.digiduck.android;

import android.content.Context;

import com.sensorfields.digiduck.android.infrastructure.dagger.ApplicationComponent;
import com.sensorfields.digiduck.android.infrastructure.dagger.DaggerApplicationComponent;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupTimber();
        setupDagger();
    }

    // Timber

    protected void setupTimber() {
    }

    // Dagger

    protected ApplicationComponent applicationComponent;

    protected void setupDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
                .build();
    }

    public static ApplicationComponent getApplicationComponent(Context context) {
        return ((Application) context.getApplicationContext()).applicationComponent;
    }
}
