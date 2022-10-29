package com.fushaolei.android.bean

import androidx.databinding.BaseObservable
import com.fushaolei.android.getObjectFromCache
import com.fushaolei.android.utils.CacheUtil

class Configuration : BaseObservable() {

    var githubToken = ""

    companion object {
        private lateinit var mConfiguration: Configuration

        /**
         * 从缓存中拿数据
         */
        fun get(): Configuration {
            val cacheData: Configuration? =
                getObjectFromCache<Configuration>(Configuration::class.java.name)
            return if (cacheData == null) {
                mConfiguration = Configuration()
                mConfiguration
            } else cacheData
        }

        /**
         * 设置并存入缓存中
         */
        fun set(configuration: Configuration) {
            CacheUtil.saveObject(Configuration::class.java.name, configuration)
            mConfiguration = configuration
        }

    }
}