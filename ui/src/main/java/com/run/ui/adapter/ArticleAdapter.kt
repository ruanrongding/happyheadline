package com.run.ui.adapter

import android.text.Html
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.common.emoj.EmotionUtils
import com.run.common.emoj.SpanStringUtils
import com.run.common.utils.UGlide
import com.run.common.utils.UWebView
import com.run.presenter.modle.ArticleBean
import com.run.ui.*
import com.run.ui.activity.PhotoViewActivity
import com.run.ui.activity.VedioDetailActivity
import com.run.ui.activity.WebPhotoActivity

@Suppress("DEPRECATION")
class ArticleAdapter : BaseMultiItemQuickAdapter<ArticleBean, BaseViewHolder>(null) {
    init {
        addItemType(ArticleBean.ARTICLE_TEXT, R.layout.item_article_text_layout)
        addItemType(ArticleBean.ARTICLE_IMAGE, R.layout.item_article_image_layout)
        addItemType(ArticleBean.ARTICLE_GIFT, R.layout.item_article_image_layout)
        addItemType(ArticleBean.ARTICLE_GIF_PLUS, R.layout.item_article_gifplus_layout)
        addItemType(ArticleBean.ARTICLE_IMAGE_PLUS, R.layout.item_article_imageplus_layout)
        addItemType(ArticleBean.ARTICLE_VEDIO, R.layout.item_article_vedio_layout)
    }

    //网络请求的URL-->:
    override fun convert(helper: BaseViewHolder?, item: ArticleBean?) {
        UGlide.loadCircleImage(mContext, item!!.user_portrait!!, helper!!.getView(R.id.iv_user_portrait))
        helper.setText(R.id.tv_user_name, item!!.user_name)
        when (helper!!.itemViewType) {
            ArticleBean.ARTICLE_TEXT -> {
                val tv_title: TextView = helper.getView(R.id.tv_title)
                val msg = item!!.content!!.replace("<\\/p>", "").replace("<p>", "")
                tv_title.text = Html.fromHtml(msg).trim()
            }
            ArticleBean.ARTICLE_IMAGE -> {
                val tv_title: TextView = helper.getView(R.id.tv_title)
                tv_title.text = SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, mContext, tv_title, item.title)
                UGlide.loadImageFitWidth(mContext, item.litpic!!, helper.getView(R.id.iv_litpic))
                helper.getView<View>(R.id.ll_root).setOnClickListener {
                    PhotoViewActivity.newInstance(mContext as MainActivity, item.litpic!!, helper.getView(R.id.iv_litpic), item.title!!, item.details_id)
                }
            }

            ArticleBean.ARTICLE_GIFT, ArticleBean.ARTICLE_GIF_PLUS -> {
                val tv_title: TextView = helper.getView(R.id.tv_title)
                tv_title.text = SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, mContext, tv_title, item.title)
                UGlide.loadRoundImage(mContext, item.litpic!!, helper.getView(R.id.iv_litpic), 5)

                helper.getView<View>(R.id.ll_root).setOnClickListener {
                    PhotoViewActivity.newInstance(mContext as MainActivity, item.litpic!!, helper.getView(R.id.iv_litpic), item.title!!, item.details_id)
                }
            }
            ArticleBean.ARTICLE_IMAGE_PLUS -> {
                val webView: WebView = helper.getView(R.id.wb_content)
                UWebView.initWebView(webView)

                val tv_title: TextView = helper.getView(R.id.tv_title)
                tv_title.text = SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, mContext, tv_title, item.title)

                webView.loadDataWithBaseURL(null, UWebView.getNewContent2(item.content!!), "text/html", "utf-8", null)
                helper.getView<View>(R.id.ll_root).setOnClickListener {
                    WebPhotoActivity.newInstance(mContext, item.details_id, item.title!!)
                }
                helper.getView<View>(R.id.view_content).setOnClickListener {
                    WebPhotoActivity.newInstance(mContext, item.details_id, item.title!!)
                }
            }

            ArticleBean.ARTICLE_VEDIO -> {
                val tv_title: TextView = helper.getView(R.id.tv_title)
                tv_title.text = SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, mContext, tv_title, item.title)
                UGlide.loadRoundImage(mContext, item.litpic!!, helper.getView(R.id.iv_litpic), 5)
                helper.getView<View>(R.id.ll_root).setOnClickListener {
                        VedioDetailActivity.newInstance(mContext, item.details_id, item.litpic!!)

                }
            }
        }
    }
}