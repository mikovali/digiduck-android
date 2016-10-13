package com.sensorfields.digiduck.android.infrastructure.flow;

import android.view.View;
import android.view.ViewGroup;

public interface ViewKey {

    View createView(ViewGroup parentView);
}
