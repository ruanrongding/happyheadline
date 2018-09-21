package com.run.presenter.api;


import com.run.config.modle.BaseModle;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiService {

    /**
     * 数据激活*
     * @return
     */
    @GET("web//config/first")
    Observable<BaseModle> first(@Header("xytoken") String token, @Query("content") String content);


}
