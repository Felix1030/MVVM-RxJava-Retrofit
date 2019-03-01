package com.felix.know.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;

import com.felix.apt.KnowApiServices;
import com.felix.base.viewmodel.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

public class KnowListViewModel extends BaseViewModel {

    private MutableLiveData<Object> mKnowList;
    public ObservableField<Integer> mCidField;

    public KnowListViewModel(@NonNull Application application) {
        super(application);
        mCidField = new ObservableField<>();
    }

    public MutableLiveData<Object> getKnowList() {
        if (null == mKnowList) {
            mKnowList = new MutableLiveData<>();
        }
        return mKnowList;
    }

    @SuppressLint("CheckResult")
    public void initDataWithPage(int pageIndex) {
        KnowApiServices.getProjectList(pageIndex, mCidField.get())
                .compose(getLifecycleViewProvider().bindToDestroy())
                .compose(provideProgress())
                .subscribe(o -> mKnowList.postValue(o), throwable -> {});
    }
}
