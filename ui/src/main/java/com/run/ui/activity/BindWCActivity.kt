package com.run.ui.activity

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import com.run.common.base.BaseActivity
import com.run.common.utils.UBitmap
import com.run.common.view.MyBottomSheetDialog
import com.run.share.utils.QRCodeUtil
import com.run.ui.R
import kotlinx.android.synthetic.main.activity_my_wallet.*

@Suppress("DEPRECATION")
/**
 * 绑定微信
 */
class BindWCActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(activity: Activity, url: String) {
            val intent = Intent(activity, BindWCActivity::class.java)
            intent.putExtra("URL", url)
            activity.startActivity(intent)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_bind_wc
    }

    private lateinit var codeView: ImageView
    override fun initViews() {
        backView.setOnClickListener { finish() }

        codeView = findViewById(R.id.codeView)
        codeView.setOnLongClickListener {
            showSaveDialog()
            false
        }
    }

    private fun showSaveDialog() {
        val dialog = MyBottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_save_img_layout, null)
        dialog.setContentView(view)
        view.findViewById<View>(R.id.saveLayout).setOnClickListener(View.OnClickListener {
            //保存
            dialog.cancel()
            if (mBitmap == null) return@OnClickListener
            UBitmap.saveImageToGallery(this@BindWCActivity, mBitmap!!)
        })
        view.findViewById<View>(R.id.copyView).setOnClickListener {
            //复制链接
            dialog.cancel()
            val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cm.text = mUrl
            showMsg("链接复制成功!")
        }
        view.findViewById<View>(R.id.tv_cancle).setOnClickListener { dialog.cancel() }
        dialog.show()
    }

    override fun initPresenter(): Nothing? {
        return null
    }

    private lateinit var mUrl: String
    private lateinit var mBitmap: Bitmap
    override fun initData() {
        mUrl = intent.getStringExtra("URL")
        mBitmap = QRCodeUtil.createQRCodeBitmap(mUrl, 480, 480)!!
        mBitmap = QRCodeUtil.addLogo(QRCodeUtil.createQRCodeBitmap(mUrl!!, 480, 480)!!, BitmapFactory.decodeResource(resources, R.mipmap.ic_logo))
        codeView.setImageBitmap(mBitmap)
    }


}
