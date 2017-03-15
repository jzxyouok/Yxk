package com.huilianonline.yxk.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.huilianonline.yxk.R;

/**
 * Created by admin on 2017/3/13.
 */
public class ShopDetailsActivity extends BaseActivity {

    public WebView mWebview;
    private ProgressBar progressbar_hori_webview;// webview 加载的进度
    private String url = "http://www.baidu.com";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
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
        mWebview.loadUrl(url);

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
}
