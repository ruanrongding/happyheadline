package com.run.ui.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.common.emoj.EmotionUtils
import com.run.common.emoj.SpanStringUtils
import com.run.presenter.modle.MsgModle
import com.run.ui.R

class MsgListAdapter : BaseQuickAdapter<MsgModle.DataBean, BaseViewHolder>(R.layout.item_msglist_layout, null) {
    override fun convert(helper: BaseViewHolder?, item: MsgModle.DataBean?) {
        helper!!.setText(R.id.titleView, item!!.title).setText(R.id.timeView, item!!.create_time)
        val contentView: TextView = helper.getView(R.id.contentView)
        contentView.text = SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE,
                mContext, contentView, item.content.toString())
    }

}