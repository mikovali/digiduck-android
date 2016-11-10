package com.sensorfields.digiduck.android;

import android.content.Context;

import com.sensorfields.android.mvp.Presenter;
import com.sensorfields.digiduck.android.infrastructure.dagger.ApplicationComponent;
import com.sensorfields.digiduck.android.infrastructure.dagger.ApplicationModule;
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
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static ApplicationComponent getApplicationComponent(Context context) {
        return ((Application) context.getApplicationContext()).applicationComponent;
    }

    /**
     * Helper for getting {@link Presenter.Factory presenter factories} from DI component.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Presenter.Factory> T getPresenterFactory(Context context,
                                                                      Class<T> type) {
        return (T) getApplicationComponent(context).presenterFactories().get(type);
    }
}
