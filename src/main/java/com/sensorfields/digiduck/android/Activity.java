package com.sensorfields.digiduck.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sensorfields.android.ActivityService;
import com.sensorfields.digiduck.android.infrastructure.flow.ActivityDispatcher;
import com.sensorfields.digiduck.android.infrastructure.flow.ParcelableKeyParceler;
import com.sensorfields.digiduck.android.screen.RecentKey;

import javax.inject.Inject;

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

    @Inject ActivityService activityService;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application.getApplicationComponent(this).inject(this);
        activityService.onCreate(this, savedInstanceState);
        setContentView(R.layout.activity);
    }

    @Override
    protected void onStart() {
        super.onStart();
        activityService.onStart(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityService.onResume(this);
    }

    @Override
    protected void onPause() {
        activityService.onPause(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        activityService.onStop(this);
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        activityService.onSaveInstanceState(this, outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        activityService.onDestroy(this);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        activityService.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        activityService.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}
