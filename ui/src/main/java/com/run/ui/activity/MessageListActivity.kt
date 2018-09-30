package com.run.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DialogTitle
import com.run.common.base.BaseActivity
import com.run.ui.R
import com.run.ui.fragment.MessageFragment
import com.run.ui.fragment.MsgListFragment
import kotlinx.android.synthetic.main.activity_message.*

class MessageListActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(context: Context, msgid: Int, title: String) {
            val intent = Intent(context, MessageListActivity::class.java)
            intent.putExtra("msgid", msgid)
            intent.putExtra("title", title)
            context.startActivity(intent)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_message_list
    }

    override fun initViews() {
        backView.setOnClickListener { finish() }
    }

    override fun initPresenter(): Nothing? {
        return null
    }

    override fun initData() {
        titleView.text = intent.getStringExtra("title")
        val msgid = intent.getIntExtra("msgid", 0)
        val tr = supportFragmentManager.beginTransaction()
        tr.add(R.id.messageLayout, MsgListFragment.newInstance(msgid))
        tr.commit()
    }


}
