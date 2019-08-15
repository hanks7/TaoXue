package com.taoxue.ui.module.mine;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.taoxue.R;
import com.taoxue.app.BaseApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpFeedbackActivity extends AppCompatActivity {
    @BindView(R.id.webView_help)
    WebView wvReaderCard;
    String url="http://192.168.3.213:8020/dulianti/src/member/helpAndFk.html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_feedback);
        ButterKnife.bind(this);
        // 启用javascript
        wvReaderCard.getSettings().setJavaScriptEnabled(true);
        // 从assets目录下面的加载html
        wvReaderCard.loadUrl(url);
        wvReaderCard.addJavascriptInterface(HelpFeedbackActivity.this,"android");
        wvReaderCard.loadUrl("javascript:setUserId("+ BaseApplication.get().getUserId()+")");
//        wvReaderCard.addJavascriptInterface(ContactReaderCardActivity.this,"android");
    }
    @JavascriptInterface
    public void startFunction(final String text){
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                new AlertDialog.Builder(HelpFeedbackActivity.this).setMessage(text).show();

            }
        });


    }

}
