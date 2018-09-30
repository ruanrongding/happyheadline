package com.run.ui.fragment

import android.os.Bundle
import com.run.common.R
import com.run.common.base.BaseListFragment
import com.run.presenter.contract.RevenueContract
import com.run.presenter.modle.IncomeRecordModle
import com.run.ui.adapter.IncomeRecordAdapter

/**
 * 收益明细的Adapter
 */
class RevenueDetailFragment : BaseListFragment<RevenueContract.RevenuePresenter, IncomeRecordModle.DataBean>(), RevenueContract.RevenueView {

    override fun initContentView(): Int {
        return R.layout.layout_recyclerview2
    }

    companion object {
        fun newInstance(type: Int): RevenueDetailFragment {
            val fragment = RevenueDetailFragment()
            val bundle = Bundle()
            bundle.putInt("TYPE", type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initPresenter(): RevenueContract.RevenuePresenter? {
        return RevenueContract.RevenuePresenter(this)
    }

    private lateinit var adapter: IncomeRecordAdapter
    override fun initData() {
        adapter = IncomeRecordAdapter()
        initAdapter(adapter)
        requestData()
    }

    override fun requestData() {
        val mType = arguments!!.getInt("TYPE")
        mPresenter!!.requestData(mType.toString(), mPage)
    }

    override fun showData(modle: List<IncomeRecordModle.DataBean>?) {
        setData(modle)
    }

}