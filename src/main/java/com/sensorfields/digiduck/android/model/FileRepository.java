package com.sensorfields.digiduck.android.model;

import io.reactivex.Single;

public interface FileRepository {

    /**
     * TODO Think about this, Maybe could make more sense.
     */
    Single<File> get(int requestCode);
}
