package com.run.ui.activity

import android.content.Context
import android.content.Intent
import com.run.common.base.BaseActivity
import com.run.ui.MainActivity
import com.run.ui.R
import kotlinx.android.synthetic.main.activity_with_draw_result.*

/**
 * 提现结果Activity
 */
class WithDrawResultActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(context: Context, type: Int, msg: String) {
            val intent = Intent(context, WithDrawResultActivity::class.java)
            intent.putExtra("TYPE", type)
            intent.putExtra("MESSAGE", msg)
            context.startActivity(intent)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_with_draw_result
    }

    override fun initViews() {
        backView.setOnClickListener { finish() }
        mainView.setOnClickListener { MainActivity.newInstance(this) }
        withDrawView.setOnClickListener { WithDrawActivity.newInstance(this) }
    }

    override fun initPresenter(): Nothing? {
        return null
    }

    private var mType: Int? = 0
    private lateinit var msg: String
    override fun initData() {
        mType = intent.getIntExtra("TYPE", 0)
        msg = intent.getStringExtra("MESSAGE")
        when (mType) {
            0 -> {
                titleView.text = "提现成功"
                withDrawImageView.setImageResource(R.mipmap.wts)
                messageView.text = "申请提现成功,请耐心等待"
            }
            1 -> {
                titleView.text = "提现失败"
                withDrawImageView.setImageResource(R.mipmap.tf)
                messageView.text = msg
            }
        }
    }


}
