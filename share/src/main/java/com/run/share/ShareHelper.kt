package com.run.share


import android.content.Context
import android.view.View
import android.widget.TextView
import com.run.common.BaseApplication
import com.run.common.view.MyBottomSheetDialog
import com.run.presenter.LoginHelper

@Suppress("DEPRECATION")
class ShareHelper private constructor() {

    private var mContext: Context? = BaseApplication.context

    /**
     * 打开分享弹窗
     */
    fun doShare(context: Context?, articleid: Int, msg: String) {
        if (context == null) return
        mContext = context
        showDialog(context, articleid, msg)
    }

    private fun showDialog(context: Context?, articleid: Int, msg: String) {
        if (context == null) return
        mContext = context
        val dialog = MyBottomSheetDialog(mContext!!)
        val view = View.inflate(mContext, R.layout.dialog_share_layout, null)
        var tv_msg: TextView = view.findViewById(R.id.tv_msg)
        tv_msg.text = "+" + msg + "元/位"
        dialog.setContentView(view)
        view.findViewById<View>(R.id.tv_cancle).setOnClickListener { dialog.cancel() }
        view.findViewById<View>(R.id.ll_share_wc).setOnClickListener {
            //微信分享
            dialog.cancel()
        }
        view.findViewById<View>(R.id.ll_share_wc_friend).setOnClickListener {
            //朋友圈分享
            dialog.cancel()
        }
        view.findViewById<View>(R.id.tv_shouming).setOnClickListener {

        }
        dialog.show()
    }




    companion object {
        private var shareHelper: ShareHelper? = null
        val instance: ShareHelper
            get() {
                if (shareHelper == null) {
                    synchronized(LoginHelper::class.java) {
                        if (shareHelper == null) {
                            shareHelper = ShareHelper()
                        }
                    }
                }
                return this!!.shareHelper!!
            }
    }
}
