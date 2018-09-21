package com.run.common

import android.app.Application
import android.content.Context

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
    }

}
