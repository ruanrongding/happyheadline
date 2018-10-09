package com.run.ui.activity

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import com.run.common.base.BaseActivity
import com.run.common.utils.UGlide
import com.run.presenter.contract.InviteContract
import com.run.presenter.modle.InviteModle
import com.run.share.UShare
import com.run.ui.R
import kotlinx.android.synthetic.main.activity_invite.*
import kotlinx.android.synthetic.main.layout_invite_header.*

@Suppress("DEPRECATION", "DEPRECATED_IDENTITY_EQUALS")
class InviteActivity : BaseActivity<InviteContract.InvitePresenter>(), InviteContract.InviteView {
    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, InviteActivity::class.java))
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_invite
    }

    private lateinit var tv_count_invite: TextView
    private lateinit var tv_list: TextView
    private lateinit var tv_count_apprentice: TextView
    private lateinit var tv_count_all: TextView
    private lateinit var webView: WebView
    override fun initViews() {
        backview.setOnClickListener { finish() }
        //收徒说明
        apprenticeView.setOnClickListener { ProblemActivity.newInstance(this, 2) }
        tv_count_invite = findViewById(R.id.tv_count_invite)
        tv_list = findViewById(R.id.tv_list)
        tv_count_apprentice = findViewById(R.id.tv_count_apprentice)
        tv_count_all = findViewById(R.id.tv_count_all)

        findViewById<View>(R.id.ll_share_wc).setOnClickListener(this)
        findViewById<View>(R.id.ll_share_wcfriend).setOnClickListener(this)
        findViewById<View>(R.id.ll_share_face).setOnClickListener(this)
        findViewById<View>(R.id.ll_share_copy).setOnClickListener(this)

        webView = findViewById(R.id.webView)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_share_wc -> UShare.doShare(this, "wechat_friend", shareBean.title!!, shareBean.content_describe!!, url!!, shareBean.share_picture!!, 0)
            R.id.ll_share_wcfriend -> UShare.doShare(this, "wechat_moments", shareBean!!.title!!, shareBean!!.content_describe!!, url!!, shareBean.share_picture!!, 1)
            R.id.ll_share_copy -> doCopy()
            R.id.ll_share_face -> FaceInviteActivity.newInstance(this, url!!, shareBean.title!!, shareBean.content_describe!!, shareBean.share_picture!!)
        }
    }

    /**
     * 复制链接
     */
    private fun doCopy() {
        val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.text = shareBean!!.title + ";" + url
        showMsg("链接复制成功")
    }

    override fun initPresenter(): InviteContract.InvitePresenter? {
        return InviteContract.InvitePresenter(this)
    }

    override fun initData() {
        mPresenter!!.invite()
    }
    private lateinit var shareBean: InviteModle.ShareBean
    private var url: String? = null
    private var friendUrl: String? = null
    override fun showInvite(modle: InviteModle) {
        tv_count_invite.text = modle.getCount_invite().toString()
        tv_list.text = modle.getList().toString()
        tv_count_apprentice.text = if (TextUtils.isEmpty(modle.getCount_apprentice())) "0.0" else modle.getCount_apprentice() + ""
        tv_count_all.text = if (TextUtils.isEmpty(modle.getCount_all())) "0.0" else modle.getCount_all() + ""

        url = modle.getUrl()
        friendUrl = modle.getFriend_url()
        shareBean = modle.getShare()!!


        //收徒说明
        webView.loadDataWithBaseURL(null, modle.getExplain(), "text/html", "utf-8", null)

    }


}
