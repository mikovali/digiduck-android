package com.sensorfields.digiduck.android.presenter;

import android.os.Parcel;
import android.view.View;

import com.sensorfields.android.mvp.ParcelSavedState;
import com.sensorfields.android.mvp.Presenter;
import com.sensorfields.digiduck.android.model.Document;
import com.sensorfields.digiduck.android.model.DocumentRepository;
import com.sensorfields.digiduck.android.view.RecentScreenView;

import java.util.List;

import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subjects.AsyncSubject;
import timber.log.Timber;

public class RecentScreenPresenter implements Presenter, ParcelSavedState.StateListener {

    private static AsyncSubject<List<Document>> findSubject;

    private final DisposableSingleObserver<List<Document>> findObserver
            = new DisposableSingleObserver<List<Document>>() {
        @Override
        public void onSuccess(List<Document> value) {
            view.setDocuments(value);
        }
        @Override
        public void onError(Throwable e) {
            Timber.e(e, "Find onError");
        }
    };

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
        findSubject = AsyncSubject.create();
        documentRepository.find().toObservable().subscribe(findSubject);
    }

    public void onAttachedToWindow() {
        if (isFirstAttach) {
            onFirstAttachedToWindow();
        }
        if (findSubject != null) {
            findSubject.singleOrError().subscribe(findObserver);
        }
    }

    public void onDetachedFromWindow() {
        findObserver.dispose();
    }

    @Override
    public void onSaveState(Parcel state) {
    }

    @Override
    public void onRestoreState(Parcel state) {
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
