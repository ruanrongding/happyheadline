package com.run.login.api


import com.run.common.BaseApplication
import com.run.common.utils.UEncrypt
import com.run.common.utils.URetrofit
import com.run.config.AppConstants
import com.run.config.AppConstants.DES_KEY
import com.run.config.modle.BaseModle
import com.run.presenter.LoginHelper
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.login.LoginModle
import com.run.presenter.modle.login.QQModle
import com.run.presenter.modle.share.ShareModle
import com.run.presenter.modle.share.ShareMsgModle
import io.reactivex.Observable
import org.json.JSONException
import org.json.JSONObject

object LoginManager {

    private var apiService: LoginService? = null//登录
    private val instance: LoginService
        get() {
            if (apiService == null) {
                synchronized(LoginService::class.java) {
                    if (apiService == null) {
                        apiService = URetrofit.getInstance(AppConstants.BASE_URL, BaseApplication.context!!)!!.create(LoginService::class.java)
                    }
                }
            }
            return this!!.apiService!!
        }


    /**
     * 登陆
     */
    fun verificationLogin(mobile: String, password: String): Observable<LoginModle> {
        val jsonObject = JSONObject()
        jsonObject.put("mobile", mobile)
        jsonObject.put("password", password)
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        return instance.verificationLogin(UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    /**
     * 微信登陆
     */
    fun loginWC(wechat_app_open_id: String, wechat_nick_name: String, head_avatar: String, gender: Int): Observable<LoginModle> {
        val jsonObject = JSONObject()
        jsonObject.put("wechat_app_open_id", wechat_app_open_id)
        jsonObject.put("wechat_nick_name", wechat_nick_name)
        jsonObject.put("head_avatar", head_avatar)
        jsonObject.put("gender", gender)
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        return instance.loginWC(UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    /**
     * 获取验证吗
     * @param mobile
     * @param type
     * @return
     */
    fun getCode(mobile: String, type: Int): Observable<BaseModle> {//type类型 1:注册 2：找回密码
        val jsonObject = JSONObject()
        try {
            jsonObject.put("mobile", mobile)
            jsonObject.put("type", type)
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return LoginManager.instance.getCode(UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 用户注册
     * @param mobile
     * @param code
     * @param password
     * @return
     */
    fun verifyRegister(mobile: String, code: String, password: String): Observable<BaseModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("nick_name", "")
            jsonObject.put("mobile", mobile)
            jsonObject.put("verification_code", code)
            jsonObject.put("password", password)
            jsonObject.put("confirm_password", password)
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return LoginManager.instance.verifyRegister(UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 重置密码
     */
    fun retrievePassword(mobile: String, code: String, password: String): Observable<BaseModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("mobile", mobile)
            jsonObject.put("verification_code", code)
            jsonObject.put("password", password)
            jsonObject.put("confirm_password", password)
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return LoginManager.instance.retrievePassword(UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    /**
     * 绑定手机
     */
    fun binding_phone(mobile: String, verification_code: String): Observable<BaseModle> {//type类型 1:注册 2：找回密码
        val jsonObject = JSONObject()
        jsonObject.put("mobile", mobile)
        jsonObject.put("verification_code", verification_code)
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)


        return instance.binding_phone(LoginHelper.instance.getmToken()!!, UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 是否绑定手机
     */
    fun detection_phone(): Observable<BaseModle> {
        return instance.detection_phone(LoginHelper.instance.getmToken()!!, "")
    }


    /**
     * 退出登录
     *
     * @return
     */
    fun logout(): Observable<BaseModle> {
        val jsonObject = JSONObject()
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        return LoginManager.instance.logout(LoginHelper.instance.getmToken()!!, UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    /**
     * 拜师
     */
    fun bound_teacher(first_id: String): Observable<BaseModle> {
        val jsonObject = JSONObject()
        jsonObject.put("first_id", first_id)
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        return instance.bound_teacher(LoginHelper.instance.getmToken()!!, UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 修改昵称
     */
    fun user_save(user_name: String, user_alipay: String, real_name: String, idiograph: String): Observable<BaseModle> {
        val jsonObject = JSONObject()
        jsonObject.put("user_name", user_name)
        jsonObject.put("user_alipay", user_alipay)
        jsonObject.put("idiograph", idiograph)
        jsonObject.put("real_name", real_name)
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        return instance.user_save(LoginHelper.instance.getmToken()!!, UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 获取qq的key
     *
     * @return
     */
    fun getQQKey(): Observable<QQModle> {
        val jsonObject = JSONObject()
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        return instance.getQQKey(LoginHelper.instance.getmToken()!!, UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 意见反馈
     *
     * @param title
     * @param content
     * @param phone
     * @return
     */
    fun feedback(title: String, content: String, phone: String): Observable<BaseModle> {
        val jsonObject = JSONObject()
        jsonObject.put("title", title)
        jsonObject.put("content", content)
        jsonObject.put("phone", phone)
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)


        return instance.feedback(LoginHelper.instance.getmToken()!!, UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    //==================================================分享===========================================================================
    /**
     * 获取分享内容
     */
    fun share_record(article_id: Int, share_type: Int): Observable<ShareModle> {
        val jsonObject = JSONObject()
        jsonObject.put("details_id", article_id)
        jsonObject.put("share_type", share_type)
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        return instance.share_record(LoginHelper.instance.getmToken()!!, UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    fun share_msg(): Observable<ShareMsgModle> {
        return instance.share_msg(LoginHelper.instance.getmToken()!!, "")
    }


    /**
     * 举报
     */
    fun add_complaints(details_id: Int, content: String): Observable<BaseModle> {
        val jsonObject = JSONObject()
        jsonObject.put("details_id", details_id)
        jsonObject.put("content", content)
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        return instance.add_complaints(LoginHelper.instance.getmToken()!!, UEncrypt.encrypt_AES(jsonObject.toString(), DES_KEY))
    }


    /**
     * 收藏
     */
    fun details_collect(details_id: Int): Observable<BaseModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("details_id", details_id)
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return instance.details_collect(LoginHelper.instance.getmToken()!!, UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }
}
