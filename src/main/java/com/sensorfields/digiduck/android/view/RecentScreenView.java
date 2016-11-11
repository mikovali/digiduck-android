package com.sensorfields.digiduck.android.view;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.sensorfields.android.mvp.ParcelSavedState;
import com.sensorfields.digiduck.android.R;
import com.sensorfields.digiduck.android.model.Document;
import com.sensorfields.digiduck.android.model.DocumentRepository;
import com.sensorfields.digiduck.android.screen.DocumentKey;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subjects.AsyncSubject;
import timber.log.Timber;

import static com.sensorfields.digiduck.android.Application.getApplicationComponent;

/**
 * Some registry for Observables (Subjects)
 * Maybe there is some way to do it with Dagger scopes, figure it out
 */
public class RecentScreenView extends CoordinatorLayout implements ParcelSavedState.StateListener {

    // TODO ObservableRegistry or something
    private static AsyncSubject<List<Document>> findSubject;

    @Inject DocumentRepository documentRepository;

    @BindView(R.id.toolbar) Toolbar toolbarView;
    @BindView(R.id.recentDocuments) DocumentListView documentsView;
    @BindView(R.id.recentCreateButton) View createButton;

    private final AtomicBoolean create = new AtomicBoolean(true);

    private final DisposableSingleObserver<List<Document>> findObserver
            = new DisposableSingleObserver<List<Document>>() {
        @Override
        public void onSuccess(List<Document> value) {
            setDocuments(value);
            dispose();
            findSubject = null;
        }
        @Override
        public void onError(Throwable e) {
            Timber.e(e, "Find onError");
            dispose();
            findSubject = null;
        }
    };

    public RecentScreenView(Context context) {
        this(context, null);
    }

    public RecentScreenView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecentScreenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getApplicationComponent(context).inject(this);
        setId(R.id.recent);
        inflate(context, R.layout.recent_screen, this);
        ButterKnife.bind(this);
        toolbarView.inflateMenu(R.menu.recent);
        documentsView.getRefresh().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Timber.e("REFRESH");
                find();
            }
        });
    }

    public void setDocuments(List<Document> documents) {
        documentsView.setDocuments(documents);
    }

    @OnClick(R.id.recentCreateButton)
    void onCreateButtonClick() {
        Flow.get(getContext()).set(new DocumentKey());
    }

    void find() {
        if (findSubject == null) {
            findSubject = AsyncSubject.create();
            documentRepository.find().toObservable().subscribe(findSubject);
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (create.get()) {
            find();
        }
        if (findSubject != null) {
            findSubject.singleOrError().subscribe(findObserver);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        findObserver.dispose();
        super.onDetachedFromWindow();
    }

    @Override
    public void onSaveState(Parcel state) {
    }

    @Override
    public void onRestoreState(Parcel state) {
        create.set(false);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return ParcelSavedState.onSaveInstanceState(super.onSaveInstanceState(), this);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(ParcelSavedState.onRestoreInstanceState(state, this));
    }
}
