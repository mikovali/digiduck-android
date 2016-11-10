package com.sensorfields.digiduck.android.infrastructure.flow;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.sensorfields.digiduck.android.R;

import butterknife.ButterKnife;
import flow.Direction;
import flow.Dispatcher;
import flow.Flow;
import flow.Traversal;
import flow.TraversalCallback;

public class ActivityDispatcher implements Dispatcher {

    private final Activity activity;

    public ActivityDispatcher(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void dispatch(@NonNull Traversal traversal, @NonNull TraversalCallback callback) {
        ViewGroup parentView = ButterKnife.findById(activity, R.id.container);

        ViewKey destinationKey = traversal.destination.top();
        ViewKey originKey = traversal.origin == null ? null : traversal.origin.<ViewKey>top();

        // do not replace the same view with itself
        if (parentView.getChildCount() == 1
                && destinationKey.equals(Flow.getKey(parentView.getChildAt(0)))) {
            callback.onTraversalCompleted();
            return;
        }

        // origin
        if (traversal.direction == Direction.FORWARD && originKey != null
                && parentView.getChildCount() == 1) {
            traversal.getState(originKey).save(parentView.getChildAt(0));
        }
        parentView.removeAllViews();

        // destination
        View destinationView = destinationKey.createView(traversal.createContext(destinationKey,
                parentView.getContext()));
        if (traversal.direction == Direction.BACKWARD) {
            traversal.getState(destinationKey).restore(destinationView);
        }
        parentView.addView(destinationView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        callback.onTraversalCompleted();
    }
}
