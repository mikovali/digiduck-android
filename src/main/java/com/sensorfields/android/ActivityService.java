package com.sensorfields.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jakewharton.rxrelay.PublishRelay;

import rx.Observable;

public class ActivityService {

    private final PublishRelay<ActivityResult> activityResultRelay = PublishRelay.create();

    private Activity currentActivity;

    @Nullable
    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public Observable<ActivityResult> getActivityResult() {
        return activityResultRelay;
    }

    public void onCreate(Activity activity, @Nullable Bundle savedInstanceState) {
        currentActivity = activity;
    }

    public void onStart(Activity activity) {
        currentActivity = activity;
    }

    public void onResume(Activity activity) {
        currentActivity = activity;
    }

    public void onPause(Activity activity) {
    }

    public void onStop(Activity activity) {
    }

    public void onSaveInstanceState(Activity activity, Bundle outState) {
    }

    public void onDestroy(Activity activity) {
        currentActivity = null;
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        activityResultRelay.call(new ActivityResult(requestCode, resultCode, data));
    }

    public void onRequestPermissionsResult(Activity activity, int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
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
