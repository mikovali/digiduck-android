package com.sensorfields.digiduck.android.screen;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.sensorfields.digiduck.android.infrastructure.flow.ViewKey;
import com.sensorfields.digiduck.android.view.DocumentScreenView;

import flow.ClassKey;

public class DocumentKey extends ClassKey implements ViewKey, Parcelable {

    @Override
    public View createView(Context context) {
        return new DocumentScreenView(context);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static final Creator<DocumentKey> CREATOR = new Creator<DocumentKey>() {
        @Override
        public DocumentKey createFromParcel(Parcel source) {
            return new DocumentKey();
        }
        @Override
        public DocumentKey[] newArray(int size) {
            return new DocumentKey[size];
        }
    };
}
