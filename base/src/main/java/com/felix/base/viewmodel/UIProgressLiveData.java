package com.felix.base.viewmodel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

public class UIProgressLiveData extends SingleLiveEvent {
    private SingleLiveEvent<String> mShowProgressEvent;
    private SingleLiveEvent<Void> mDismissProgressEvent;

    public SingleLiveEvent<String> getShowProgressEvent() {
        return mShowProgressEvent = createSingleLiveData(mShowProgressEvent);
    }

    public SingleLiveEvent<Void> getDismissProgressEvent() {
        return mDismissProgressEvent = createSingleLiveData(mDismissProgressEvent);
    }

    private <T> SingleLiveEvent<T> createSingleLiveData(SingleLiveEvent<T> liveEvent) {
        if (liveEvent == null) {
            liveEvent = new SingleLiveEvent<>();
        }
        return liveEvent;
    }

    @Override
    public void observe(LifecycleOwner owner, Observer observer) {
        super.observe(owner, observer);
    }
}
