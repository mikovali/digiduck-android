package com.sensorfields.digiduck.android.infrastructure.dagger;

import com.sensorfields.android.mvp.Presenter;
import com.sensorfields.digiduck.android.Activity;
import com.sensorfields.digiduck.android.view.DocumentScreenView;
import com.sensorfields.digiduck.android.view.RecentScreenView;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(Activity activity);

    Map<Class<?>, Presenter.Factory> presenterFactories();

    void inject(RecentScreenView recentScreenView);
    void inject(DocumentScreenView documentScreenView);
}
