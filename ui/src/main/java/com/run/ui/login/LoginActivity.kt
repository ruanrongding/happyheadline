package com.run.ui.login

import android.view.View
import com.run.common.base.BaseActivity
import com.run.common.utils.URxBus
import com.run.conifg.RxBusConfig
import com.run.conifg.modle.RxBean
import com.run.presenter.LoginHelper
import com.run.ui.R


/**
 * 选择登录(手机号登录/微信登录)
 */
class LoginActivity : BaseActivity<Nothing>() {

    override fun initContentView(): Int {
        return R.layout.activity_login
    }

    override fun initViews() {
        findViewById<View>(R.id.wcLoginView).setOnClickListener {
            showLoading()
            LoginHelper.instance.wxLogin()
        }
        findViewById<View>(R.id.mobileLoginView).setOnClickListener {
            MobileLoginActivity.newInstance(this)
            finish()
        }
        findViewById<View>(R.id.backView).setOnClickListener { finish() }
    }

    override fun initPresenter(): Nothing? {
        return null
    }

    override fun initData() {
        initRxBack()
    }


    /**
     * 初始化RxBack
     */
    private fun initRxBack() {
        URxBus.get().toFlowable().map { o -> o as RxBean<*> }.subscribe {
            if (it.type == RxBusConfig.LoginConfig.Login_Type) {
                hideLoading()
                when (it.code) {
                    RxBusConfig.LoginConfig.Success_Code -> {
                        showMsg("登录成功！")
                        finish()
                    }
                    RxBusConfig.LoginConfig.Fali_Code -> {
                        showMsg("登录失败！")
                    }
                    RxBusConfig.LoginConfig.BindMobile_Code -> {
                        BindPhoneActivity.newInstance(this)
                        finish()
                    }
                }
            }
        }
    }

}
