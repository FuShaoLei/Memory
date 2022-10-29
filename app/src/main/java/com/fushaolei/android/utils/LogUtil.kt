package com.fushaolei.android.utils

import android.util.Log

object LogUtil {

    fun e(tag: String, msg: String) {
        Log.e(tag, msg)
    }

    fun e(msg: String) {
        Log.e("Memory", msg)
    }
}