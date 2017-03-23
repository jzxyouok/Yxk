package com.huilianonline.yxk.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huilianonline.yxk.R;

/**
 * Created by admin on 2017/3/23.
 */
public class H5Activity extends BaseActivity implements View.OnClickListener {

    public WebView mWebview;
    private ProgressBar progressbar_hori_webview;// webview 加载的进度
    private String url = "http://www.sunrise-china.com/aftersalessupport.html";
    private View back;
    private TextView txtTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shouhou_baozhang);
        initView();
    }

    private void initView() {
        progressbar_hori_webview = (ProgressBar) findViewById(R.id.progressbar_hori_webview);
        progressbar_hori_webview.setProgress(0);
        mWebview = (WebView) findViewById(R.id.web_content);
        WebSettings webseting = mWebview.getSettings();
        webseting.setDefaultTextEncodingName("utf-8");
        // 支持JavaScript
        webseting.setJavaScriptEnabled(true);
        webseting.setBuiltInZoomControls(true);
        webseting.setSupportZoom(true);
        // 支持保存数据
        webseting.setSaveFormData(false);
        webseting.setDomStorageEnabled(true);
        String appCacheDir = getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        webseting.setAppCachePath(appCacheDir);
        webseting.setLoadWithOverviewMode(true);
        webseting.setUseWideViewPort(true);
        webseting.setDatabaseEnabled(true);
        webseting.setAllowFileAccess(true);
        webseting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webseting.setLoadsImagesAutomatically(true);
        mWebview.setWebChromeClient(new chooseProductWebChromeClient());
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebview.loadUrl(url);
        back = findViewById(R.id.ll_title_common_back);
        back.setOnClickListener(this);
        txtTitle = (TextView) findViewById(R.id.tv_title_text);
        txtTitle.setText("售后保障");
    }

    @Override
    public void onClick(View v) {
        if (v == back) {
            finish();
        }
    }

    /**
     * 加载进度显示
     */
    class chooseProductWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (100 == newProgress) {
                progressbar_hori_webview.setVisibility(View.GONE);
            } else {
                progressbar_hori_webview.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    }

    // 覆盖onKeydown 添加处理WebView 界面内返回事件处理
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
            mWebview.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
