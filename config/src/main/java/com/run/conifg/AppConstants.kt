package com.run.config

/**
 * App的常量配置
 */
public object AppConstants {

    /**
     * 是否打印日志
     */
    const val DebugLog = true
    /**
     * 网络请求地址
     */
    const val BASE_URL = "http://hltt.huanleap.com/"
    /**
     * des加密的key
     */
    const val DES_KEY = "2V8uzwuvLTzWcZ6C"
    /**
     * 配置渠道号
     */
    const val CHANNEL_KEY = 1

    /**
     * 微信账号相关
     */
    val WC_APPID = "wx1a366fae4656d730"
    val WC_AppSecret = "ea8f74f5cdb68ed8661e839c3a37faea"

    val BASE_WC_URL = "https://api.weixin.qq.com/sns/"

}
