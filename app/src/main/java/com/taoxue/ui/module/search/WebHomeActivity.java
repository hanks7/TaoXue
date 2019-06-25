package com.taoxue.ui.module.search;

import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.taoxue.app.Constants;
import com.taoxue.ui.module.home.NormalListViewActivity;
import com.taoxue.ui.module.yuejia.ResourceDetailActivity;
import com.taoxue.utils.LogUtils;

public class WebHomeActivity extends WebBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int tag = (int) getIntentKey1();
        switch (tag){
            case 0:
                topBar.setTitle("读绘本");
                mWebView.loadUrl(Constants.WEB_DUHUIBEN_URL);
                break;
            case 1:
                topBar.setTitle("最爱听");
                mWebView.loadUrl(Constants.WEB_ZUIAITING_URL);
                break;
            case 2:
                topBar.setTitle("有好书");
                mWebView.loadUrl(Constants.WEB_YOUHAOSHU_URL);
                break;
            case 3:
                topBar.setTitle("超实惠");
                mWebView.loadUrl(Constants.WEB_CHAOSHIHUI_URL);
                break;
            case 4:
                topBar.setTitle("都在看");
                mWebView.loadUrl(Constants.WEB_DOUZAIKAN_URL);
                break;
            case 5:
                topBar.setTitle("宝宝静听");
                mWebView.loadUrl(Constants.WEB_BAOBAOJINTING_URL);
                break;
            case 6:
                topBar.setTitle("少年博览");
                mWebView.loadUrl(Constants.WEB_DSHAO_NIAN_BO_LAN_URL);
                break;
            case 7:
                topBar.setTitle("豆蔻年华");
                mWebView.loadUrl(Constants.WEB_DONGKOUNIANHUA_URL);
                break;
            case 9:
                topBar.setTitle("帮助反馈");
                mWebView.loadUrl(Constants.WEB_HELPANDFK_URL);
                break;

        }
    }

    /**
     * 由于安全原因 targetSdkVersion>=17需要加 @JavascriptInterface
     * JS调用Android JAVA方法名和HTML中的按钮 onclick后的别名后面的名字对应
     */
    @JavascriptInterface
    public void intentWithData(final String type, final String value,final String cgs_id) {
        LogUtils.i(type + "    " + value);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (Integer.valueOf(type)) {
                    case 1://列表
                        launch(NormalListViewActivity.class, value,this.getClass().getSimpleName());
                        break;
                    case 2://详情
                        launch(ResourceDetailActivity.class, value,cgs_id);
                        break;
                    case 3://跳转到资源供应商首页
                        launch(WebSupplierHomeActivity.class, value);
                        break;
                }
            }
        });
    }
}
