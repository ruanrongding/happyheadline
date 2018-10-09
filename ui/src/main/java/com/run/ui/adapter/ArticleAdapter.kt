package com.run.ui.adapter

import android.os.Handler
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.view.animation.AnimationUtils
import android.webkit.WebView
import android.widget.TextView
import android.widget.Toast
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.run.common.base.BaseObserver
import com.run.common.emoj.EmotionUtils
import com.run.common.emoj.SpanStringUtils
import com.run.common.utils.UGlide
import com.run.common.utils.UWebView
import com.run.presenter.LoginHelper
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.ArticleBean
import com.run.presenter.modle.PostModle
import com.run.share.ShareHelper
import com.run.ui.*
import com.run.ui.activity.PhotoViewActivity
import com.run.ui.activity.VedioDetailActivity
import com.run.ui.activity.WebPhotoActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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
        helper.setText(R.id.tv_user_name, item.user_name)
                .setText(R.id.tv_ding, if (item.goodpost == 0) "赞" else item.goodpost.toString())
                .setText(R.id.tv_comment, if (item.comment_num == 0) "评论" else item.comment_num.toString())
                .setText(R.id.tv_share_count, if (TextUtils.isEmpty(item.share_count)) "分享" else item.share_count + "")
                .setText(R.id.tv_cai, if (item.badpost == 0) "踩" else item.badpost.toString())






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
        helper.getView<View>(R.id.ll_operation_share).setOnClickListener {
            ShareHelper.instance.doShare(mContext, item.details_id)
        }


        val ll_operation_ding: View = helper.getView(R.id.ll_operation_ding)
        val ll_operation_cai: View = helper.getView(R.id.ll_operation_cai)
        when (item.post_type) {
            0 -> {
                ll_operation_cai.isSelected = false
                ll_operation_ding.isSelected = false
            }
            1 -> {
                ll_operation_cai.isSelected = false
                ll_operation_ding.isSelected = true
            }
            2 -> {
                ll_operation_cai.isSelected = true
                ll_operation_ding.isSelected = false
            }
        }

        /**
         * 点赞操作
         */
        ll_operation_ding.setOnClickListener {
            if (!LoginHelper.instance.isLogin(mContext)) return@setOnClickListener
            if (item.post_type === 1) { //取消点赞
                ApiManager.post_del(item.details_id, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : BaseObserver<PostModle>() {
                            override fun onSuccess(o: PostModle) {
                                Toast.makeText(mContext, o.msg, Toast.LENGTH_SHORT).show()
                                it.isSelected = false
                                helper.setText(R.id.tv_ding, (item.goodpost - 1).toString())
                                item.goodpost = item.goodpost - 1
                                item.post_type = 0
                            }

                            override fun onError(errorType: Int, msg: String?) {
                                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
                            }
                        })

            } else {
                ApiManager.like_post(item.details_id, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : BaseObserver<PostModle>() {
                            override fun onSuccess(o: PostModle) {
                                it.isSelected = true
                                helper.setText(R.id.tv_ding, (item.goodpost + 1).toString())
                                val addOne: View = helper.getView(R.id.addOne_tv)
                                addOne.visibility = View.VISIBLE
                                if (animation == null) {
                                    animation = AnimationUtils.loadAnimation(mContext, R.anim.add_score_anim)
                                }
                                addOne.startAnimation(animation)
                                Handler().postDelayed({ addOne.visibility = View.GONE }, 1000)
                                item.goodpost = item.goodpost + 1
                                item.post_type = 1
                            }

                            override fun onError(errorType: Int, msg: String?) {
                                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
                            }

                        })

            }

        }

        //踩
        ll_operation_cai.setOnClickListener {
            if (!LoginHelper.instance.isLogin(mContext)) return@setOnClickListener
            ApiManager.like_post(item.details_id, 2).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<PostModle>() {
                        override fun onSuccess(o: PostModle) {
                            it.isSelected = true
                            helper.setText(R.id.tv_cai, (item.badpost + 1).toString())
                            val addOne: View = helper.getView(R.id.addOne_tv2)
                            addOne.visibility = View.VISIBLE
                            if (animation == null) {
                                animation = AnimationUtils.loadAnimation(mContext, R.anim.add_score_anim)
                            }
                            addOne.startAnimation(animation)
                            Handler().postDelayed({ addOne.visibility = View.GONE }, 1000)
                            item.badpost = item.badpost + 1
                            item.post_type = 2
                        }

                        override fun onError(errorType: Int, msg: String?) {
                            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
                        }

                    })
        }
    }

    private var animation: android.view.animation.Animation? = null
}