package com.fushaolei.android.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.fushaolei.android.R
import com.fushaolei.android.base.BaseActivity
import com.fushaolei.android.bean.Configuration
import com.fushaolei.android.databinding.ActivityConfigurationBinding
import com.fushaolei.android.utils.CacheUtil
import com.fushaolei.android.utils.LogUtil
import com.fushaolei.android.utils.ToastUtil

/**
 * 配置
 */
class ConfigurationActivity : BaseActivity() {
    private lateinit var binding: ActivityConfigurationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_configuration)

        val mConfig = Configuration.get()
        binding.configData = mConfig

        binding.tvOk.setOnClickListener {
            Configuration.set(mConfig)
            ToastUtil.show(R.string.success)
            finish()
        }
    }
}