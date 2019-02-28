package com.felix.know.api;

import com.felix.annotation.ApiAnnotation;
import com.felix.base.http.BaseResponse;
import com.felix.know.model.KnowBaseModel;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

@ApiAnnotation
public interface KnowApi {

    /**
     * http://www.wanandroid.com/project/tree/json
     * @return 获取项目列表
     */
    @GET("project/tree/json")
    Flowable<BaseResponse<List<KnowBaseModel>>> getProjectTree();

    /**
     * 项目列表数据
     * http://www.wanandroid.com/project/list/1/json?cid=294
     * @param pageIndex
     * @param cId
     */
    @GET("project/list/{pageIndex}/json")
    Flowable<BaseResponse<Object>> getProjectList(@Path("pageIndex")int pageIndex, @Query("cid") int cId);
}
