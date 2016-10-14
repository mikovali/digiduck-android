package com.sensorfields.digiduck.android.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MenuItem;

import com.sensorfields.digiduck.android.Application;
import com.sensorfields.digiduck.android.R;
import com.sensorfields.digiduck.android.model.File;
import com.sensorfields.digiduck.android.model.FileRepository;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Share
 * Add File
 * Add Signature
 */
public class DocumentScreenView extends CoordinatorLayout {

    @Inject FileRepository fileRepository;

    @BindView(R.id.toolbar) Toolbar toolbarView;
    @BindView(R.id.documentFiles) FileListView fileListView;
    @BindView(R.id.documentSignatures) SignatureListView signatureListView;

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
            fileListView.addFile(file);
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
        ButterKnife.bind(this);
        toolbarView.inflateMenu(R.menu.document);
        toolbarView.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.documentFileAddButton:
                        onFileAddButtonClick();
                        return true;
                    case R.id.documentSignatureAddButton:
                        onSignatureAddButtonClick();
                        return true;
                    case R.id.documentShareButton:
                        onShareButtonClick();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void onFileAddButtonClick() {
        Timber.e("SUBSCRIBE");
        subscriptions.add(fileRepository.get().subscribe(fileSubscriber));
    }

    private void onSignatureAddButtonClick() {
    }

    private void onShareButtonClick() {
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Timber.e("onAttachedToWindow");
    }

    @Override
    public void onDetachedFromWindow() {
        Timber.e("onDetachedFromWindow");
        subscriptions.unsubscribe();
        super.onDetachedFromWindow();
    }
}
