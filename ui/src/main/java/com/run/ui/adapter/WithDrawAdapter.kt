package com.run.ui.adapter

import android.annotation.SuppressLint
import android.widget.Button
import android.widget.TextView

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.ui.R

class WithDrawAdapter : BaseQuickAdapter<Int, BaseViewHolder>(R.layout.item_withdraw_layout, null) {
    private var money: Int = 0
    fun setMoney(money: Int) {
        this.money = money
    }

    @SuppressLint("SetTextI18n")
    override fun convert(helper: BaseViewHolder, item: Int?) {
        val moneyView: TextView = helper.getView(R.id.moneyView)
        moneyView.isSelected = item == money
        moneyView.text = item!!.toString() + "元"
    }
}
