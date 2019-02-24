package com.felix.base.arch;


import androidx.annotation.NonNull;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 *
 * @author yiyang
 */
public class Variable<T> {

    private  T val;

    private final Subject<T> subject;

    public Variable(@NonNull T initial) {
        val = initial;
        BehaviorSubject<T> subject = BehaviorSubject.create();
        this.subject = subject.toSerialized();
    }

    @NonNull
    public T value() {
        synchronized (this) {
            return val;
        }
    }

    public void setValue(@NonNull T value) {
        synchronized (this) {
            this.val = value;
        }
        subject.onNext(value);
    }

    public Observable<T> asObservable() {
        return subject;
    }

    public Flowable<T> asFlowable() {
        return subject.toFlowable(BackpressureStrategy.LATEST);
    }

}
