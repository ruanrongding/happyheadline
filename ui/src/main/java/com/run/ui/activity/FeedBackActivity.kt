package com.run.ui.activity

import android.content.Context
import android.content.Intent

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.run.common.base.BaseActivity
import com.run.common.utils.UInputManager
import com.run.presenter.LoginHelper
import com.run.presenter.contract.FeedBackContract
import com.run.ui.R
import kotlinx.android.synthetic.main.activity_feed_back.*

/**
 * 意见反馈
 */
class FeedBackActivity : BaseActivity<FeedBackContract.FeedBackPresenter>(), FeedBackContract.FeedBackView {

    companion object {
        fun newInstance(context: Context) {
            val intent = Intent(context, FeedBackActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_feed_back
    }


    override fun initViews() {
        backView.setOnClickListener { finish() }
        submitView.setOnClickListener {
            if (TextUtils.isEmpty(inputView.text)) {
                showMsg("内容不能为空,请编辑反馈内容！")
                return@setOnClickListener
            }
            mPresenter!!.feedBack("", inputView.text.toString(), LoginHelper.instance.getmMobile()!!)

        }
        inputView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                countView.text = s!!.length.toString() + "/100"
            }

        })
    }

    override fun initPresenter(): FeedBackContract.FeedBackPresenter? {
        return FeedBackContract.FeedBackPresenter(this)
    }

    override fun initData() {
    }

    override fun submitSucdess(msg: String) {
        UInputManager.closeKeybord(inputView, this)
        showMsg(msg)
        inputView.setText("")
    }
}
