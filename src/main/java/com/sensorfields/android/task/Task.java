package com.sensorfields.android.task;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import timber.log.Timber;

public abstract class Task<T, S, O extends Disposable> {

    private Subject<T> subject;

    private O observer;

    private DisposableObserver<T> terminalEventObserver;

    public void start(S source) {
        if (subject == null) {
            subject = BehaviorSubject.create();
            subscribeToSubject(source, subject);
            attach();
        }
    }

    protected abstract void subscribeToSubject(S source, Subject<T> subject);

    protected abstract void subscribeToObserver(Subject<T> subject, O observer);

    void setObserver(O observer) {
        this.observer = observer;
    }

    void attach() {
        if (subject != null && !subject.hasObservers()) {
            subscribeToObserver(subject, observer);
            terminalEventObserver = subject.subscribeWith(new DisposableObserver<T>() {
                @Override
                public void onNext(T value) {}
                @Override
                public void onError(Throwable e) {
                    finish();
                }
                @Override
                public void onComplete() {
                    finish();
                }
            });
        }
    }

    void detach() {
        Timber.e("DETACH");
        if (terminalEventObserver != null) {
            terminalEventObserver.dispose();
        }
        terminalEventObserver = null;
        if (observer != null) {
            observer.dispose();
        }
        observer = null;
    }

    void finish() {
        Timber.e("FINISH");
        if (terminalEventObserver != null) {
            terminalEventObserver.dispose();
        }
        terminalEventObserver = null;
        if (observer != null) {
            observer.dispose();
        }
        subject = null;
    }
}
