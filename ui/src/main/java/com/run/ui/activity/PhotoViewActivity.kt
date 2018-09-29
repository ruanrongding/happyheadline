package com.run.ui.activity

import android.annotation.TargetApi
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import com.run.common.base.BaseActivity
import com.run.common.emoj.EmotionUtils
import com.run.common.emoj.SpanStringUtils
import com.run.common.utils.UStatusBar
import com.run.ui.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * 图片查看器
 */
class PhotoViewActivity : BaseActivity<Nothing>() {

    companion object {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        fun newInstance(context: Activity, url: String, view: View, title: String, articled: Int) {
            val intent = Intent(context, PhotoViewActivity::class.java)
            intent.putExtra("URL", url)
            intent.putExtra("title", title)
            intent.putExtra("ARTICLEID", articled)
            context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(context, view, "share").toBundle())
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_photo_view
    }

    private lateinit var titleView: TextView
    override fun initViews() {
        UStatusBar.setStatusBarTranslucent(this)
        UStatusBar.setDarkMode(this@PhotoViewActivity)

        val title = intent.getStringExtra("title")
        titleView = findViewById(R.id.titleView)
        titleView.text = SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, this, titleView, title)

        findViewById<View>(R.id.returnView).setOnClickListener { onBackPressed() }
        findViewById<View>(R.id.iv_share).setOnClickListener {
            //TODO 开始分享 }
        }
        val mUrl = intent.getStringExtra("URL")
        val photoView: PhotoView = findViewById(R.id.photo_view)
        if (TextUtils.isEmpty(mUrl)) finish()
        Glide.with(this).load(mUrl).into(photoView)
        //解决gif图片加载不动的问题
        if (mUrl.endsWith("gif")) {
            Observable.timer(500, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        Glide.with(this).load(mUrl).into(photoView)
                    }
        }
    }

    override fun initPresenter(): Nothing? {
        return null
    }
    override fun initData() {

    }
}