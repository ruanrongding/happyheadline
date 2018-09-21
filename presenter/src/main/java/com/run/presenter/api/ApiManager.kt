package com.run.presenter.api


import com.run.common.BaseApplication
import com.run.common.utils.UEncrypt
import com.run.common.utils.URetrofit
import com.run.config.AppConstants
import com.run.config.modle.BaseModle
import com.run.presenter.LoginHelper
import io.reactivex.Observable
import org.json.JSONObject

object ApiManager {

    private var apiService: ApiService? = null
    private val instance: ApiService
        get() {
            if (apiService == null) {
                synchronized(ApiManager::class.java) {
                    if (apiService == null) {
                        apiService = URetrofit.getInstance(AppConstants.BASE_URL, BaseApplication.context!!)!!.create(ApiService::class.java)
                    }
                }
            }
            return this!!.apiService!!
        }

    /**
     * 记录激活数据
     */
    fun first(imei: String): Observable<BaseModle> {
        val jsonObject = JSONObject()
        jsonObject.put("imei", imei)
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        return ApiManager.instance.first(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

}
