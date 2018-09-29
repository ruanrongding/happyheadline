package com.run.ui.adapter


import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.common.utils.UGlide
import com.run.presenter.modle.CircleModle
import com.run.ui.R


class CircleAdapter : BaseQuickAdapter<CircleModle.DataBean, BaseViewHolder>(R.layout.item_circle_layout, null) {
    override fun convert(helper: BaseViewHolder, item: CircleModle.DataBean) {
        UGlide.loadRoundImage(mContext, item.circle_head, helper.getView(R.id.iv_circle_head), 5)
        helper.setText(R.id.tv_circle_name, item.circle_name)
                .setText(R.id.tv_intro, item.intro)
                .setText(R.id.tv_mun, item.mun.toString() + "")
    }
}
