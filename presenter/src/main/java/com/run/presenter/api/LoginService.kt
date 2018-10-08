package com.run.login.api

import com.run.config.modle.BaseModle
import com.run.presenter.modle.login.LoginModle
import com.run.presenter.modle.login.QQModle
import com.run.presenter.modle.share.ShareModle
import com.run.presenter.modle.share.ShareMsgModle
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


/**
 * 登录接口
 */
interface LoginService {

    //=========================================登录======================================

    /**
     * 微信登录
     */
    @GET("web/Oauth/verify?")
    abstract fun loginWC(@Query("content") content: String): Observable<LoginModle>


    /**
     * 登录
     */
    @GET("web/login/verification_login?")
    fun verificationLogin(@Query("content") content: String): Observable<LoginModle>


    //获取验证码接口
    @GET("web/register/code?")
    fun getCode(@Query("content") content: String): Observable<BaseModle>


    /**
     * 用户注册
     *
     * @param content
     * @return
     */
    @GET("web/register/verifyRegister?")
    fun verifyRegister(@Query("content") content: String): Observable<BaseModle>


    /**
     * 重置密码
     *
     * @param content
     * @return
     */
    @GET("web/Reset/retrieve_password")
    fun retrievePassword(@Query("content") content: String): Observable<BaseModle>


    /**
     * 绑定手机
     */
    @GET("web/User/binding_phone")
    abstract fun binding_phone(@Header("xytoken") token: String, @Query("content") content: String): Observable<BaseModle>

    /**
     * 是否绑定手机
     */
    @GET("web/User/detection_phone")
    abstract fun detection_phone(@Header("xytoken") token: String, @Query("content") content: String): Observable<BaseModle>


    /**
     * 个人中心 – 退出登录
     * @param content
     * @return
     */
    @GET("web/login/logout")
    fun logout(@Header("xytoken") token: String, @Query("content") content: String): Observable<BaseModle>


    /**
     * 保存资料
     */
    @GET("web/User/user_save")
    abstract fun user_save(@Header("xytoken") token: String, @Query("content") content: String): Observable<BaseModle>


    /**
     * 拜师操作
     */
    @GET("/web/User/bound_teacher")
    abstract fun bound_teacher(@Header("xytoken") token: String, @Query("content") content: String): Observable<BaseModle>


    /**
     * 获取QQEKY值
     *
     * @param token
     * @param content
     * @return
     */
    @GET(" web/config/getqq")
    fun getQQKey(@Header("xytoken") token: String, @Query("content") content: String): Observable<QQModle>


    /**
     * 意见反馈
     *
     * @param content
     * @return
     */
    @GET("/web/User/feedback")
    fun feedback(@Header("xytoken") token: String, @Query("content") content: String): Observable<BaseModle>



    //===========================================分享======================================================================
    /**
     * 获取分享内容
     */
    @GET("web/Article/share_record")
    abstract fun share_record(@Header("xytoken") token: String, @Query("content") content: String): Observable<ShareModle>


    /**
     * 获取分享标题
     */
    @GET("web/config/share_msg")
    abstract fun share_msg(@Header("xytoken") token: String, @Query("content") content: String): Observable<ShareMsgModle>


    /**
     * 举报
     */
    @GET("web/user/add_complaints")
    abstract fun add_complaints(@Header("xytoken") token: String, @Query("content") content: String): Observable<BaseModle>


    /**
     * 收藏内容
     */
    @GET("web/User/details_collect")
    abstract fun details_collect(@Header("xytoken") token: String, @Query("content") content: String): Observable<BaseModle>


}
