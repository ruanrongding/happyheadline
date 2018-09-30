package com.run.ui.activity

import android.content.Context
import android.content.Intent
import com.run.common.base.BaseActivity
import com.run.ui.R
import com.run.ui.fragment.ApprenticeFragment
import kotlinx.android.synthetic.main.activity_apprentice_list.*

class ApprenticeListActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, ApprenticeListActivity::class.java))
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_apprentice_list
    }

    override fun initViews() {
        backview.setOnClickListener { finish() }
    }

    override fun initData() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.framelayout, ApprenticeFragment.newInstance())
        fragmentTransaction.commit()
    }

    override fun initPresenter(): Nothing? {
        return null
    }


}
