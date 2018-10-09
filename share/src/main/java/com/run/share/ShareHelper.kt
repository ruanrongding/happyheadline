package com.run.share


import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.widget.Toast
import com.run.common.BaseApplication
import com.run.common.base.BaseObserver
import com.run.common.utils.UCompositeDisposable
import com.run.common.utils.ULog
import com.run.common.view.MyBottomSheetDialog
import com.run.config.modle.BaseModle
import com.run.login.api.LoginManager
import com.run.presenter.LoginHelper
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.share.ShareModle
import com.run.share.utils.ShareManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Suppress("DEPRECATION")
class ShareHelper private constructor() {

    private var mContext: Context? = BaseApplication.context

    /**
     * 打开分享弹窗
     */
    fun doShare(context: Context?, articleid: Int) {
        if (context == null) return
        mContext = context
        showDialog(context, articleid)
    }

    private fun showDialog(context: Context?, articleid: Int) {
        if (context == null) return
        mContext = context
        val dialog = MyBottomSheetDialog(mContext!!)
        val view = View.inflate(mContext, R.layout.dialog_share_layout, null)
        dialog.setContentView(view)
        view.findViewById<View>(R.id.tv_cancle).setOnClickListener { dialog.cancel() }
        view.findViewById<View>(R.id.ll_share_wc).setOnClickListener {
            //微信分享
            dialog.cancel()
            requestShare(context, 1, articleid)
        }
        view.findViewById<View>(R.id.ll_share_wc_friend).setOnClickListener {
            //朋友圈分享
            dialog.cancel()
            requestShare(context, 2, articleid)
        }
        view.findViewById<View>(R.id.ll_share_code).setOnClickListener {
            requestShare(context, 3, articleid)
            dialog.cancel()
        }
        view.findViewById<View>(R.id.ll_share_save).setOnClickListener {
            //收藏
            doSave(context, articleid)
            dialog.cancel()
        }
        view.findViewById<View>(R.id.ll_share_report).setOnClickListener {
            //举报
            if (!LoginHelper.instance.isLogin(context)) return@setOnClickListener
            showReportDialog(context, articleid)
            dialog.cancel()
        }

        dialog.show()
    }


    private fun requestShare(context: Context, type: Int, articleid: Int) {
        if (mContext == null) mContext = context
        if (LoginHelper.instance.isLogin(context)) {
            LoginManager.share_record(articleid, type)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<ShareModle>() {
                        override fun onError(errorType: Int, msg: String?) {
                            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
                            ULog.e("msg:$msg")
                        }

                        override fun onSuccess(o: ShareModle) {
                            exShare(o.share_data, type)
                        }

                    })
        }
    }

    private fun exShare(shareBean: ShareModle.ShareDataBean?, type: Int) {
        if (shareBean == null) return
        var platform = "wechat_friend"
        var url = shareBean.url
        if (type == 3) {
            //复制链接
            val cm = mContext!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cm.text = shareBean.title + ";" + shareBean.url
            Toast.makeText(mContext, "链接复制成功!", Toast.LENGTH_SHORT).show()
            return
        } else if (type == 2 || type == 4) {
            platform = "wechat_moments"
            url = shareBean.url
        }
        if (type == 4) {
            UShare.doShare(mContext!!, platform, shareBean.title, shareBean.content_describe, url, shareBean.share_picture, shareBean.sort, 0, 2)
        } else {
            UShare.doShare(mContext!!, platform, shareBean.title, shareBean.content_describe, url, shareBean.share_picture, shareBean.sort, 0, shareBean.friend_type)
        }

    }

    /**
     * 执行收藏操作
     */
    private fun doSave(context: Context?, articlid: Int) {
        if (context != null) {
            mContext = context
        }
        if (LoginHelper.instance.isLogin(mContext!!)) {
            LoginManager.details_collect(articlid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<BaseModle>() {
                        override fun onSuccess(o: BaseModle) {
                            Toast.makeText(mContext, o.msg, Toast.LENGTH_SHORT).show()
                        }

                        override fun onError(errorType: Int, msg: String?) {
                            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
                        }
                    })
        }
    }


    /**
     * 举报弹窗
     */
    private fun showReportDialog(context: Context?, articleid: Int) {
        if (context == null) return
        mContext = context
        val dialog = MyBottomSheetDialog(mContext!!)
        val view = View.inflate(mContext, R.layout.dialog_report_layout, null)
        dialog.setContentView(view)
        view.findViewById<View>(R.id.tv_cancle).setOnClickListener { dialog.cancel() }
        view.findViewById<View>(R.id.tv_report_1).setOnClickListener {
            doReport(articleid, "内容质量差（不好看）")
            dialog.cancel()
        }
        view.findViewById<View>(R.id.tv_report_2).setOnClickListener {
            doReport(articleid, "内容有重复（坟贴等）")
            dialog.cancel()
        }
        view.findViewById<View>(R.id.tv_report_3).setOnClickListener {
            doReport(articleid, "内容太老旧（之前看过）")
            dialog.cancel()
        }
        view.findViewById<View>(R.id.tv_report_4).setOnClickListener {
            doReport(articleid, "广告色情/政治/侮辱")
            dialog.cancel()
        }
        view.findViewById<View>(R.id.tv_report_5).setOnClickListener {
            doReport(articleid, "其他屏蔽理由")
            dialog.cancel()
        }
        dialog.show()

    }

    private fun doReport(articleid: Int, content: String) {
        LoginManager.add_complaints(articleid, content).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserver<BaseModle>() {
                    override fun onSuccess(o: BaseModle) {
                        Toast.makeText(mContext, o.msg, Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(errorType: Int, msg: String?) {
                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
                    }
                })
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
                return this.shareHelper!!
            }
    }
}
