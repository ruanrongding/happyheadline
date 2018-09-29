package com.run.common

import android.app.Application
import android.content.Context
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        initData()
    }

    /**
     * 初始化数据
     */
    protected open fun initData() {

    }

    companion object {
        var context: Context? = null
            private set

        lateinit var mWxApi: IWXAPI
    }


    //===================================添加应用到微信==========================================

    protected fun registerToWC(WC_APPID: String) {
        //第二个参数是指你应用在微信开放平台上的AppID
        mWxApi = WXAPIFactory.createWXAPI(this, WC_APPID, false)
        // 将该app注册到微信
        mWxApi.registerApp(WC_APPID)
    }

}
