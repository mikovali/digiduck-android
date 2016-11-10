package com.sensorfields.digiduck.android.infrastructure.android;

import android.os.SystemClock;

import com.sensorfields.digiduck.android.model.Document;
import com.sensorfields.digiduck.android.model.DocumentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class StorageDocumentRepository implements DocumentRepository {

    private static final Document[] DOCUMENTS = new Document[] {
            new Document("Miko Väli contract.bdoc"),
            new Document("KÜ Lubja 7b.bdoc"),
            new Document("Sensor Fields OÜ hinnapakkumine.bdoc"),
            new Document("Leping Massruum OÜ.bdoc"),
            new Document("Maksekorraldus.bdoc"),
            new Document("Lets agree on this.bdoc"),
            new Document("Notar 2016 dokument.bdoc")
    };

    private final Single<List<Document>> findSingle = Single
            .fromCallable(new Callable<List<Document>>() {
                @Override
                public List<Document> call() throws Exception {
                    Timber.e("CALLABLE IS CREATING...");
                    SystemClock.sleep(10000);
                    Timber.e("CALLABLE FINISHED CREATING");
                    return Arrays.asList(DOCUMENTS);
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());

    @Override
    public Single<List<Document>> find() {
        return findSingle;
    }
}
