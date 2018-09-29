package com.run.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import com.run.common.base.BaseActivity
import com.run.common.dialog.ClearCacherDialog
import com.run.common.dialog.DialogCallBack
import com.run.common.utils.UCache
import com.run.common.utils.UVersion
import com.run.presenter.contract.SettingContract
import com.run.ui.MainActivity
import com.run.ui.R

/**
 * 设置中心
 */
class SettingActivity : BaseActivity<SettingContract.SettingPresenter>(), SettingContract.SettingView {

    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, SettingActivity::class.java))
        }
    }
    override fun initContentView(): Int {
        return R.layout.activity_setting
    }
    private lateinit var versionView: TextView
    private lateinit var cacheView: TextView
    override fun initViews() {
        cacheView = findViewById(R.id.cacheView)
        versionView = findViewById(R.id.versionView)
        findViewById<View>(R.id.backView).setOnClickListener(this)
        findViewById<View>(R.id.logoutView).setOnClickListener(this)
        findViewById<View>(R.id.editLayout).setOnClickListener(this)
        findViewById<View>(R.id.cacheLayout).setOnClickListener(this)
        findViewById<View>(R.id.versiionLayout).setOnClickListener(this)
        findViewById<View>(R.id.aboutLayout).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.backView -> finish()
            R.id.logoutView -> mPresenter!!.logout()
            R.id.cacheLayout -> showClearCacheDialog()
            R.id.aboutLayout -> AboutActivity.newInstance(this)
            R.id.editLayout -> EditPersionActivity.newInstance(this)
        }
    }

    override fun initPresenter(): SettingContract.SettingPresenter? { return SettingContract.SettingPresenter(this) }

    override fun initData() {
        var cache = "0M"
        try {
            cache = UCache.getTotalCacheSize(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        cacheView.text = cache
        versionView.text = "v" + UVersion.getLocalVersionName(this)
    }

    private fun showClearCacheDialog() {
        ClearCacherDialog.newInstance(this@SettingActivity).show(this@SettingActivity, callBack = object : DialogCallBack {
            override fun onNext() {
                UCache.clearAllCache(this@SettingActivity)
                cacheView.text = "0KB"
            }
            override fun cancle() {
            }
        })
    }

    override fun callBackLogout(msg: String) {
        showMsg(msg)
        MainActivity.newInstance(this)
    }


}
