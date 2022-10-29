package com.fushaolei.android

import android.content.Context
import android.content.Intent
import com.fushaolei.android.utils.CacheUtil
import com.fushaolei.android.utils.DataUtil

/**
 * 启动Activity
 * @param T 目的Activity
 */
inline fun <reified T> start(context: Context) {
    context.startActivity(Intent(context, T::class.java))
}

inline fun <reified T> getObjectFromCache(key: String): T? {
    val jsonData = CacheUtil.getObjectStr(key) ?: return null
    return DataUtil.gson.fromJson(jsonData, T::class.java)
}