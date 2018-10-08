package com.run.common.dialog

import android.content.Context
import com.run.common.R

class DialogFragmentHelper : BaseDialogFragment() {


    companion object {

        /**
         * 权限管理对话框
         */
        fun newPermissionDialog(context: Context): DialogFragmentHelper {
            val dialog = DialogFragmentHelper()
            dialog.arguments = getBudle("",
                    context.resources.getString(R.string.request_permisson_hint),
                    context.resources.getString(R.string.positive),
                    context.resources.getString(R.string.cancle),
                    false)
            return dialog
        }


        /**
         * 清除缓存对话框
         */
        fun newCacheDialog(context: Context): DialogFragmentHelper {
            val dialog = DialogFragmentHelper()
            dialog.arguments = getBudle("",
                    context.resources.getString(R.string.clear_cacher),
                    context.resources.getString(R.string.positive),
                    context.resources.getString(R.string.cancle),
                    true)
            return dialog
        }


        fun newBindWcDialog(context: Context, msg: String): DialogFragmentHelper {
            val dialog = DialogFragmentHelper()
            dialog.arguments = getBudle("", msg, context.resources.getString(R.string.positive), context.resources.getString(R.string.cancle), true)
            return dialog
        }

    }
}