package com.run.ui.activity

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.RelativeLayout
import com.run.common.base.BaseActivity
import com.run.common.utils.UGlide
import com.run.common.utils.URxBus
import com.run.common.utils.USize
import com.run.conifg.RxBusConfig
import com.run.conifg.modle.RxBean
import com.run.conifg.modle.VedioBean
import com.run.presenter.contract.ArticleDetailContract
import com.run.presenter.modle.ArticleDetailModle
import com.run.ui.R
import com.yun.uvedio.JZVideoPlayerStandard

/**
 * 视屏播放
 */
class VedioDetailActivity : BaseActivity<ArticleDetailContract.ArticleDetailPresenter>(), ArticleDetailContract.ArticleDetailView, JZVideoPlayerStandard.OnPlayListener{

    companion object {
        fun newInstance(context: Context, articled: Int, url: String) {
            val intent = Intent(context, VedioDetailActivity::class.java)
            intent.putExtra("ARTICLEID", articled)
            intent.putExtra("IMAGEURL", url)
            context.startActivity(intent)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_vedio_detail
    }

    private lateinit var jzVideoPlayerStandard: JZVideoPlayerStandard

    override fun initViews() {
        jzVideoPlayerStandard = findViewById(R.id.videoplayer)
        jzVideoPlayerStandard.setmListener(this)
    }

    override fun initPresenter(): ArticleDetailContract.ArticleDetailPresenter? {
        return ArticleDetailContract.ArticleDetailPresenter(this)
    }

    private var articled: Int = 0
    private var imageUrl: String? = null
    override fun initData() {
        imageUrl = intent.getStringExtra("IMAGEURL")
        articled = intent.getIntExtra("ARTICLEID", 0)
        mPresenter!!.requestData(articled)
        operateBus()
    }
    private var mHeight: Int = 250
    private fun operateBus() {
        URxBus.get().toFlowable().map { o -> o as RxBean<*> }.subscribe {
            when (it.type) {
                RxBusConfig.VEDIO_TYPE -> {
                    if (!hasPortrait) {
                        val vedio: VedioBean = it.data as VedioBean
                        mHeight = USize.getWidth(this@VedioDetailActivity) * vedio.height / vedio.width
                        performAnim(true)
                        hasPortrait = true
                        return@subscribe
                    }
                }
                RxBusConfig.VEDIO_PLAY_TYPE -> {
                    if (it.code == RxBusConfig.VEDIO_REPLAY_CODE) {
                        performAnim(true)
                    }
                }
            }
        }
    }
    private var hasPortrait: Boolean = false
    override fun callBackData(bean: ArticleDetailModle.DataBean) {
        when (bean.is_width_height) {
            1-> {
                val params: RelativeLayout.LayoutParams = jzVideoPlayerStandard.layoutParams as RelativeLayout.LayoutParams
                params.height = USize.dip2px(this, 250f)
                jzVideoPlayerStandard.layoutParams = params
            }
        }
        UGlide.loadImage(this, imageUrl!!, jzVideoPlayerStandard!!.thumbImageView)
        jzVideoPlayerStandard!!.setUp(if (TextUtils.isEmpty(bean.video_url)) bean.content else bean.video_url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "")
        jzVideoPlayerStandard!!.startVideo()
    }

    //====================================================================视频播放回调===============
    /**
     * 视频播放完成
     */
    override fun playFinish() {
        performAnim(false)
    }
    private var hasShowVedio: Boolean = false
    private fun performAnim(show: Boolean) {
        if (hasShowVedio == show) return
        //属性动画对象
        val va: ValueAnimator = if (show) {  //显示view，高度从0变到height值
            ValueAnimator.ofInt(USize.dip2px(this, 250f), mHeight)
        } else {
            ValueAnimator.ofInt(mHeight, USize.dip2px(this, 250f))  //隐藏view，高度从height变为0
        }
        va.addUpdateListener { valueAnimator ->
            //获取当前的height值
            val h = valueAnimator.animatedValue as Int
            //动态更新view的高度
            jzVideoPlayerStandard.layoutParams.height = h
            jzVideoPlayerStandard.requestLayout()
        }
        this.hasShowVedio = show
        va.duration = 800
        //开始动画
        va.start()
    }

    override fun onBackPressed() {
        if (hasShowVedio) {
            performAnim(false)
            return
        }
        if (JZVideoPlayerStandard.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        if (jzVideoPlayerStandard != null) {
            JZVideoPlayerStandard.releaseAllVideos()
        }
    }

}
