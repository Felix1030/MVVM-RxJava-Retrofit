package com.felix.main.api;

import com.felix.annotation.ApiAnnotation;
import com.felix.base.http.BaseResponse;
import com.felix.main.model.ArticleBaseModel;
import com.felix.main.model.ArticleModel;
import com.felix.main.model.BannerModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

@ApiAnnotation
public interface MainApi {
    /**
     * 获取轮播图
     * http://www.wanandroid.com/banner/json
     */
    @GET("banner/json")
    Flowable<BaseResponse<List<BannerModel>>> getBanners();

    /**
     * 首页文章列表
     * http://www.wanandroid.com/article/list/0/json
     */
    @GET("article/list/{pageIndex}/json")
    Flowable<BaseResponse<ArticleBaseModel>> getArticles(@Path("pageIndex")int pageIndex);

    /**
     * 获取首页置顶文章列表
     * http://www.wanandroid.com/article/top/json
     */
    @GET("article/top/json")
    Flowable<BaseResponse<List<ArticleModel>>> getTopArticles();
}
