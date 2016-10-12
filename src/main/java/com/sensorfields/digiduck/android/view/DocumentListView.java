package com.sensorfields.digiduck.android.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sensorfields.digiduck.android.model.Document;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DocumentListView extends RecyclerView {

    private final DocumentAdapter adapter;

    public DocumentListView(Context context) {
        this(context, null);
    }

    public DocumentListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DocumentListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutManager(new LinearLayoutManager(context));
        setAdapter(adapter = new DocumentAdapter());
    }

    public void setDocuments(List<Document> documents) {
        adapter.setDocuments(documents);
        adapter.notifyDataSetChanged();
    }

    static class DocumentAdapter extends Adapter<DocumentAdapter.DocumentViewHolder> {

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

        static class DocumentViewHolder extends ViewHolder {
            @BindView(android.R.id.text1) TextView nameView;
            DocumentViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                nameView.setClickable(true);
            }
        }
    }
}
