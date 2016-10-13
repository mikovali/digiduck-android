package com.sensorfields.digiduck.android.infrastructure.dagger;

import com.sensorfields.android.ActivityService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    @Singleton
    @Provides
    static ActivityService activityService() {
        return new ActivityService();
    }
}
