package com.sensorfields.digiduck.android.model;

import rx.Observable;

public interface FileRepository {

    Observable<File> get(int requestCode);
}
