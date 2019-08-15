package com.taoxue.ui.module.search;

import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.taoxue.app.BaseApplication;
import com.taoxue.app.Constants;
import com.taoxue.ui.module.home.NormalListViewActivity;
import com.taoxue.ui.module.yuejia.ResourceDetailActivity;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.UtilIntent;


public class WebLibHomeActivity extends WebBaseHomeActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String cgs_id = (String) getIntentKey1();
        String user_id = BaseApplication.get().getUserModel().getUser_id();
        mWebView.loadUrl(Constants.WEB_LIBRARY_URL + "&id=" + cgs_id + "&user_id=" + user_id);
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
                    case 1://列表 value表示的是分类名称
                        launch(NormalListViewActivity.class, value, this.getClass().getSimpleName());
                        break;
                    case 2://详情 资源id 采购商id
                        launch(ResourceDetailActivity.class, value,cgs_id);
                        break;
                    case 3://跳转到资源供应商首页 供应商id
                        launch(WebSupplierHomeActivity.class, value);
                        break;
                }

            }
        });
    }

    @JavascriptInterface
    public void intentFlist(final String id, final String dr_data_id, final String name) {
        LogUtils.i("intentFlist  " + id + "    " + dr_data_id + "    " + name);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("dr_data_id", dr_data_id);
                bundle.putString("name", name);
                UtilIntent.intentDIY(mActivity, WebFlistLib2Activity.class, bundle);
            }
        });
    }

}
