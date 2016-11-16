package com.sensorfields.android.task;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subjects.Subject;

public class SingleTask<T> extends Task<T, Single<T>, DisposableSingleObserver<T>> {

    @Override
    protected void subscribeToSubject(Single<T> source, Subject<T> subject) {
        source.toObservable().subscribe(subject);
    }

    @Override
    protected void subscribeToObserver(Subject<T> subject, DisposableSingleObserver<T> observer) {
        subject.singleOrError().subscribe(observer);
    }
}
