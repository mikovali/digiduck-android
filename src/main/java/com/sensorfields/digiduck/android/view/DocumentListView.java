package com.sensorfields.digiduck.android.view;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sensorfields.android.mvp.ParcelSavedState;
import com.sensorfields.digiduck.android.R;
import com.sensorfields.digiduck.android.model.Document;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class DocumentListView extends SwipeRefreshLayout implements
        SwipeRefreshLayout.OnRefreshListener, ParcelSavedState.StateListener {

    private final PublishSubject<Object> refreshSubject = PublishSubject.create();

    private final RecyclerView recyclerView;
    private final DocumentAdapter adapter;

    public DocumentListView(Context context) {
        this(context, null);
    }

    public DocumentListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnRefreshListener(this);

        recyclerView = new RecyclerView(context);
        recyclerView.setId(R.id.documentListRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter = new DocumentAdapter());
        addView(recyclerView, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
    }

    public void setDocuments(List<Document> documents) {
        setRefreshing(false);
        adapter.setDocuments(documents);
        adapter.notifyDataSetChanged();
    }

    public Observable<Object> getRefresh() {
        return refreshSubject;
    }

    @Override
    public void onRefresh() {
        refreshSubject.onNext(Object.class);
    }

    @Override
    public void onSaveState(Parcel state) {
        state.writeTypedList(adapter.documents);
    }

    @Override
    public void onRestoreState(Parcel state) {
        adapter.documents = state.createTypedArrayList(Document.CREATOR);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return ParcelSavedState.onSaveInstanceState(super.onSaveInstanceState(), this);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(ParcelSavedState.onRestoreInstanceState(state, this));
    }

    static class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder> {

        private List<Document> documents;

        void setDocuments(List<Document> documents) {
            this.documents = documents;
        }

        @Override
        public DocumentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DocumentViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_selectable_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(DocumentViewHolder holder, int position) {
            holder.nameView.setText(documents.get(position).name);
        }

        @Override
        public int getItemCount() {
            return documents == null ? 0 : documents.size();
        }

        static class DocumentViewHolder extends RecyclerView.ViewHolder {
            @BindView(android.R.id.text1) TextView nameView;
            DocumentViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                nameView.setClickable(true);
            }
        }
    }
}
