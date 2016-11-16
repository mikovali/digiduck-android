package com.sensorfields.digiduck.android.infrastructure.dagger;

import android.app.Application;
import android.content.ContentResolver;

import com.sensorfields.android.ActivityService;
import com.sensorfields.android.mvp.Presenter;
import com.sensorfields.android.task.TaskManager;
import com.sensorfields.digiduck.android.infrastructure.android.SafFileRepository;
import com.sensorfields.digiduck.android.infrastructure.android.StorageDocumentRepository;
import com.sensorfields.digiduck.android.model.DocumentRepository;
import com.sensorfields.digiduck.android.model.FileRepository;
import com.sensorfields.digiduck.android.presenter.RecentScreenPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

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
    static TaskManager observableRegistry() {
        return new TaskManager();
    }

    @Singleton
    @Provides
    static DocumentRepository documentRepository() {
        return new StorageDocumentRepository();
    }

    @Singleton
    @Provides
    static FileRepository fileRepository(ActivityService activityService,
                                         ContentResolver contentResolver) {
        return new SafFileRepository(activityService, contentResolver);
    }

    @Singleton
    @Provides @IntoMap
    @ClassKey(RecentScreenPresenter.Factory.class)
    static Presenter.Factory recentScreenPresenterFactory(
            DocumentRepository documentRepository) {
        return new RecentScreenPresenter.Factory(documentRepository);
    }
}
