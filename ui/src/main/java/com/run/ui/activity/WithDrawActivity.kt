package com.run.ui.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.run.common.base.BaseActivity
import com.run.common.dialog.DialogCallBack
import com.run.common.dialog.DialogFragmentHelper
import com.run.presenter.contract.IncomeModle
import com.run.presenter.contract.WithDrawContract
import com.run.ui.R
import com.run.ui.adapter.WithDrawAdapter
import kotlinx.android.synthetic.main.activity_with_draw.*
import kotlinx.android.synthetic.main.item_withdraw_layout.*

@Suppress("UNUSED_EXPRESSION")
open class WithDrawActivity : BaseActivity<WithDrawContract.WithDrawPresenter>(), WithDrawContract.WithDrawView, BaseQuickAdapter.OnItemClickListener {


    companion object {
        fun newInstance(act: Activity) {
            val intent = Intent(act, WithDrawActivity::class.java)
            act.startActivity(intent)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_with_draw
    }

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var adpater: WithDrawAdapter
    private var headView: View? = null
    private var footerView: View? = null

    private lateinit var withDrawTypeView: View
    private lateinit var withdrawView: View
    private lateinit var moenyView: TextView
    private lateinit var webView: WebView
    override fun initViews() {
        mRecyclerView = findViewById(R.id.recyclerview)
        mRecyclerView.layoutManager = GridLayoutManager(this, 2)
        adpater = WithDrawAdapter()
        mRecyclerView.adapter = adpater

        if (headView == null) {
            headView = View.inflate(this, R.layout.head_withdraw_layout, null)

            withDrawTypeView = headView!!.findViewById(R.id.withDrawTypeView)
            withDrawTypeView.isSelected = true

            moenyView = headView!!.findViewById(R.id.moenyView)

            footerView = View.inflate(this, R.layout.footer_withdraw_layout, null)
            webView = footerView!!.findViewById(R.id.webView)
            withdrawView = footerView!!.findViewById(R.id.withdrawView)
        }

        adpater.addHeaderView(headView)
        adpater.addFooterView(footerView)
        adpater.onItemClickListener = this

        backView.setOnClickListener { finish() }

        withdrawView.setOnClickListener {
            moneyView!!.isEnabled = false
            mPresenter!!.money(money, type, 0)
        }
    }

    override fun onItemClick(ad: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        money = adpater.getItem(position) as Int
        adpater.setMoney(money)
        adpater.notifyDataSetChanged()
        withdrawView.isEnabled = money <= allMoney
    }

    override fun initPresenter(): WithDrawContract.WithDrawPresenter? {
        return WithDrawContract.WithDrawPresenter(this)
    }

    override fun initData() {
        mPresenter!!.money_view()
    }

    private var money: Int = 0
    private var allMoney: Double = 0.0
    private var type: Int = 0//提现type

    override fun showData(modle: IncomeModle) {
        type = modle.list!![0].value
        money = modle.typelist!![0]
        adpater.setMoney(money)
        adpater.setNewData(modle.typelist)

        allMoney = modle.profit_balance
        moenyView.text = allMoney.toString()
        webView.loadDataWithBaseURL(null, modle.msg, "text/html", "utf-8", null)
        withdrawView.isEnabled = money <= allMoney
    }
    /**
     * 绑定微信
     */
    override fun gotoBindWC(msg: String, url: String) {
        DialogFragmentHelper.newBindWcDialog(this, msg)
                .show(this, object : DialogCallBack {
                    override fun cancle() {}
                    override fun onNext() {
                        BindWCActivity.newInstance(this@WithDrawActivity, url)
                    }
                })
    }

    /**
     * 提现成功
     */
    override fun moneyFinish(msg: String) {
        WithDrawResultActivity.newInstance(this, 0, "")
        this.finish()
    }

    /**
     * 提现失败
     */
    override fun showMoneyError(msg: String) {
        WithDrawResultActivity.newInstance(this, 1, msg)
        this.finish()
    }

}
