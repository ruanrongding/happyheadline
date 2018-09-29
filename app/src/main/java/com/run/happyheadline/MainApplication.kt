package com.run.happyheadline

import com.run.common.BaseApplication
import com.run.config.AppConstants
import com.umeng.socialize.Config
import com.umeng.socialize.PlatformConfig
import com.umeng.socialize.UMShareAPI

class MainApplication : BaseApplication() {

    override fun initData() {
        initShare()
        //注册到微信
        registerToWC(AppConstants.WC_APPID)
    }

    /**
     * 初始化分享
     */
    private fun initShare() {
        PlatformConfig.setWeixin("wx07f7a310c8bb9537", "aa798e4fc45a0dfa878500492a7ef656")
        //TODO 代申请
        UMShareAPI.get(this)
        Config.DEBUG = true
    }
}