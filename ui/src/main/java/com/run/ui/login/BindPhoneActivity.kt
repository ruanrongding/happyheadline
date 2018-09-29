package com.run.ui.login

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.run.common.base.BaseActivity
import com.run.presenter.contract.login.BindMobileContract
import com.run.ui.R

/**
 * 绑定手机
 */
class BindPhoneActivity : BaseActivity<BindMobileContract.BindMobilePresenter>(), BindMobileContract.BindMobileView {

    companion object {
        fun newInstance(context: Context) {
            val intent = Intent(context, BindPhoneActivity::class.java)
            context.startActivity(intent)
        }
    }
    override fun initContentView(): Int {
        return R.layout.activity_bind_phone
    }

    private lateinit var tv_code: TextView
    private lateinit var iv_delete_mobile: ImageView
    private lateinit var iv_delete_code: ImageView
    private lateinit var et_mobile: EditText
    private lateinit var et_code: EditText
    override fun initViews() {
        tv_code = findViewById(R.id.tv_code)
        iv_delete_mobile = findViewById(R.id.iv_delete_mobile)
        iv_delete_code = findViewById(R.id.iv_delete_code)
        et_mobile = findViewById(R.id.et_mobile)
        et_code = findViewById(R.id.et_code)
        initViewStatus()
        findViewById<View>(R.id.tv_back).setOnClickListener(this)
        findViewById<View>(R.id.tv_sure).setOnClickListener(this)
        iv_delete_mobile.setOnClickListener(this)
        iv_delete_code.setOnClickListener(this)
        tv_code.setOnClickListener(this)
    }

    /**
     * 设置view的状态
     */
    private fun initViewStatus() {
        et_mobile.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    iv_delete_mobile.visibility = View.VISIBLE
                    tv_code.isEnabled = true
                } else {
                    tv_code.isEnabled = false
                    iv_delete_mobile.visibility = View.GONE
                }
            }
        })
        et_code.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    iv_delete_code.visibility = View.VISIBLE
                } else {
                    iv_delete_code.visibility = View.GONE
                }
            }
        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_back -> finish()
            R.id.iv_delete_mobile -> et_mobile.setText("")
            R.id.iv_delete_code -> et_code.setText("")
            R.id.tv_sure -> doSure()
            R.id.tv_code -> getCode()
        }
    }

    override fun initPresenter(): BindMobileContract.BindMobilePresenter? {
        return BindMobileContract.BindMobilePresenter(this)
    }

    override fun initData() {
    }

    private var mMobile: String? = null
    /*
   * 获取验证码
   */
    private fun getCode() {
        mMobile = et_mobile.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(mMobile)) {
            showMsg("手机号不能为空！请检查")
            return
        }
        mPresenter!!.getCode(mMobile!!, 1)
    }

    override fun callBackfinish(msg: String) {
    }

    private var mCode: String? = null
    private fun doSure() {
        mMobile = et_mobile.text.toString().trim { it <= ' ' }
        mCode = et_code.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(mCode)) {
            showMsg("验证码不能为空！")
        }
        mPresenter!!.bindMobile(mMobile!!, mCode!!)
    }

    /**
     * 设置获取验证码按钮的状态
     */
    private var mTimer: CountDownTimer? = null

    override fun callBackCode() {
        showMsg("获取验证码成功！")
        mTimer = object : CountDownTimer((60 * 1000).toLong(), 1000) {
            override fun onTick(l: Long) {
                tv_code.isEnabled = false
                tv_code.text = "还剩" + l / 1000 + "s"
            }

            override fun onFinish() {
                tv_code.text = "获取验证码"
                tv_code.isEnabled = true
            }
        }
        (mTimer as CountDownTimer).start()
    }


    /**
     * 销毁验证码计时
     */
    override fun onDestroy() {
        super.onDestroy()
        if (mTimer != null) {
            mTimer!!.cancel()
            mTimer = null
        }
    }


}
