package com.sensorfields.digiduck.android.view;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sensorfields.digiduck.android.R;
import com.sensorfields.digiduck.android.model.File;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FileListView extends RecyclerView {

    private final FileAdapter adapter;

    public FileListView(Context context) {
        this(context, null);
    }

    public FileListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FileListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutManager(new LinearLayoutManager(context));
        setAdapter(adapter = new FileAdapter());
    }

    public void setFiles(List<File> files) {
        adapter.setFiles(files);
    }

    public void addFile(File file) {
        adapter.addFile(file);
    }

    static class FileAdapter extends Adapter<FileAdapter.FileViewHolder> {

        private final List<File> files = new ArrayList<>();

        void setFiles(List<File> files) {
            this.files.clear();
            this.files.addAll(files);
            notifyDataSetChanged();
        }

        void addFile(File file) {
            files.add(file);
            notifyItemInserted(getItemCount() - 1);
        }

        void removeFile(File file) {
            int position = files.indexOf(file);
            if (position != -1) {
                files.remove(position);
                notifyItemRemoved(position);
            }
        }

        private File getFile(int position) {
            return files.get(position);
        }

        @Override
        public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FileViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.file_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(FileViewHolder holder, int position) {
            File file = getFile(position);

            holder.nameView.setText(file.name);
        }

        @Override
        public int getItemCount() {
            return files.size();
        }

        static class FileViewHolder extends ViewHolder {
            @BindView(R.id.fileName) TextView nameView;
            FileViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    // State

    @Override
    protected Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), adapter.files);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        adapter.setFiles(savedState.files);
    }

    private static class SavedState extends BaseSavedState {

        final List<File> files;

        SavedState(Parcelable superState, List<File> files) {
            super(superState);
            this.files = files;
        }

        SavedState(Parcel source) {
            super(source);
            files = source.createTypedArrayList(File.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeTypedList(files);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }
            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
