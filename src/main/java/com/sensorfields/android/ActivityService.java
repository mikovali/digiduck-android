package com.sensorfields.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jakewharton.rxrelay.PublishRelay;

import rx.Observable;
import timber.log.Timber;

public class ActivityService {

    private final PublishRelay<ActivityResult> activityResultRelay = PublishRelay.create();

    private Activity currentActivity;

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public Observable<ActivityResult> getActivityResult() {
        return activityResultRelay;
    }

    public void onCreate(Activity activity, @Nullable Bundle savedInstanceState) {
        Timber.d("onCreate: %s, %s", activity, savedInstanceState);
        currentActivity = activity;
    }

    public void onStart(Activity activity) {
        Timber.d("onStart: %s", activity);
        currentActivity = activity;
    }

    public void onResume(Activity activity) {
        Timber.d("onResume: %s", activity);
        currentActivity = activity;
    }

    public void onPause(Activity activity) {
        Timber.d("onPause: %s", activity);
    }

    public void onStop(Activity activity) {
        Timber.d("onStop: %s", activity);
    }

    public void onSaveInstanceState(Activity activity, Bundle outState) {
        Timber.d("onSaveInstanceState: %s, %s", activity, outState);
    }

    public void onDestroy(Activity activity) {
        Timber.d("onDestroy: %s", activity);
        currentActivity = null;
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        Timber.d("onActivityResult: %s, %d, %d, %s", activity, requestCode, resultCode, data);
        activityResultRelay.call(new ActivityResult(requestCode, resultCode, data));
    }

    public void onRequestPermissionsResult(Activity activity, int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Timber.d("onRequestPermissionsResult: %s, %d, %s, %s", activity, requestCode, permissions,
                grantResults);
    }

    public static final class ActivityResult {

        public final int requestCode;
        public final int resultCode;
        public final Intent data;

        private ActivityResult(int requestCode, int resultCode, Intent data) {
            this.requestCode = requestCode;
            this.resultCode = resultCode;
            this.data = data;
        }

        @Override
        public String toString() {
            return "ActivityResult{" +
                    "requestCode=" + requestCode +
                    ", resultCode=" + resultCode +
                    ", data=" + data +
                    '}';
        }
    }
}
