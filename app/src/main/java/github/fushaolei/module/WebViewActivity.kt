package github.fushaolei.module

import android.os.Handler
import android.util.Log
import android.webkit.WebView
import com.google.android.material.appbar.MaterialToolbar
import github.fushaolei.R
import github.fushaolei.base.BaseActivity
import github.fushaolei.base.BasePresenter

class WebViewActivity : BaseActivity<BasePresenter<Any>>() {
    private lateinit var toolbar: MaterialToolbar
    private lateinit var webview: WebView
    private lateinit var path: String


    override fun initialize() {
        toolbar = findViewById(R.id.tool_bar)
        webview = findViewById(R.id.web_view)

        webview.settings.javaScriptEnabled = true
        webview.settings.blockNetworkImage = false

        toolbar.setNavigationOnClickListener { finish() }
        var intent = intent
        if (intent != null) {
            path = intent.getStringExtra("path").toString()
            Log.e("path =>", path)
        }
    }

    override fun initView() {
        Handler().postDelayed({ webview.loadUrl(path) }, 500)
    }

    override fun initPresenter(): BasePresenter<Any>? {
        return null
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_webview
    }

}