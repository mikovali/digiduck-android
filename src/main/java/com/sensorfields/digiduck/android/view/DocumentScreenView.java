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
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import timber.log.Timber;

import static com.sensorfields.digiduck.android.Constants.DOCUMENT_GET_FILE;

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

    private final DisposableSingleObserver<File> fileSubscriber
            = new DisposableSingleObserver<File>() {
        @Override
        public void onSuccess(File value) {
            Timber.e("onSuccess: %s", value);
            fileListView.addFile(value);
        }
        @Override
        public void onError(Throwable e) {
            Timber.e(e, "onError");
        }
    };

    private final CompositeDisposable disposables = new CompositeDisposable();

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
        disposables.add(fileRepository.get(DOCUMENT_GET_FILE).subscribeWith(fileSubscriber));
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
        disposables.dispose();
        super.onDetachedFromWindow();
    }
}
