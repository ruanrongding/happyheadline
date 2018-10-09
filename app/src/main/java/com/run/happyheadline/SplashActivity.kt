package com.run.happyheadline

import android.Manifest
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.run.common.dialog.DialogCallBack
import com.run.common.dialog.DialogFragmentHelper
import com.run.common.helper.SharedPreferenceHelper
import com.run.common.utils.ULog
import com.run.common.utils.UPermission
import com.run.ui.MainActivity
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

@Suppress("JAVA_CLASS_ON_COMPANION")
class SplashActivity : AppCompatActivity() {
    companion object {
        val TAG: String = SplashActivity.javaClass.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //防止每次打开应用的时候都弹出启动图来
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            finish()
            return
        }
    }

    private var dialogShow = false //对话框是否开启
    override fun onStart() {
        super.onStart()
        //如果权限对话框打开，则不检查权限
        if (dialogShow) return
        checkPermission()
    }

    //==========================================权限检查==================================================================================
    /**
     * 需要初始化的权限
     */
    private val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)

    /**
     * 检查权限
     */
    private fun checkPermission() {
        ULog.d(TAG, "开始检查权限")
        if (UPermission.checkPermission(this, *permissions)) {
            //通过权限
            permissionSuccess()
        }
    }

    /**
     * 权限回调
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        ULog.d(TAG, "权限请求开始回调")
        val permissionStatus: Boolean = UPermission.onRequestPermissionsResult(requestCode, grantResults)
        if (permissionStatus) {
            //权限通过
            permissionSuccess()
        } else {
            //没有通过权限请求
            requestPermissionDialog()
        }
    }

    /**
     * 还有权限没有开启，用户再次确认
     */
    private fun requestPermissionDialog() {
        ULog.d(TAG, "权限没通过，请求用户再次确认")
        DialogFragmentHelper.newPermissionDialog(this@SplashActivity).show(this@SplashActivity, callBack = object : DialogCallBack {
            override fun onNext() {
                ULog.d(TAG, "重新请求权限")
                checkPermission()
                dialogShow = false
            }

            override fun cancle() {
                finish()
                dialogShow = false
                ULog.d(TAG, "关闭应用")
            }
        })
        dialogShow = true
    }

    /**
     * 获取权限成功
     * 别让我等：响应速度要快
     * 别让我想：不要用户去思考
     * 别让我烦：
     */
    private fun permissionSuccess() {
        ULog.d(TAG, "权限请求通过")
        Observable.timer(1, TimeUnit.SECONDS).subscribe {
            if (SharedPreferenceHelper.checkHasOpenGuide(this@SplashActivity)) {
                MainActivity.newInstance(this@SplashActivity)
                finish()
            } else {
                GuideActivity.newInstance(this@SplashActivity)
            }
        }
    }
}
