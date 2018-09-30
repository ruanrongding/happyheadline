package com.run.ui.fragment

import android.os.Bundle
import com.run.common.base.BaseListFragment
import com.run.presenter.contract.MsgListContract
import com.run.presenter.modle.MsgModle
import com.run.ui.adapter.MsgListAdapter

class MsgListFragment : BaseListFragment<MsgListContract.MsgListPresenter, MsgModle.DataBean>(), MsgListContract.MsgListView {

    companion object {
        fun newInstance(msgId: Int): MsgListFragment {
            val fragment = MsgListFragment()
            val bundle = Bundle()
            bundle.putInt("msgId", msgId)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun initPresenter(): MsgListContract.MsgListPresenter? {
        return MsgListContract.MsgListPresenter(this)
    }

    private lateinit var adapter: MsgListAdapter
    private var msgid: Int = 0
    override fun initData() {
        msgid = arguments!!.getInt("msgId")
        adapter = MsgListAdapter()
        initAdapter(adapter)
        requestData()
    }

    override fun requestData() {
        mPresenter!!.requeestData(msgid)
    }

    override fun callBackData(list: List<MsgModle.DataBean>?) {
        setData(list)
    }

}