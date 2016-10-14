package com.sensorfields.digiduck.android.infrastructure.dagger;

import android.app.Application;
import android.content.ContentResolver;

import com.sensorfields.android.ActivityService;
import com.sensorfields.digiduck.android.infrastructure.android.SafFileRepository;
import com.sensorfields.digiduck.android.model.FileRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    ContentResolver contentResolver() {
        return application.getContentResolver();
    }

    @Singleton
    @Provides
    static ActivityService activityService() {
        return new ActivityService();
    }

    @Singleton
    @Provides
    static FileRepository fileRepository(ActivityService activityService,
                                         ContentResolver contentResolver) {
        return new SafFileRepository(activityService, contentResolver);
    }
}
