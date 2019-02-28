package com.felix.base.http;

import io.reactivex.subscribers.ResourceSubscriber;

public abstract class RxSubscriber<T> extends ResourceSubscriber<T> {

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {

    }

    protected abstract void onSuccess(T t);
}
