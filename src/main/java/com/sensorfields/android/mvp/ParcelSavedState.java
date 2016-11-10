package com.sensorfields.android.mvp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * TODO Write documentation (View and Presenter examples) and publish
 */
public final class ParcelSavedState implements Parcelable {

    public interface StateListener {
        void onSaveState(Parcel state);
        void onRestoreState(Parcel state);
    }

    public static Parcelable onSaveInstanceState(Parcelable superState, StateListener listener) {
        Parcel state = Parcel.obtain();
        listener.onSaveState(state);
        return new ParcelSavedState(superState, state);
    }

    public static Parcelable onRestoreInstanceState(Parcelable state, StateListener listener) {
        if (!(state instanceof ParcelSavedState)) {
            throw new IllegalArgumentException("state must be ParcelSavedState: " + state);
        }
        ParcelSavedState savedState = (ParcelSavedState) state;
        savedState.state.setDataPosition(0);
        listener.onRestoreState(savedState.state);
        savedState.state.recycle();
        return savedState.superState;
    }

    private final Parcelable superState;
    private final Parcel state;

    private ParcelSavedState(Parcelable superState, Parcel state) {
        this.superState = superState;
        this.state = state;
    }

    private ParcelSavedState(Parcel source, ClassLoader loader) {
        superState = source.readParcelable(loader);
        state = Parcel.obtain();
        int start = source.dataPosition();
        state.appendFrom(source, start, source.dataSize() - start);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(superState, flags);
        dest.appendFrom(state, 0, state.dataSize());
        state.recycle();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ParcelSavedState> CREATOR
            = new ClassLoaderCreator<ParcelSavedState>() {
        @Override
        public ParcelSavedState createFromParcel(Parcel source, ClassLoader loader) {
            return new ParcelSavedState(source, loader);
        }
        @Override
        public ParcelSavedState createFromParcel(Parcel source) {
            return createFromParcel(source, null);
        }
        @Override
        public ParcelSavedState[] newArray(int size) {
            return new ParcelSavedState[size];
        }
    };
}
