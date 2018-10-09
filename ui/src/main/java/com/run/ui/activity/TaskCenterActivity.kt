package com.run.ui.activity

import android.content.Context
import android.content.Intent
import com.run.common.base.BaseActivity
import com.run.ui.R
import com.run.ui.fragment.ArticleFragment
import com.run.ui.fragment.TaskFragment
import kotlinx.android.synthetic.main.activity_task_center.*


/**
 * 任务中心
 */
class TaskCenterActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, TaskCenterActivity::class.java))
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_task_center
    }

    override fun initViews() {
        backView.setOnClickListener { finish() }
    }

    override fun initPresenter(): Nothing? {
        return null
    }

    override fun initData() {
        val transation = supportFragmentManager.beginTransaction()
        transation.add(R.id.collectLayout, TaskFragment.newInstance())
        transation.commit()
    }


}
