package com.run.ui.adapter

import android.view.View
import android.widget.TextView

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.presenter.modle.TaskBean
import com.run.ui.MainActivity
import com.run.ui.R
import com.run.ui.activity.InviteActivity


@Suppress("DEPRECATED_IDENTITY_EQUALS")
class TaskAdapter : BaseQuickAdapter<TaskBean, BaseViewHolder>(R.layout.item_task_layout, null) {

    override fun convert(helper: BaseViewHolder, item: TaskBean) {
        helper.setText(R.id.tv_title, item.title)
                .setText(R.id.tv_gold_num, "+" + item.gold_num)
                .setText(R.id.tv_description, item.description)

        val tv_task_progress: TextView = helper.getView(R.id.tv_task_progress)
        val tv_status: TextView = helper.getView(R.id.tv_status)
        val ll_money: View = helper.getView(R.id.ll_money)
        if (item.upper_limit == 0) {
            if (item.t_money == 0) {
                ll_money.visibility = View.GONE
            } else {
                helper.setText(R.id.tv_gold_num, "+" + item.t_money)
                tv_task_progress.text = ""
                ll_money.visibility = View.VISIBLE
            }
        } else {
            val msg = "(" + item.gold_num / item.t_money + "/" + item.upper_limit / item.t_money + ")"
            tv_task_progress.text = msg
            ll_money.visibility = View.VISIBLE
            if (item.gold_num == item.upper_limit) {
                tv_status.text = "已完成"
                tv_status.isEnabled = false
            } else {
                tv_status.text = "去看看"
                tv_status.isEnabled = true
            }
        }
        helper.getView<View>(R.id.tv_status).setOnClickListener {
            if (helper.layoutPosition == 5) {
                InviteActivity.newInstance(mContext)
            } else {
                MainActivity.newInstance(mContext)
            }
        }
    }
}
