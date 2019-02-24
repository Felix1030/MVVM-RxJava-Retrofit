package com.felix.mvvm_rxjava_retrofit.home;

import android.app.Application;

import com.felix.apt.AppApiServices;
import com.felix.base.arch.RxCommand;
import com.felix.base.viewmodel.BaseViewModel;
import com.felix.mvvm_rxjava_retrofit.model.MusicRaingItem;

import java.util.List;

import androidx.annotation.NonNull;

public class HomeViewModel extends BaseViewModel {

    private RxCommand<List<MusicRaingItem>> mDatas;

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public RxCommand<List<MusicRaingItem>> getMusicRanking() {
        if (null == mDatas) {
            mDatas = RxCommand.create(o -> AppApiServices.musicRankings().toObservable());
        }
        return mDatas;
    }
}
