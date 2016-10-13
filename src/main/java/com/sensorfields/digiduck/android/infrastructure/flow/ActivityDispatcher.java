package com.sensorfields.digiduck.android.infrastructure.flow;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.sensorfields.digiduck.android.R;

import butterknife.ButterKnife;
import flow.Direction;
import flow.Dispatcher;
import flow.Traversal;
import flow.TraversalCallback;

public class ActivityDispatcher implements Dispatcher {

    private final Activity activity;

    public ActivityDispatcher(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void dispatch(@NonNull Traversal traversal, @NonNull TraversalCallback callback) {
        // origin
        ViewGroup parentView = ButterKnife.findById(activity, R.id.container);
        ViewKey originKey = traversal.origin == null ? null : traversal.origin.<ViewKey>top();
        if (traversal.direction == Direction.FORWARD && originKey != null
                && parentView.getChildCount() == 1) {
            traversal.getState(originKey).save(parentView.getChildAt(0));
        }
        parentView.removeAllViews();

        // destination
        ViewKey destinationKey = traversal.destination.top();
        View destinationView = destinationKey.createView(parentView);
        parentView.addView(destinationView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        if (traversal.direction == Direction.BACKWARD) {
            traversal.getState(destinationKey).restore(destinationView);
        }

        callback.onTraversalCompleted();
    }
}
