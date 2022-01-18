package github.fushaolei.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcelable
import github.fushaolei.app.App

@SuppressLint("StaticFieldLeak")
object CacheUtil {
    private var context = App.getContext()
    private var sp = context.getSharedPreferences("sp", Context.MODE_PRIVATE)

    fun save(key: String, value: String) {
        sp.edit().putString(key, value).apply()
    }

    fun get(key: String): String? {
        return sp.getString(key, null)
    }

}