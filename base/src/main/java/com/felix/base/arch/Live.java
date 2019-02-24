package com.felix.base.arch;

import android.os.Looper;

import org.reactivestreams.Publisher;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 *https://github.com/listenzz/Live/blob/master/library/src/main/java/com/shundaojia/live/Live.java
 * @author yiyang
 */
public final class Live<T> implements ObservableTransformer<T, T>, FlowableTransformer<T, T>, LifecycleObserver {

    private static final String TAG = "Live";

    public static <T> ObservableTransformer<T, T> bindLifecycle(LifecycleOwner owner) {
        return new Live<>(owner);
    }

    public static <T> FlowableTransformer<T, T> bindFlowableLifecycle(LifecycleOwner owner) {
        return new Live<>(owner);
    }

    private final PublishSubject<T> mSubject = PublishSubject.create();

    private final LifecycleOwner mLifecycleOwner;

    private Disposable mDisposable;

    private T mData;

    private boolean mActive;

    private int mVersion = -1;

    private int mLastVersion = -1;

    private Live(LifecycleOwner owner) {
        mLifecycleOwner = owner;
    }

    @MainThread
    @Override
    public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
        assertMainThread();
        if (mLifecycleOwner.getLifecycle().getCurrentState() != Lifecycle.State.DESTROYED) {
            mLifecycleOwner.getLifecycle().addObserver(this);
            mDisposable = upstream.subscribe(it -> {
                assertMainThread();
                ++ mVersion;
                mData = it;
                considerNotify();
            }, it -> {
                assertMainThread();
                mSubject.onError(it);
            }, this::assertMainThread);

            return  mSubject.doOnDispose(mDisposable::dispose);
        } else {
            return Observable.empty();
        }
    }

    @MainThread
    @Override
    public Publisher<T> apply(Flowable<T> upstream) {
        assertMainThread();
        if (mLifecycleOwner.getLifecycle().getCurrentState() != Lifecycle.State.DESTROYED) {
            mLifecycleOwner.getLifecycle().addObserver(this);
            mDisposable = upstream.subscribe(it -> {
                assertMainThread();
                ++ mVersion;
                mData = it;
                considerNotify();
            }, it -> {
                assertMainThread();
                mSubject.onError(it);
            }, this::assertMainThread);

            return  mSubject.doOnDispose(mDisposable::dispose).toFlowable(BackpressureStrategy.LATEST);
        } else {
            return Flowable.empty();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    void onStateChange() {
        if (this.mLifecycleOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
            if (mDisposable != null && !mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
            if (!mSubject.hasComplete()) {
                mSubject.onComplete();
            }
            mLifecycleOwner.getLifecycle().removeObserver(this);
        } else {
            this.activeStateChanged(Live.isActiveState(mLifecycleOwner.getLifecycle().getCurrentState()));
        }
    }

    void activeStateChanged(boolean newActive) {
        if (newActive != mActive) {
            mActive = newActive;
            considerNotify();
        }
    }

    void considerNotify() {
        if (mActive) {
            if (isActiveState(mLifecycleOwner.getLifecycle().getCurrentState())) {
                if (mLastVersion < mVersion) {
                    mLastVersion = mVersion;
                    if (!mSubject.hasComplete()) {
                        mSubject.onNext(mData);
                    }
                }
            }
        }
    }

    private void assertMainThread() {
        if (!isMainThread()) {
            throw new IllegalStateException("You should not use the Live Transformer at a background thread.");
        }
    }

    static boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    static boolean isActiveState(Lifecycle.State state) {
        return state.isAtLeast(Lifecycle.State.STARTED);
    }

}