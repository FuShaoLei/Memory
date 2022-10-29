package com.fushaolei.android.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.fushaolei.android.R
import com.fushaolei.android.base.BaseActivity
import com.fushaolei.android.databinding.ActivityMainBinding
import com.fushaolei.android.start

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.tvSetting.setOnClickListener { start<ConfigurationActivity>(this) }

    }
}