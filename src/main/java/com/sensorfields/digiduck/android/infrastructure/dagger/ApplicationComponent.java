package com.sensorfields.digiduck.android.infrastructure.dagger;

import com.sensorfields.digiduck.android.Activity;
import com.sensorfields.digiduck.android.view.RecentScreenView;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(Activity activity);

    void inject(RecentScreenView recentScreenView);
}
