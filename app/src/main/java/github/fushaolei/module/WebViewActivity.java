package github.fushaolei.module;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebView;

import com.google.android.material.appbar.MaterialToolbar;

import github.fushaolei.R;
import github.fushaolei.base.BaseActivity;
import github.fushaolei.base.BasePresenter;

/**
 * @Auther: fushaolei
 * @datetime: 2021/2/13
 * @desc:
 */
public class WebViewActivity extends BaseActivity {
    private MaterialToolbar toolbar;
    private WebView webView;
    private String path;

    @Override
    protected void initialize() {
        toolbar = findViewById(R.id.tool_bar);
        webView = findViewById(R.id.web_view);

        webView.getSettings().setJavaScriptEnabled(true);//启用js
        webView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        toolbar.setNavigationOnClickListener((v) -> finish());
        Intent intent = getIntent();
        if (intent != null) {
            path = intent.getStringExtra("path");
            Log.e("path => ", path);
        }
    }

    @Override
    protected void initView() {
        new Handler().postDelayed(() -> webView.loadUrl(path), 500);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }
}
