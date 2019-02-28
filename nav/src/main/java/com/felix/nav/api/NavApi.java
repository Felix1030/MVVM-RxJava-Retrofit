package com.felix.nav.api;

import com.felix.annotation.ApiAnnotation;
import com.felix.base.http.BaseResponse;
import com.felix.nav.model.NavModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;

@ApiAnnotation
public interface NavApi {

    /**
     * http://www.wanandroid.com/navi/json
     * @return 获取导航页所有数据
     */
    @GET("navi/json")
    Flowable<BaseResponse<List<NavModel>>> getNavis();

}
