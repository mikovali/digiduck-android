package com.sensorfields.digiduck.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.sensorfields.digiduck.android.view.RecentScreenView;

/**
 * Recent signed documents
 *  - sort by date, name
 *  - find
 * View signed document
 * Open documents (Android default picker)
 *  -> View signed document (no signatures)
 * Share signed document
 *
 * Settings:
 *  - where to store signed documents (SD card or private storage)
 *  - clear recent documents
 *  - Day/Night mode
 */
public class Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new RecentScreenView(this), new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }
}
