package com.sensorfields.android.task;

import android.util.SparseArray;
import android.view.View;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;

public class TaskManager {

    private final SparseArray<Task<?, ?, ?>> tasks = new SparseArray<>();

    public <T> ObservableTask<T> getObservable(View view, int id,
                                               Callable<DisposableObserver<T>> observerFactory) {
        @SuppressWarnings("unchecked") ObservableTask<T> task = (ObservableTask<T>) tasks.get(id);
        if (task == null) {
            task = new ObservableTask<>();
            tasks.put(id, task);
        }
        task.setObserverFactory(observerFactory);

        viewShit(view, id);

        return task;
    }

    public <T> SingleTask<T> getSingle(View view, int id,
                                       Callable<DisposableSingleObserver<T>> observerFactory) {
        @SuppressWarnings("unchecked") SingleTask<T> task = (SingleTask<T>) tasks.get(id);
        if (task == null) {
            task = new SingleTask<>();
            tasks.put(id, task);
        }
        task.setObserverFactory(observerFactory);

        viewShit(view, id);

        return task;
    }

    /**
     * TODO Listener for View and Screen change
     */
    private void viewShit(View view, int id) {
        int viewId = view.getId();
        if (viewId == View.NO_ID) {
            throw new IllegalArgumentException("View id has to be set.");
        }
        Set<Integer> taskIds = viewTasks.get(viewId);
        if (taskIds == null) {
            taskIds = new HashSet<>();
            viewTasks.put(viewId, taskIds);
        }
        taskIds.add(id);
        view.addOnAttachStateChangeListener(onAttachStateChangeListener);
    }

    private final SparseArray<Set<Integer>> viewTasks = new SparseArray<>();

    private final View.OnAttachStateChangeListener onAttachStateChangeListener
            = new View.OnAttachStateChangeListener() {
        @Override
        public void onViewAttachedToWindow(View v) {
            for (Integer id : viewTasks.get(v.getId())) {
                tasks.get(id).attach();
            }
        }
        @Override
        public void onViewDetachedFromWindow(View v) {
            v.removeOnAttachStateChangeListener(this);
            for (Integer id : viewTasks.get(v.getId())) {
                tasks.get(id).detach();
            }
        }
    };
}
