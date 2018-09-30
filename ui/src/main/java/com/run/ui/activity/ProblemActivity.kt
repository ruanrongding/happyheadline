package com.run.ui.activity

import android.content.Context
import android.content.Intent
import com.run.common.base.BaseActivity
import com.run.ui.R
import com.run.ui.fragment.ProblemFragment
import kotlinx.android.synthetic.main.activity_problem.*

class ProblemActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(activity: Context, type: Int) {
            val intent = Intent(activity, ProblemActivity::class.java)
            intent.putExtra("TYPE", type)
            activity.startActivity(intent)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_problem
    }

    override fun initViews() {
        backView.setOnClickListener { finish() }
    }

    override fun initPresenter(): Nothing? {
        return null
    }

    private var mType: Int = 0
    override fun initData() {
        mType = intent.getIntExtra("TYPE", 1)
        when (mType) {
            1 -> titleView.text = "新手指导"
            2 -> titleView.text = "如何赚钱"
        }

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.problem_layout, ProblemFragment.newInstance(mType))
        fragmentTransaction.commit()
    }


}
