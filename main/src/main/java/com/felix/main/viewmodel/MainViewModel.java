package com.felix.main.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;

import com.felix.apt.MainApiServices;
import com.felix.base.viewmodel.BaseViewModel;
import com.felix.main.model.ArticleBaseModel;
import com.felix.main.model.ArticleModel;
import com.felix.main.model.BannerModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Flowable;

public class MainViewModel extends BaseViewModel {

    private MutableLiveData<List<BannerModel>> mBanner;
    private MutableLiveData<ArticleBaseModel> mArticleBaseModel;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<BannerModel>> getBanner() {
        if (null == mBanner) {
            mBanner = new MutableLiveData<>();
        }
        return mBanner;
    }

    public MutableLiveData<ArticleBaseModel> getArticleModel() {
        if (null == mArticleBaseModel) {
            mArticleBaseModel = new MutableLiveData<>();
        }
        return mArticleBaseModel;
    }

    @SuppressLint("CheckResult")
    public void initBanners() {
        MainApiServices.getBanners()
                .compose(getLifecycleViewProvider().bindToDestroy())
                .subscribe(bannerModels -> getBanner().postValue(bannerModels), throwable -> {});
    }

    @SuppressLint("CheckResult")
    public void initAllArticles() {
        Flowable.zip(MainApiServices.getTopArticles(), MainApiServices.getArticles(0), (articleModels, articleBaseModel) -> {
            for (ArticleModel articleModel : articleModels) {
                articleModel.setTop(String.valueOf(1)); // 手动设置置顶
            }
            articleBaseModel.getDatas().addAll(0,articleModels);
            return articleBaseModel;
        }).subscribe(mArticleBaseModel::postValue, throwable -> {});
    }

}
