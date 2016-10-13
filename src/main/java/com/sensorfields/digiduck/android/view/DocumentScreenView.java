package com.sensorfields.digiduck.android.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.sensorfields.digiduck.android.R;

public class DocumentScreenView extends LinearLayout {

    public DocumentScreenView(Context context) {
        this(context, null);
    }

    public DocumentScreenView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DocumentScreenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setId(R.id.document);
        setOrientation(VERTICAL);
        inflate(context, R.layout.document_screen, this);
    }
}
