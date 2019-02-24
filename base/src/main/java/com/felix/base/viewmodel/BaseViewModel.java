package com.felix.base.viewmodel;

import android.app.Application;

import com.felix.base.basic.ILifeCycleView;
import com.trello.rxlifecycle3.LifecycleProvider;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.FlowableTransformer;

public class BaseViewModel extends AndroidViewModel implements IBaseViewModel {

    private LifecycleProvider mLifecycleProvider;
    private UIProgressLiveData mUIProgressLiveData;
    private ILifeCycleView mLifeCycleView;

    /**
     * 注入RxLifecycle生命周期
     * @param lifecycle
     */
    public void injectLifecycleProvider(LifecycleProvider lifecycle) {
        this.mLifecycleProvider = lifecycle;
    }

    public LifecycleProvider getLifecycleProvider() {
        return mLifecycleProvider;
    }

    public void injectLifecycleView(ILifeCycleView lifecycleView) {
        this.mLifeCycleView = lifecycleView;
    }

    public ILifeCycleView getLifecycleViewProvider() {
        return mLifeCycleView;
    }

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public UIProgressLiveData getUIProgressLiveData() {
        if (null == mUIProgressLiveData) {
            mUIProgressLiveData = new UIProgressLiveData();
        }
        return mUIProgressLiveData;
    }

    /**
     * 显示loading框
     */
    public void showProgress() {
        showProgress("");
    }

    /**
     * 显示loading框
     * @param progressMessage 文言
     */
    public void showProgress(String progressMessage) {
        getUIProgressLiveData().getShowProgressEvent().postValue(progressMessage);
    }

    // 隐藏弹窗
    public void dismissProgress() {
        getUIProgressLiveData().getDismissProgressEvent().call();
    }

    // progress封装带message
    public <T> FlowableTransformer<T,T> provideProgress(String progressMessage) {
        return upstream -> upstream.doOnSubscribe(subscription -> showProgress(progressMessage))
                .doOnNext(consumer -> dismissProgress())
                .doOnError(throwable -> dismissProgress());
    }

    // progress封装
    public <T> FlowableTransformer<T,T> provideProgress() {
        return provideProgress("");
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void registerRxBus() {

    }

    @Override
    public void removeRxBus() {

    }
}
