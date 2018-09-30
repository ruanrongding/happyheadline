package com.run.ui.activity

import android.app.Activity
import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.run.common.base.BaseActivity
import com.run.presenter.contract.IncomeModle
import com.run.presenter.contract.WithDrawContract
import com.run.ui.R
import com.run.ui.adapter.WithDrawAdapter

class WithDrawActivity : BaseActivity<WithDrawContract.WithDrawPresenter>(), WithDrawContract.WithDrawView, SwipeRefreshLayout.OnRefreshListener {


    companion object {
        fun newInstance(act: Activity) {
            val intent = Intent(act, WithDrawActivity::class.java)
            act.startActivity(intent)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_with_draw
    }

    protected lateinit var mRecyclerView: RecyclerView
    protected lateinit var mSwipeLayout: SwipeRefreshLayout
    private lateinit var adpater: WithDrawAdapter
    private var headView: View? = null
    private var footerView: View? = null
    override fun initViews() {
        mRecyclerView = findViewById(R.id.recyclerview)
        mSwipeLayout = findViewById(R.id.swiperefreshlayout)
        mSwipeLayout.setColorSchemeResources(R.color.colorSwipe)//设置进度动画的颜色。
        mSwipeLayout.setOnRefreshListener(this)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(this, 2)
        adpater = WithDrawAdapter()
        mRecyclerView.adapter

        if (headView == null) {
            headView = View.inflate(this, R.layout.head_withdraw_layout, null)
            footerView = View.inflate(this, R.layout.footer_withdraw_layout, null)
        }
        adpater.addHeaderView(headView)
        adpater.addFooterView(footerView)

    }

    override fun initPresenter(): WithDrawContract.WithDrawPresenter? {
        return WithDrawContract.WithDrawPresenter(this)
    }

    override fun initData() {
        mPresenter!!.money_view()
    }

    private var money: Int = 0
    override fun showData(modle: IncomeModle) {
        money = modle.typelist!![0]
        adpater.setMoney(money)
        adpater.setNewData(modle.typelist)
    }

    override fun gotoBindWC(msg: String, url: String) {
    }

    override fun moneyFinish(msg: String) {
    }

    override fun showMoneyError(msg: String) {
    }

    override fun onRefresh() {

    }

}
