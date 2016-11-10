package com.sensorfields.digiduck.android.presenter;

import android.os.Parcel;
import android.view.View;

import com.sensorfields.android.mvp.Presenter;
import com.sensorfields.digiduck.android.model.DocumentRepository;
import com.sensorfields.digiduck.android.view.RecentScreenView;

import timber.log.Timber;

public class RecentScreenPresenter implements Presenter {

    private final RecentScreenView view;
    private final DocumentRepository documentRepository;

    private boolean isFirstAttach = true;

    public RecentScreenPresenter(RecentScreenView view, DocumentRepository documentRepository) {
        this.view = view;
        this.documentRepository = documentRepository;

        view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                onAttachedToWindow();
            }
            @Override
            public void onViewDetachedFromWindow(View v) {
                onDetachedFromWindow();
            }
        });
    }

    public void onFirstAttachedToWindow() {
        Timber.e("onFirstAttachedToWindow");
    }

    public void onAttachedToWindow() {
        Timber.e("onAttachedToWindow");
        if (isFirstAttach) {
            onFirstAttachedToWindow();
        }
    }

    public void onDetachedFromWindow() {
        Timber.e("onDetachedFromWindow");
    }

    @Override
    public void onSaveState(Parcel state) {
        Timber.e("onSavedState");
    }

    @Override
    public void onRestoreState(Parcel state) {
        Timber.e("onRestoreState");
        isFirstAttach = false;
    }

    public static class Factory implements Presenter.Factory {

        private final DocumentRepository documentRepository;

        public Factory(DocumentRepository documentRepository) {
            this.documentRepository = documentRepository;
        }

        public RecentScreenPresenter create(RecentScreenView view) {
            return new RecentScreenPresenter(view, documentRepository);
        }
    }
}
