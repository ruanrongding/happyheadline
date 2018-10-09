package com.run.ui.adapter

import android.view.View

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.ui.R

class SignAdapter : BaseQuickAdapter<Int, BaseViewHolder>(R.layout.item_sign_layout, null) {

    var sign_degree: Int = 0
    override fun convert(helper: BaseViewHolder, item: Int) {
        val ll_sign_status = helper.getView<View>(R.id.ll_sign_status)
        var day = ""
        day = if (item < 6) (item + 1).toString() + "天" else "更多"
        ll_sign_status.isEnabled = item > sign_degree - 1
        helper.setText(R.id.tv_lb, "+" + (20 + item * 5)).setText(R.id.tv_day, day)
    }
}
