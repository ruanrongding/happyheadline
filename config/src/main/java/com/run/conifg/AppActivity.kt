package com.run.conifg

import android.content.Context
import android.content.Intent

object AppActivity {


    fun openLogin(context: Context?) {
        if (context == null) return
        val intent = Intent()
        intent.action = ActionConfig.Action_Login
        context.startActivity(intent)
    }
}