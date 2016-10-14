package com.sensorfields.digiduck.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class File implements Parcelable {

    public final String name;

    public File(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "File{" +
                "name='" + name + '\'' +
                '}';
    }

    // Parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    private File(Parcel in) {
        name = in.readString();
    }

    public static final Creator<File> CREATOR = new Creator<File>() {
        @Override
        public File createFromParcel(Parcel source) {
            return new File(source);
        }
        @Override
        public File[] newArray(int size) {
            return new File[size];
        }
    };
}
