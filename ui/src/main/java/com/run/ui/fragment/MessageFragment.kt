package com.run.ui.fragment

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.run.common.base.BaseListFragment
import com.run.presenter.contract.MessageContract
import com.run.presenter.modle.MsgTypeModle
import com.run.ui.activity.MessageListActivity
import com.run.ui.adapter.MessageAdapter

class MessageFragment : BaseListFragment<MessageContract.MessagePresenter, MsgTypeModle.DataBean>(), MessageContract.MessageView, BaseQuickAdapter.OnItemClickListener {


    companion object {
        fun newInstance(): MessageFragment {
            return MessageFragment()
        }
    }

    override fun initPresenter(): MessageContract.MessagePresenter? {
        return MessageContract.MessagePresenter(this)
    }

    private lateinit var adapter: MessageAdapter
    override fun initData() {
        adapter = MessageAdapter()
        initAdapter(adapter)
        requestData()

        adapter.setOnItemClickListener(this)
    }

    override fun requestData() {
        mPresenter!!.requestDeta()
    }

    override fun callBackData(list: List<MsgTypeModle.DataBean>?) {
        setData(list, false)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val bean: MsgTypeModle.DataBean = adapter!!.getItem(position) as MsgTypeModle.DataBean?
                ?: return
        MessageListActivity.newInstance(activity!!, bean.msg_id, bean.title!!)
    }

}