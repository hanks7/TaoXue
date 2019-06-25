package com.taoxue.ui.module.login;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAgreementActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;

    private  String url="http://117.71.57.47:11000/frontend/mt/member/user/protocol.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);
        ButterKnife.bind(this);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
}
