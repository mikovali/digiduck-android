package com.sensorfields.android.task;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.Subject;

public class ObservableTask<T> extends Task<T, Observable<T>, DisposableObserver<T>> {

    @Override
    protected void subscribeToSubject(Observable<T> source, Subject<T> subject) {
        source.subscribe(subject);
    }

    @Override
    protected void subscribeToObserver(Subject<T> subject, DisposableObserver<T> observer) {
        subject.subscribe(observer);
    }
}
