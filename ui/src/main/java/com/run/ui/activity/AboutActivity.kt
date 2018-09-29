package com.run.ui.activity

import android.content.Context
import android.content.Intent
import com.run.common.base.BaseActivity
import com.run.common.utils.UVersion
import com.run.ui.R
import kotlinx.android.synthetic.main.activity_about.*

/**
 * 关于我们
 */
class AboutActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, AboutActivity::class.java))
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_about
    }

    override fun initViews() {
        backView.setOnClickListener { finish() }
    }

    override fun initPresenter(): Nothing? {
        return null
    }

    override fun initData() {
        versionView.text = "当前版本： v" + UVersion.getLocalVersionName(this)

    }


}
