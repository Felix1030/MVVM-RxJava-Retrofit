package com.felix.know.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;

import com.felix.apt.KnowApiServices;
import com.felix.base.http.RxSubscriber;
import com.felix.base.viewmodel.BaseViewModel;
import com.felix.know.model.KnowBaseModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class KnowViewModel extends BaseViewModel {

    private MutableLiveData<List<KnowBaseModel>> mKnowModels;

    public KnowViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<KnowBaseModel>> getKnowModels() {
        if (null == mKnowModels) {
            mKnowModels = new MutableLiveData<>();
        }
        return mKnowModels;
    }

    @SuppressLint("CheckResult")
    public void initKnowModels() {
        KnowApiServices.getProjectTree()
                .subscribeWith(new RxSubscriber<List<KnowBaseModel>>() {
                    @Override
                    protected void onSuccess(List<KnowBaseModel> knowBaseModels) {
                        mKnowModels.postValue(knowBaseModels);
                    }
                });
    }

}
