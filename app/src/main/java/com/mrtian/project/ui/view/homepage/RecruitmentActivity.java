package com.mrtian.project.ui.view.homepage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mrtian.project.R;
import com.mrtian.project.ui.view.base.BaseActivity;
import com.mrtian.project.utils.LogUtil;

public class RecruitmentActivity extends BaseActivity {
    public static final String EXTRA_ACTIVITY_TITLE = "Title";
    private final String TAG = "RecruitmentActivity";
    private WebView webView;
    private String uri = "http://www.hotjob.cn/wt/zhaolian/web/index";

    public static Intent newIntent(Context packageContext, String title) {
        Intent intent = new Intent(packageContext, RecruitmentActivity.class);
        intent.putExtra(EXTRA_ACTIVITY_TITLE, title);
        return intent;
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        setTitleName("" + getIntent().getStringExtra(EXTRA_ACTIVITY_TITLE));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_company_evaluation);
        init();
    }

    private void init() {
        webView = (WebView) findViewById(R.id.web_view);
        //webView支持JS
        webView.getSettings().setJavaScriptEnabled(true);
        //webView自适应屏幕
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        //设置页面放大缩小
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl(uri);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.d(TAG, url);
                view.loadUrl(url);
                return true;
            }
        });
    }
}
