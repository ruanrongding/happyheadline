package com.run.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import com.run.common.base.BaseActivity
import com.run.common.emoj.EmotionUtils
import com.run.common.emoj.SpanStringUtils
import com.run.common.utils.UStatusBar
import com.run.common.utils.UWebView
import com.run.presenter.contract.ArticleDetailContract
import com.run.presenter.modle.ArticleDetailModle
import com.run.ui.R

@Suppress("DEPRECATION")
class WebPhotoActivity : BaseActivity<ArticleDetailContract.ArticleDetailPresenter>(), ArticleDetailContract.ArticleDetailView {

    companion object {
        fun newInstance(context: Context, articled: Int, title: String) {
            var intent = Intent(context, WebPhotoActivity::class.java)
            intent.putExtra("ARTICLEID", articled)
            intent.putExtra("title", title)
            context.startActivity(intent)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_web_photo
    }

    private lateinit var webView: WebView
    private lateinit var titleView: TextView
    override fun initViews() {
        UStatusBar.setStatusColor(this, resources.getColor(R.color.black), 0)
        UStatusBar.setDarkMode(this@WebPhotoActivity)
        webView = findViewById(R.id.webView)


        val title = intent.getStringExtra("title")
        titleView = findViewById(R.id.titleView)
        titleView.text = SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, this, titleView, title)

        findViewById<View>(R.id.returnView).setOnClickListener { onBackPressed() }


    }

    override fun initPresenter(): ArticleDetailContract.ArticleDetailPresenter? {
        return ArticleDetailContract.ArticleDetailPresenter(this)
    }

    private var articled: Int = 0
    override fun initData() {
        UWebView.initWebView(webView)
        webView.setBackgroundColor(resources.getColor(R.color.black))
        articled = intent.getIntExtra("ARTICLEID", 0)
        mPresenter!!.requestData(articled)
    }

    override fun callBackData(bean: ArticleDetailModle.DataBean) {
        webView.loadDataWithBaseURL(null, UWebView.getNewContent(bean.content!!), "text/html", "utf-8", null)
    }

}
