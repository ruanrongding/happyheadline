package com.run.ui.fragment

import android.os.Bundle
import com.run.common.base.BaseListFragment
import com.run.presenter.contract.CircleContract
import com.run.presenter.modle.CircleModle
import com.run.ui.adapter.CircleAdapter

class CircleFragment : BaseListFragment<CircleContract.CirclePresenter, CircleModle.DataBean>(), CircleContract.CircleView {

    companion object {
        fun newInstance(type: Int): CircleFragment {
            val fragment = CircleFragment()
            val bundle = Bundle()
            bundle.putInt("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initPresenter(): CircleContract.CirclePresenter? {
        return CircleContract.CirclePresenter(this)
    }


    private lateinit var adapter: CircleAdapter
    private var mType: Int = 0
    override fun initData() {
        mType = arguments!!.getInt("type")

        adapter = CircleAdapter()
        initAdapter(adapter)
    }

    override fun visiable() {
        requestData()
    }

    override fun requestData() {
        mPresenter!!.requestData(mType)
    }

    override fun callBackCircle(list: List<CircleModle.DataBean>?) {
        setData(list)
    }


}