package com.sensorfields.digiduck.android.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.sensorfields.digiduck.android.R;
import com.sensorfields.digiduck.android.model.Document;
import com.sensorfields.digiduck.android.screen.DocumentKey;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import flow.Flow;

public class RecentScreenView extends CoordinatorLayout {

    @BindView(R.id.toolbar) Toolbar toolbarView;
    @BindView(R.id.recentDocuments) DocumentListView documentsView;
    @BindView(R.id.recentCreateButton) View createButton;

    public RecentScreenView(Context context) {
        this(context, null);
    }

    public RecentScreenView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecentScreenView(final Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setId(R.id.recent);
        inflate(context, R.layout.recent_screen, this);
        ButterKnife.bind(this);
        toolbarView.inflateMenu(R.menu.recent);
        documentsView.setDocuments(Arrays.asList(DOCUMENTS));
        createButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Flow.get(context).set(new DocumentKey());
            }
        });
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
