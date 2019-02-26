package com.felix.mvvm_rxjava_retrofit.fragments.home;

import android.app.Application;

import com.felix.base.viewmodel.BaseViewModel;

import androidx.annotation.NonNull;

public class HomeViewModel extends BaseViewModel {

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }



//    public RxCommand<List<MusicRaingItem>> getMusicRanking() {
//        if (null == mDatas) {
//            mDatas = RxCommand.create(o -> AppApiServices.musicRankings().toObservable());
//        }
//        return mDatas;
//    }
}
