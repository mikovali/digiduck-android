package com.sensorfields.digiduck.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Document implements Parcelable {

    public final String name;

    public Document(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    public Document(Parcel source) {
        name = source.readString();
    }

    public static final Creator<Document> CREATOR = new ClassLoaderCreator<Document>() {
        @Override
        public Document createFromParcel(Parcel source, ClassLoader loader) {
            return new Document(source);
        }
        @Override
        public Document createFromParcel(Parcel source) {
            return createFromParcel(source, null);
        }
        @Override
        public Document[] newArray(int size) {
            return new Document[size];
        }
    };
}
