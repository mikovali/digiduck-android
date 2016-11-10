package com.sensorfields.android.mvp;

import android.os.Parcel;

public interface Presenter {

    void onSaveState(Parcel state);

    void onRestoreState(Parcel state);

    interface Factory {}
}
