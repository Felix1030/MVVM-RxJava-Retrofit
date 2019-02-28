package com.felix.nav.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.felix.apt.NavApiServices;
import com.felix.base.viewmodel.BaseViewModel;
import com.felix.nav.model.NavModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class NavViewModel extends BaseViewModel {

    private MutableLiveData<List<MultiItemEntity>> mNavItems;

    public NavViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<MultiItemEntity>> getNavItems() {
        if (null == mNavItems) {
            mNavItems = new MutableLiveData<>();
        }
        return mNavItems;
    }

    @SuppressLint("CheckResult")
    public void initNavis() {
        NavApiServices.getNavis()
                .map(navModels -> {
                    List<MultiItemEntity> navs = new ArrayList<>();
                    for (NavModel navModel : navModels) {
                        navs.add(navModel);
                        navs.addAll(navModel.getArticles());
                    }
                    // 暂时最后一行做处理
                    navs.add(new NavModel(""));
                    return navs;
                })
                .subscribe(mNavItems::postValue, v -> {});
    }
}
