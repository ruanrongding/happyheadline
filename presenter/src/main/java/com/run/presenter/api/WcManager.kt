package com.run.login.api

import com.run.common.BaseApplication
import com.run.common.utils.URetrofit
import com.run.config.AppConstants
import com.run.presenter.api.WcService
import com.run.presenter.modle.login.WCModle
import com.run.presenter.modle.login.WCTokenModle
import com.run.presenter.modle.login.WCUser
import io.reactivex.Observable

/**
 * 微信登录相关操作类
 */
object WcManager {
    private var wcService: WcService? = null//登录
    private val instance: WcService
        get() {
            if (wcService == null) {
                synchronized(LoginService::class.java) {
                    if (wcService == null) {
                        wcService = URetrofit.getInstance(AppConstants.BASE_WC_URL, BaseApplication.context!!)!!.create(WcService::class.java)
                    }
                }
            }
            return this.wcService!!
        }

    /**
     * 获取appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
     */
    fun getOpenid(appid: String, secret: String, code: String): Observable<WCModle> {
        return instance.getOpenid(appid, secret, code, "authorization_code")
    }

    /**
     * 获取用户信息
     */
    fun userinfo(access_token: String, openid: String): Observable<WCUser> {
        return instance.userinfo(access_token, openid)
    }

    /**
     * 刷新token
     */
    fun refresh_token(appid: String, refresh_token: String): Observable<WCTokenModle> {
        return instance.refresh_token(appid, "refresh_token", refresh_token)
    }

}
