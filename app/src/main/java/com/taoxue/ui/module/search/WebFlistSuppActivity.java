package com.taoxue.ui.module.search;

import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.taoxue.app.Constants;
import com.taoxue.app.BaseApplication;
import com.taoxue.ui.module.yuejia.ResourceDetailActivity;
import com.taoxue.utils.LogUtils;

/**
 * 供应商
 */
public class WebFlistSuppActivity extends WebBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String gys_id = bundle.getString("id");
        String dr_data_id = bundle.getString("dr_data_id");
        String name = bundle.getString("name");

        topBar.setTitle(name);
        mWebView.loadUrl(Constants.WEB_SUPPLIER_FLLIST_URL + "?id=" + gys_id + "&dr_data_id=" + dr_data_id + "&user_id=" + BaseApplication.get().getUserId());
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
                    case 2://详情
                        launch(ResourceDetailActivity.class, value, cgs_id);
                        break;
                }

            }
        });
    }
}
