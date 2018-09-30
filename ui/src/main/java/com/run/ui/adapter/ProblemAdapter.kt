package com.run.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.presenter.modle.UserItemBean
import com.run.ui.R

class ProblemAdapter : BaseQuickAdapter<UserItemBean, BaseViewHolder>(R.layout.item_problem_layout, null) {
    override fun convert(helper: BaseViewHolder, item: UserItemBean) {
        helper.setText(R.id.tv_title, item.title).setText(R.id.tv_content, item.content)
    }

}
