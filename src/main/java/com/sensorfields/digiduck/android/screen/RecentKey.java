package com.sensorfields.digiduck.android.screen;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.sensorfields.digiduck.android.infrastructure.flow.ViewKey;
import com.sensorfields.digiduck.android.view.RecentScreenView;

import flow.ClassKey;

public final class RecentKey extends ClassKey implements ViewKey, Parcelable {

    @Override
    public View createView(Context context) {
        return new RecentScreenView(context);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static final Creator<RecentKey> CREATOR = new Creator<RecentKey>() {
        @Override
        public RecentKey createFromParcel(Parcel source) {
            return new RecentKey();
        }
        @Override
        public RecentKey[] newArray(int size) {
            return new RecentKey[size];
        }
    };
}
