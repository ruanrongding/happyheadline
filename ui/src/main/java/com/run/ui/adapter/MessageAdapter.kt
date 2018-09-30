package com.run.ui.adapter

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.common.emoj.EmotionUtils
import com.run.common.emoj.SpanStringUtils
import com.run.common.utils.UGlide
import com.run.presenter.modle.MsgTypeModle
import com.run.ui.R

class MessageAdapter : BaseQuickAdapter<MsgTypeModle.DataBean, BaseViewHolder>(R.layout.item_msg_layout, null) {
    override fun convert(helper: BaseViewHolder?, item: MsgTypeModle.DataBean?) {
        UGlide.loadRoundImage(mContext, item!!.ico_url, helper!!.getView(R.id.iconView), 5)
        helper.setText(R.id.titleView, item.title)
        var msgCountView: TextView = helper.getView(R.id.msgCountView)
        if (item.my_msg_mun <= 0) {
            msgCountView.visibility = View.GONE
        } else {
            msgCountView.visibility = View.VISIBLE
            msgCountView.text = if (item.my_msg_mun < 100) item.my_msg_mun.toString() else "99+"
        }
        val msgbean = item.new_mes ?: return
        helper.setText(R.id.timeView, msgbean.create_time)
        val contentView: TextView = helper.getView(R.id.contentView)
        contentView.text = if (TextUtils.isEmpty(msgbean.content)) "暂无消息" else SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, mContext, contentView, msgbean.content.toString())
    }

}