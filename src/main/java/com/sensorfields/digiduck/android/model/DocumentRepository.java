package com.sensorfields.digiduck.android.model;

import java.util.List;

import io.reactivex.Single;

public interface DocumentRepository {

    Single<List<Document>> find();
}
