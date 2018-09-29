package com.run.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.run.common.base.BaseActivity
import com.run.presenter.contract.ModifyNumberContract
import com.run.ui.R
import kotlinx.android.synthetic.main.activity_modify_number.*

/**
 * 修改用户信息
 */
class ModifyNumberActivity : BaseActivity<ModifyNumberContract.ModifyNumberPresenter>(), ModifyNumberContract.ModifyNumberView {


    companion object {
        fun newInstance(context: Activity, type: Int, requestCode: Int) {
            var intent = Intent(context, ModifyNumberActivity::class.java)
            intent.putExtra("TYPE", type)
            context.startActivityForResult(intent, requestCode)
        }

        const val ModifyNick = 212  //修改昵称
        const val ModifySigner = 213 //修改签名
        const val BindTeacher = 214  //绑定师傅ID
    }

    override fun initContentView(): Int {
        return R.layout.activity_modify_number
    }

    override fun initViews() {
        inputView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                countView.text = s!!.length.toString() + "/50"
            }
        })
        submitView.setOnClickListener {
            inputMsg = inputView.text.toString()
            if (TextUtils.isEmpty(inputMsg)) {
                showMsg("修改的内容不能为空！")
                return@setOnClickListener
            }
            when (mType) {
                0 -> mPresenter!!.modifyNick(inputMsg)
                1 -> mPresenter!!.modifySigner(inputMsg)
                2 -> mPresenter!!.modifyTeacher(inputMsg)
            }
        }
        backView.setOnClickListener { finish() }
    }

    private lateinit var inputMsg: String
    override fun initPresenter(): ModifyNumberContract.ModifyNumberPresenter? {
        return ModifyNumberContract.ModifyNumberPresenter(this)
    }

    private var mType: Int = 0
    override fun initData() {
        mType = intent.getIntExtra("TYPE", 0)
        when (mType) {
            0 -> {
                inputView.hint = "请输入新昵称"
                titleView.text = "修改昵称"
            }
            1 -> {
                inputView.hint = "请输入个性签名"
                titleView.text = "修改签名"
            }
            2 -> {
                inputView.hint = "请输入师傅ID"
                inputView.inputType = InputType.TYPE_CLASS_NUMBER
                titleView.text = "绑定师傅"
                msgView.visibility = View.VISIBLE

            }
        }
    }

    override fun callBackFinish(msg: String) {
        val intent = Intent()
        intent.putExtra("MODIFYMSG", inputMsg)
        setResult(RESULT_OK, intent)
        finish()

    }


}
