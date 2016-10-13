package com.sensorfields.digiduck.android.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.sensorfields.android.ActivityService;
import com.sensorfields.digiduck.android.Application;
import com.sensorfields.digiduck.android.R;
import com.sensorfields.digiduck.android.model.Document;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * TODO refactor ActivityService out and create a FilePicker or DocumentPicker or something service
 */
public class RecentScreenView extends CoordinatorLayout {

    @Inject ActivityService activityService;

    @BindView(R.id.toolbar) Toolbar toolbarView;
    @BindView(R.id.recentDocuments) DocumentListView documentsView;
    @BindView(R.id.recentCreateButton) View createButton;

    private final Action1<ActivityService.ActivityResult> activityResultSubscriber
            = new Action1<ActivityService.ActivityResult>() {
        @Override
        public void call(ActivityService.ActivityResult activityResult) {
            Timber.e("onActivityResult: %s", activityResult);
        }
    };

    private final CompositeSubscription subscriptions = new CompositeSubscription();

    public RecentScreenView(Context context) {
        this(context, null);
    }

    public RecentScreenView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecentScreenView(final Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Application.getApplicationComponent(context).inject(this);
        setId(R.id.recent);
        inflate(context, R.layout.recent_screen, this);
        ButterKnife.bind(this);
        toolbarView.inflateMenu(R.menu.recent);
        documentsView.setDocuments(Arrays.asList(DOCUMENTS));
    }

    @OnClick(R.id.recentCreateButton)
    void onCreateButtonClick() {
        Activity activity = activityService.getCurrentActivity();
        if (activity != null) {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setType("*/*");

            activity.startActivityForResult(intent, 3);
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        subscriptions.add(activityService.getActivityResult().subscribe(activityResultSubscriber));
    }

    @Override
    public void onDetachedFromWindow() {
        subscriptions.unsubscribe();
        super.onDetachedFromWindow();
    }

    private static final Document[] DOCUMENTS = new Document[] {
            new Document("Miko Väli contract.bdoc"),
            new Document("KÜ Lubja 7b.bdoc"),
            new Document("Sensor Fields OÜ hinnapakkumine.bdoc"),
            new Document("Leping Massruum OÜ.bdoc"),
            new Document("Maksekorraldus.bdoc"),
            new Document("Lets agree on this.bdoc"),
            new Document("Notar 2016 dokument.bdoc")
    };
}
