package com.huilianonline.yxk.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.huilianonline.yxk.MainActivity;
import com.huilianonline.yxk.R;

/**
 * Created by admin on 2017/3/13.
 */
public class ShopDetailsActivity extends BaseActivity implements View.OnClickListener {

    public WebView mWebview;
    private ProgressBar progressbar_hori_webview;// webview 加载的进度
    private String url = "http://yxkservice.huilianonline.com/Product/ProductDetail";
    private TextView txtAddCar;
    private View search;
    private View message;
    private View shopCar;

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
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebview.loadUrl(url);
        txtAddCar = (TextView) findViewById(R.id.txt_add_cart);
        txtAddCar.setOnClickListener(this);
        search = findViewById(R.id.layout_search);
        message = findViewById(R.id.layout_message);
        shopCar = findViewById(R.id.layout_shopCar);
        search.setOnClickListener(this);
        message.setOnClickListener(this);
        shopCar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == txtAddCar) {
            Toast.makeText(ShopDetailsActivity.this,"添加成功！",Toast.LENGTH_SHORT).show();
        } else if (v == search) {
            Intent intent = new Intent();
            intent.setClass(ShopDetailsActivity.this, SearchActivity.class);
            startActivity(intent);
        } else if (v == message) {
            Intent intent = new Intent();
            intent.setClass(ShopDetailsActivity.this, MessageListActivity.class);
            startActivity(intent);
        } else if (v == shopCar) {
            Intent intent = new Intent();
            intent.putExtra("flag_main",2);
            intent.setClass(ShopDetailsActivity.this, MainActivity.class);
            startActivity(intent);
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
