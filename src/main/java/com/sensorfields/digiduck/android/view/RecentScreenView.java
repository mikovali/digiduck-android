package com.sensorfields.digiduck.android.view;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.sensorfields.android.mvp.ParcelSavedState;
import com.sensorfields.digiduck.android.R;
import com.sensorfields.digiduck.android.model.Document;
import com.sensorfields.digiduck.android.presenter.RecentScreenPresenter;
import com.sensorfields.digiduck.android.screen.DocumentKey;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;

import static com.sensorfields.digiduck.android.Application.getPresenterFactory;

/**
 * Some registry for Observables (Subjects)
 * Maybe there is some way to do it with Dagger scopes, figure it out
 */
public class RecentScreenView extends CoordinatorLayout {

    private final RecentScreenPresenter presenter;

    @BindView(R.id.toolbar) Toolbar toolbarView;
    @BindView(R.id.recentDocuments) DocumentListView documentsView;
    @BindView(R.id.recentCreateButton) View createButton;

    public RecentScreenView(Context context) {
        this(context, null);
    }

    public RecentScreenView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecentScreenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        presenter = getPresenterFactory(context, RecentScreenPresenter.Factory.class).create(this);
        setId(R.id.recent);
        inflate(context, R.layout.recent_screen, this);
        ButterKnife.bind(this);
        toolbarView.inflateMenu(R.menu.recent);
    }

    public void setDocuments(List<Document> documents) {
        documentsView.setDocuments(documents);
    }

    @OnClick(R.id.recentCreateButton)
    void onCreateButtonClick() {
        Flow.get(getContext()).set(new DocumentKey());
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return ParcelSavedState.onSaveInstanceState(super.onSaveInstanceState(), presenter);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(ParcelSavedState.onRestoreInstanceState(state, presenter));
    }
}
