package com.run.ui.activity

import android.content.Context
import android.content.Intent
import com.run.common.base.BaseActivity
import com.run.ui.R
import com.run.ui.fragment.MessageFragment
import kotlinx.android.synthetic.main.activity_message.*

class MessageActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, MessageActivity::class.java))
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_message
    }

    override fun initViews() {
        backView.setOnClickListener { finish() }
    }

    override fun initPresenter(): Nothing? {
        return null
    }

    override fun initData() {
        val tr = supportFragmentManager.beginTransaction()
        tr.add(R.id.messageLayout, MessageFragment.newInstance())
        tr.commit()
    }


}
