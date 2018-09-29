package com.yun.happyheadline.wxapi


import android.content.Context
import android.os.Bundle
import android.widget.Toast

import com.run.common.BaseApplication
import com.run.common.utils.ULog
import com.run.presenter.LoginHelper
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.umeng.weixin.callback.WXCallbackActivity


class WXEntryActivity : WXCallbackActivity(), IWXAPIEventHandler {
    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ULog.e(TAG, "onCreate:------>")
        mContext = this
        //这句没有写,是不能执行回调的方法的
        BaseApplication.mWxApi.handleIntent(intent, this)
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    override fun onReq(baseReq: BaseReq) {}

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    override fun onResp(baseResp: BaseResp) {
        ULog.d(TAG, "onResp:------>")
        ULog.d(TAG, "error_code:---->" + baseResp.errCode)
        val type = baseResp.type //类型：分享还是登录
        ULog.d(TAG, "type:---->" + baseResp.type)
        when (baseResp.errCode) {
            BaseResp.ErrCode.ERR_AUTH_DENIED -> {
                //用户取消
                var message = ""
                if (type == RETURN_MSG_TYPE_LOGIN) {
                    message = "取消了微信登录"
                } else if (type == RETURN_MSG_TYPE_SHARE) {
                    message = "取消了微信分享"
                }
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
            }
            BaseResp.ErrCode.ERR_USER_CANCEL -> {
                var message = ""
                if (type == RETURN_MSG_TYPE_LOGIN) {
                    message = "取消了微信登录"
                } else if (type == RETURN_MSG_TYPE_SHARE) {
                    message = "取消了微信分享"
                }
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
            }
            BaseResp.ErrCode.ERR_OK ->
                //用户同意
                if (type == RETURN_MSG_TYPE_LOGIN) {
                    //用户换取access_token的code，仅在ErrCode为0时有效
                    val code = (baseResp as SendAuth.Resp).code
                    ULog.d(TAG, "code:------>$code")
                    LoginHelper.instance.getOpenID(code)
                } else if (type == RETURN_MSG_TYPE_SHARE) {
                    Toast.makeText(mContext, "微信分享成功", Toast.LENGTH_SHORT).show()
                }
        }
        this.finish()
    }


    override fun a(var1: com.umeng.weixin.umengwx.b?) {
        this.finish()
    }

    override fun a(var1: com.umeng.weixin.umengwx.a) {
        this.finish()
    }

    companion object {
        private const val TAG = "WXEntryActivity"
        private const val RETURN_MSG_TYPE_LOGIN = 1 //登录
        private const val RETURN_MSG_TYPE_SHARE = 2 //分享
    }


}
