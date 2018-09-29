package com.run.presenter.api;


import com.run.config.modle.BaseModle;
import com.run.presenter.modle.ArticleDetailModle;
import com.run.presenter.modle.ArticleModle;
import com.run.presenter.modle.ArticleTitleModle;
import com.run.presenter.modle.CircleModle;
import com.run.presenter.modle.UserInfoModile;
import com.run.presenter.modle.UserModle;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiService {

    /**
     * 数据激活*
     *
     * @return
     */
    @GET("web//config/first")
    Observable<BaseModle> first(@Header("xytoken") String token, @Query("content") String content);


    /**
     * 一周和一个月 最火
     */
    @GET("web/Article/seniority_details")
    Observable<ArticleModle> seniority_details(@Header("xytoken") String token, @Query("content") String content);


    /**
     * 今日推荐
     */
    @GET("web/Article/recommend_day")
    Observable<ArticleModle> recommend_day(@Header("xytoken") String token, @Query("content") String content);


    /**
     * 获取精华
     */
    @GET("web/Article/essence")
    Observable<ArticleModle> articleessence(@Header("xytoken") String token, @Query("content") String content);


    /**
     * 获取分类文章列表
     */
    @GET("web/Article/lists")
    Observable<ArticleModle> articlelist(@Header("xytoken") String token, @Query("content") String content);

    /**
     * 发现最新
     */
    @GET("web/article/discover_newest")
    Observable<ArticleModle> discover_newest(@Header("xytoken") String token, @Query("content") String content);

    /**
     * 发现热门
     */
    @GET("web/article/discover_hot")
    Observable<ArticleModle> discover_hot(@Header("xytoken") String token, @Query("content") String content);


    /*
     *个人中心-帖子
     */
    @GET("web/User/my_article")
    Observable<ArticleModle> my_article(@Header("xytoken") String token, @Query("content") String content);

    /*
     *分享帖子
     */
    @GET("web/User/ym_share")
    Observable<ArticleModle> ym_share(@Header("xytoken") String token, @Query("content") String content);

    //所属圈子文章
    @GET("web/circle/article")
    Observable<ArticleModle> circle_article(@Header("xytoken") String token, @Query("content") String content);


    /**
     * 获取栏目分类
     */
    @GET("web/Article/index")
    Observable<ArticleTitleModle> article(@Header("xytoken") String token, @Query("content") String content);


    /**
     * 获取文章详情
     */
    @GET("web/Article/details")
    Observable<ArticleDetailModle> articledetail(@Header("xytoken") String token, @Query("content") String content);


    //圈子
    @GET("web/circle/circle_list")
    Observable<CircleModle> circle_list(@Header("xytoken") String token, @Query("content") String content);

    //关注的圈子
    @GET("web/circle/circle_attention_list")
    Observable<CircleModle> circle_attention_list(@Header("xytoken") String token, @Query("content") String content);


    //==================================用户首页信息=================================================

    /**
     * 用户信息
     */
    @GET("web/user/index")
    Observable<UserModle> index(@Header("xytoken") String token, @Query("content") String content);


    /**
     * 个人中心 – 用户设置
     */
    @GET("web/user/user_info")
    Observable<UserInfoModile> user_info(@Header("xytoken") String token, @Query("content") String content);



}
