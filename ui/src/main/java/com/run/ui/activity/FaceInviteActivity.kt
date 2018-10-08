package com.run.ui.activity

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.run.common.base.BaseActivity
import com.run.common.utils.UBitmap
import com.run.common.utils.UStatusBar
import com.run.common.view.MyBottomSheetDialog
import com.run.share.utils.QRCodeUtil
import com.run.ui.R

@Suppress("DEPRECATION")
class FaceInviteActivity : BaseActivity<Nothing>() {


    companion object {
        fun newInstance(activity: Activity, url: String, title: String, content: String, picture: String) {
            val intent = Intent(activity, FaceInviteActivity::class.java)
            intent.putExtra("URL", url)
            intent.putExtra("title", title)
            intent.putExtra("content", content)
            intent.putExtra("picture", picture)
            activity.startActivity(intent)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_face_invite
    }

    private lateinit var rl_root: View
    private lateinit var iv_card: ImageView
    override fun initViews() {
        UStatusBar.setStatusBarTranslucent(this)
        findViewById<View>(R.id.iv_back).setOnClickListener({ finish() })
        iv_card = findViewById(R.id.iv_card)
        rl_root = findViewById(R.id.rl_root)
        rl_root!!.setOnLongClickListener {
            showSaveDialog()
            false
        }
    }

    override fun initPresenter(): Nothing? {
        return null
    }

    private var title: String? = null
    private var content: String? = null
    private var picture: String? = null
    private var mBitmap: Bitmap? = null
    private var url: String? = null

    override fun initData() {
        url = intent.getStringExtra("URL")
        title = intent.getStringExtra("title")
        content = intent.getStringExtra("content")
        picture = intent.getStringExtra("picture")
        mBitmap = QRCodeUtil.addLogo(QRCodeUtil.createQRCodeBitmap(this.url!!, 480, 480)!!, BitmapFactory.decodeResource(resources, R.mipmap.ic_logo))
        if (mBitmap == null) return
        iv_card!!.setImageBitmap(mBitmap)
    }

    private fun showSaveDialog() {
        val dialog = MyBottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_save_img_layout, null)
        dialog.setContentView(view)
        view.findViewById<View>(R.id.saveLayout).setOnClickListener(View.OnClickListener {
            //保存
            dialog.cancel()
            if (mBitmap == null) return@OnClickListener
            UBitmap.saveImageToGallery(this, mBitmap!!)
        })
        view.findViewById<View>(R.id.copyView).setOnClickListener {
            //复制链接
            dialog.cancel()
            val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cm.text = url
            showMsg("链接复制成功!")
        }
        view.findViewById<View>(R.id.tv_cancle).setOnClickListener { dialog.cancel() }
        dialog.show()
    }

}
