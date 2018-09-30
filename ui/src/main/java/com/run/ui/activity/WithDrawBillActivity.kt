package com.run.ui.activity

import android.app.Activity
import android.content.Intent
import com.run.common.base.BaseActivity
import com.run.ui.R
import com.run.ui.fragment.WithDrawBillFragment
import kotlinx.android.synthetic.main.activity_with_draw_bill.*

class WithDrawBillActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(act: Activity) {
            val intent = Intent(act, WithDrawBillActivity::class.java)
            act.startActivity(intent)
        }
    }
    override fun initContentView(): Int {
        return R.layout.activity_with_draw_bill
    }

    override fun initViews() {
        backView.setOnClickListener { finish() }
    }

    override fun initData() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.framelayout, WithDrawBillFragment.newInstance())
        fragmentTransaction.commit()
    }

    override fun initPresenter(): Nothing? {
        return null
    }
}