package com.run.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.run.common.base.BaseActivity
import com.run.ui.R
import com.run.ui.fragment.ArticleFragment
import kotlinx.android.synthetic.main.activity_problem.*

class CollectListActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(activity: Context) {
            activity.startActivity(Intent(activity, CollectListActivity::class.java))
        }
    }


    override fun initContentView(): Int {
        return R.layout.activity_collect_list
    }

    override fun initViews() {
        backView.setOnClickListener { finish() }
    }

    override fun initPresenter(): Nothing? {
        return null
    }

    @SuppressLint("CommitTransaction")
    override fun initData() {
        val transation = supportFragmentManager.beginTransaction()
        transation.add(R.id.collectLayout, ArticleFragment.newInstance("collect_list"))
        transation.commit()

    }


}
