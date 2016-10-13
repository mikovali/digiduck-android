package com.sensorfields.digiduck.android;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sensorfields.digiduck.android.infrastructure.flow.ActivityDispatcher;
import com.sensorfields.digiduck.android.infrastructure.flow.ParcelableKeyParceler;
import com.sensorfields.digiduck.android.screen.RecentKey;

import flow.Flow;

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
        setContentView(R.layout.activity);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Flow.configure(newBase, this)
                .dispatcher(new ActivityDispatcher(this))
                .keyParceler(new ParcelableKeyParceler())
                .defaultKey(new RecentKey())
                .install());
    }

    @Override
    public void onBackPressed() {
        if (!Flow.get(this).goBack()) {
            super.onBackPressed();
        }
    }
}
