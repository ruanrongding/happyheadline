package com.run.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.run.common.base.BaseActivity
import com.run.common.utils.UGlide
import com.run.presenter.contract.EditPersionContract
import com.run.presenter.modle.UserInfoModile
import com.run.ui.R
import com.run.ui.login.BindPhoneActivity
import kotlinx.android.synthetic.main.activity_edit_persion.*

/**
 * 用户编辑
 */
class EditPersionActivity : BaseActivity<EditPersionContract.EditPresionPresenter>(), EditPersionContract.EditPersionView {
    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, EditPersionActivity::class.java))
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_edit_persion
    }


    override fun initViews() {
        backView.setOnClickListener { finish() }
        mobileLayout.setOnClickListener { if (mobileView.text.length > 4) showMsg("您的账号已经绑定手机号码") else BindPhoneActivity.newInstance(this) }
        nickLayout.setOnClickListener { ModifyNumberActivity.newInstance(this, 0, ModifyNumberActivity.ModifyNick) }
        signerLayout.setOnClickListener { ModifyNumberActivity.newInstance(this, 1, ModifyNumberActivity.ModifySigner) }
        teacherLayout.setOnClickListener { if (apprenticeView.text.length > 4) showMsg("你的账号已经绑定过师傅") else ModifyNumberActivity.newInstance(this, 2, ModifyNumberActivity.BindTeacher) }
    }

    override fun initPresenter(): EditPersionContract.EditPresionPresenter? {
        return EditPersionContract.EditPresionPresenter(this)
    }

    override fun initData() {
        mPresenter!!.getUserInfo()
    }

    override fun callBackData(data: UserInfoModile.DataBean) {
        UGlide.loadCircleImage(this, data.head_avatar, headerView)
        nickView.text = if (TextUtils.isEmpty(data.nick_name)) "待设置" else data.nick_name
        sexView.text = if (data.gender == 1) "男" else "女"
        signerView.text = if (TextUtils.isEmpty(data.idiograph)) "这家伙很懒，什么都没写！" else data.idiograph
        apprenticeView.text = if (data.first_user_id == 0L) "待绑定" else data.first_user_id.toString()
        mobileView.text = if (TextUtils.isEmpty(data.mobile)) "待绑定" else data.mobile
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val msg = data!!.getStringExtra("MODIFYMSG")
            when (requestCode) {
                ModifyNumberActivity.ModifyNick -> nickView.text = msg
                ModifyNumberActivity.ModifySigner -> signerView.text = msg
                ModifyNumberActivity.BindTeacher -> apprenticeView.text = msg
            }
        }
    }

}
