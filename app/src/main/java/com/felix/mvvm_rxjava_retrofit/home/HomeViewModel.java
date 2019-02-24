package com.felix.mvvm_rxjava_retrofit.home;

import android.annotation.SuppressLint;
import android.app.Application;

import com.felix.apt.AppApiServices;
import com.felix.base.viewmodel.BaseViewModel;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class HomeViewModel extends BaseViewModel {

    private MutableLiveData<Boolean> mResetSuccess;

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    // 监听以及执行方法
    public MutableLiveData<Boolean> getIsReset() {
        if (null == mResetSuccess) {
            mResetSuccess = new MutableLiveData<>();
            reset();
        }
        return mResetSuccess;
    }

    @SuppressLint("CheckResult")
    private void reset() {
        AppApiServices.reset("")
                .compose(getLifecycleViewProvider().bindToDestroy())
                .compose(provideProgress())
                .subscribe(consumer -> mResetSuccess.setValue(consumer), throwable -> {});

    }
}
