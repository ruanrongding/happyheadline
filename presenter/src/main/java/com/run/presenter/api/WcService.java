package com.run.presenter.api;


import com.run.presenter.modle.login.WCModle;
import com.run.presenter.modle.login.WCTokenModle;
import com.run.presenter.modle.login.WCUser;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 获取微信信息接口
 */
public interface WcService {
    /**
     * 获取微信登录的access_token
     */
    @GET("oauth2/access_token?")
    Observable<WCModle> getOpenid(@Query("appid") String appid, @Query("secret") String secret, @Query("code") String code, @Query("grant_type") String grant_type);

    /**
     * 获取用户信息
     */
    @GET("userinfo?")
    Observable<WCUser> userinfo(@Query("access_token") String access_token, @Query("openid") String openid);

    /**
     * 刷新tooken
     */
    @GET("oauth2/refresh_token?")
    Observable<WCTokenModle> refresh_token(@Query("appid") String appid, @Query("grant_type") String grant_type, @Query("refresh_token") String refresh_token);


}
