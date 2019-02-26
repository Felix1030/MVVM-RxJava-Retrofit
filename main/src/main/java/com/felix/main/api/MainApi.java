package com.felix.main.api;

import com.felix.annotation.ApiAnnotation;
import com.felix.base.http.BaseResponse;
import com.felix.main.model.BannerModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;

@ApiAnnotation
public interface MainApi {
    /**
     * 获取轮播图
     * http://www.wanandroid.com/banner/json
     */
    @GET("banner/json")
    Flowable<BaseResponse<List<BannerModel>>> getBanners();
}
