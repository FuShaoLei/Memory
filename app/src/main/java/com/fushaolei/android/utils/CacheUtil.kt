package com.fushaolei.android.utils

import android.content.Context
import androidx.annotation.Nullable
import com.fushaolei.android.App
import com.fushaolei.android.bean.Configuration
import com.google.gson.Gson

object CacheUtil {

    private var sp = App.appContext.getSharedPreferences("sp", Context.MODE_PRIVATE)

    /**
     * 存储对象
     */
    fun saveObject(key: String, data: Any) {
        sp.edit().putString(key, DataUtil.gson.toJson(data)).apply()
    }

    /**
     * 获取存储对象的json字符串。。
     */
    @Nullable
    fun getObjectStr(key: String): String? {
        if (!sp.contains(key)) return null
        val originStr = sp.getString(key, "")
        LogUtil.e("originStr = $originStr")
        return originStr
    }

    fun saveString(key: String, value: String) {
        sp.edit().putString(key, value).apply()
    }

    fun getString(key: String): String {
        return if (sp.contains(key)) sp.getString(key, "")!! else ""
    }
}