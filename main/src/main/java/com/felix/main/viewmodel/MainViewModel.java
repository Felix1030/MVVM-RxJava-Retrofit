package com.felix.main.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;

import com.felix.apt.MainApiServices;
import com.felix.base.viewmodel.BaseViewModel;
import com.felix.main.model.BannerModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class MainViewModel extends BaseViewModel {

    private MutableLiveData<List<BannerModel>> mBanner;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<BannerModel>> getBanner() {
        if (null == mBanner) {
            mBanner = new MutableLiveData<>();
        }
        return mBanner;
    }

    @SuppressLint("CheckResult")
    public void initBanners() {
        MainApiServices.getBanners()
                .compose(getLifecycleViewProvider().bindToDestroy())
                .subscribe(bannerModels -> getBanner().postValue(bannerModels), throwable -> {});
    }
}
