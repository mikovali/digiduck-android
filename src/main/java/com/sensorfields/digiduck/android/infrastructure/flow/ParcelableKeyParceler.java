package com.sensorfields.digiduck.android.infrastructure.flow;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import flow.KeyParceler;

public class ParcelableKeyParceler implements KeyParceler {

    @NonNull
    @Override
    public Parcelable toParcelable(@NonNull Object key) {
        return (Parcelable) key;
    }

    @NonNull
    @Override
    public Object toKey(@NonNull Parcelable parcelable) {
        return parcelable;
    }
}
