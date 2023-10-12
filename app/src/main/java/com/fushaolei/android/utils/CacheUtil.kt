package com.fushaolei.android.utils

import android.content.Context
import com.fushaolei.android.App

object CacheUtil {

    private var sp = App.appContext.getSharedPreferences("sp", Context.MODE_PRIVATE)

    /**
     * 存储对象
     */
    fun saveObject(key: String, data: Any) {
        sp.edit().putString(key, DataUtil.gson.toJson(data)).apply()
    }

    /**
     * 获取存储对象的json字符串
     */
    fun getObjectStr(key: String): String? {
        if (!sp.contains(key)) return null
        return sp.getString(key, "")
    }

    fun saveString(key: String, value: String) {
        sp.edit().putString(key, value).apply()
    }

    fun getString(key: String): String {
        return if (sp.contains(key)) sp.getString(key, "")!! else ""
    }
}