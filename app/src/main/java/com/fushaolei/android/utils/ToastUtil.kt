package com.fushaolei.android.utils

import android.widget.Toast
import androidx.annotation.StringRes
import com.fushaolei.android.App

object ToastUtil {

    fun show(@StringRes resId: Int) {
        Toast.makeText(App.appContext, App.appContext.getString(resId), Toast.LENGTH_SHORT).show()
    }
}