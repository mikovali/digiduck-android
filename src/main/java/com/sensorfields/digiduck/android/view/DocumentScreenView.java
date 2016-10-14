package com.sensorfields.digiduck.android.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;

import com.sensorfields.digiduck.android.Application;
import com.sensorfields.digiduck.android.R;
import com.sensorfields.digiduck.android.model.File;
import com.sensorfields.digiduck.android.model.FileRepository;

import javax.inject.Inject;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class DocumentScreenView extends CoordinatorLayout {

    @Inject FileRepository fileRepository;

    private final Subscriber<File> fileSubscriber = new Subscriber<File>() {
        @Override
        public void onCompleted() {
            Timber.e("onCompleted");
        }
        @Override
        public void onError(Throwable e) {
            Timber.e(e, "onError");
        }
        @Override
        public void onNext(File file) {
            Timber.e("onNext: %s", file);
        }
    };

    private final CompositeSubscription subscriptions = new CompositeSubscription();

    public DocumentScreenView(Context context) {
        this(context, null);
    }

    public DocumentScreenView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DocumentScreenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Application.getApplicationComponent(context).inject(this);
        setId(R.id.document);
        inflate(context, R.layout.document_screen, this);
    }
}
