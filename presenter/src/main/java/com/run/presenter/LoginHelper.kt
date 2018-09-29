package com.run.presenter

import android.content.Context
import android.text.TextUtils
import com.run.common.BaseApplication
import com.run.common.base.BaseObserver
import com.run.common.utils.URxBus
import com.run.common.utils.USharePreference
import com.run.config.AppConstants
import com.run.conifg.AppActivity
import com.run.conifg.RxBusConfig
import com.run.conifg.modle.RxBean
import com.run.login.api.LoginManager
import com.run.login.api.WcManager
import com.run.presenter.modle.login.LoginModle
import com.tencent.mm.opensdk.modelmsg.SendAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginHelper private constructor() {

    private var mMobile: String? = null
    private var mPassword: String? = null
    private var mToken: String? = null

    /**
     * 检查是否是否登录
     */
    val isLogin: Boolean
        get() = !TextUtils.isEmpty(getmToken())

    private var mContext: Context? = null

    fun setmToken(mToken: String) {
        this.mToken = mToken
        //清空Token
        USharePreference.put(BaseApplication.context!!, "TOKEN", mToken)
    }

    fun getmMobile(): String? {
        if (TextUtils.isEmpty(mMobile)) {
            mMobile = USharePreference[BaseApplication.context!!, "MOBILE", ""] as String?
        }
        return mMobile
    }

    fun getmPassword(): String? {
        if (TextUtils.isEmpty(mPassword)) {
            mPassword = USharePreference[BaseApplication.context!!, "PASSWORD", ""] as String?
        }
        return mPassword
    }

    fun getmToken(): String? {
        if (TextUtils.isEmpty(mToken)) {
            mToken = USharePreference[BaseApplication.context!!, "TOKEN", ""] as String?
        }
        return mToken
    }

    /**
     * 保存登陆信息
     */
    fun save(mobile: String, password: String, token: String) {
        USharePreference.put(BaseApplication.context!!, "MOBILE", mobile)
        USharePreference.put(BaseApplication.context!!, "PASSWORD", password)
        if (TextUtils.isEmpty(token)) {
            return
        }
        USharePreference.put(BaseApplication.context!!, "TOKEN", token)
    }

    fun isLogin(context: Context): Boolean {
        val isLogin = !TextUtils.isEmpty(getmToken())
        if (!isLogin) {
            showLogin(context)
        }
        return isLogin
    }

    /**
     * 打开登录弹窗
     */
    fun showLogin(context: Context?) {
        if (context == null) return
        this.mContext = context
        AppActivity.openLogin(mContext)
    }

    companion object {
        private val TAG: String = LoginHelper.javaClass.name
        private var loginHelper: LoginHelper? = null
        val instance: LoginHelper
            get() {
                if (loginHelper == null) {
                    synchronized(LoginHelper::class.java) {
                        if (loginHelper == null) {
                            loginHelper = LoginHelper()
                        }
                    }
                }
                return loginHelper!!
            }
    }

    //执行微信登录
    fun doWCLogin(openid: String, nick: String, heard: String, gender: Int) {
        LoginManager.loginWC(openid, nick, heard, gender).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserver<LoginModle>() {
                    override fun onError(errorType: Int, msg: String?) {
                        URxBus.get().post(RxBean<Nothing>(RxBusConfig.LoginConfig.Login_Type, RxBusConfig.LoginConfig.Fali_Code))
                    }
                    override fun onSuccess(o: LoginModle) {
                        setmToken(o.token!!)
                        if(o.first_tyoe == 1){
                            URxBus.get().post(RxBean<Nothing>(RxBusConfig.LoginConfig.Login_Type, RxBusConfig.LoginConfig.BindMobile_Code))
                        }else{
                            URxBus.get().post(RxBean<Nothing>(RxBusConfig.LoginConfig.Login_Type, RxBusConfig.LoginConfig.Success_Code))
                        }

                    }

                })
    }


    //======================================================微信登录=================================
    /**
     * 微信登录
     */
    fun wxLogin() {
        val req = SendAuth.Req()
        req.scope = "snsapi_userinfo"
        req.state = "xinyun_wx_login"
        //像微信发送请求
        BaseApplication.mWxApi.sendReq(req)
    }


    fun getOpenID(code: String) {
        if (TextUtils.isEmpty(code)) return
        WcManager.getOpenid(AppConstants.WC_APPID, AppConstants.WC_AppSecret, code)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    if (it.errcode == 40163) {
                        refreshAccessToken()
                        return@subscribe
                    }
                    getUserInfo(it.access_token!!, it.openid!!)
                    USharePreference.put(BaseApplication.context!!, "REFRESH_TOKEN", it.refresh_token!!)
                }
    }

    /**
     * 刷新
     */
    private fun refreshAccessToken() {
        val refreshToken = USharePreference[BaseApplication.context!!, "REFRESH_TOKEN", ""] as String
        if (TextUtils.isEmpty(refreshToken)) return
        WcManager.refresh_token(AppConstants.WC_APPID, refreshToken)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    getUserInfo(it.access_token!!, it.openid!!)
                }

    }

    /**
     * 获取用户信息
     */
    fun getUserInfo(access_token: String, openid: String) {
        WcManager.userinfo(access_token, openid)
                .subscribeOn(Schedulers.io())
                .subscribe {

                    doWCLogin(it.openid!!, it.nickname!!, it.headimgurl!!, it.sex)
                }
    }


}
