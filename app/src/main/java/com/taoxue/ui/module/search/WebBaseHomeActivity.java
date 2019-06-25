package com.taoxue.ui.module.search;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.EditText;
import android.widget.ImageView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.ui.dialog.LoadingDialog;
import com.taoxue.ui.module.home.ZXingActivity;
import com.taoxue.ui.view.LibPopWinMenu;
import com.taoxue.ui.view.ProgressBarWebView;
import com.taoxue.utils.UtilIntent;
import com.taoxue.utils.permission.PermissionReq;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WebBaseHomeActivity extends BaseActivity {


    @BindView(R.id.lib_home_web_view)
    ProgressBarWebView mWebView;

    LoadingDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib_home_web);
        ButterKnife.bind(this);
        dialog = new LoadingDialog(this);
        init();


    }

    /**
     * 由于安全原因 targetSdkVersion>=17需要加 @JavascriptInterface
     * JS调用Android JAVA方法名和HTML中的按钮 onclick后的别名后面的名字对应
     *
     * window.android.showDialog(1) //打开dialog
     * window.android.showDialog(0) //关闭dialog
     */
    @JavascriptInterface
    public void showDialog(final String type) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (Integer.valueOf(type)) {
                    case 0://关闭dialog
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        break;
                    case 1://打开dialog
                        dialog.show();
                        break;
                }

            }
        });
    }




//*******************************************toolbar初始化************************************
    @BindView(R.id.fa_home_iv_more)
    ImageView mIvMore;
    @BindView(R.id.et_search)
    EditText etSearch;
    private void init() {
        new LibPopWinMenu(this, mIvMore);//初始化popwinMenu
        setFocusableFalse();
        dialog = new LoadingDialog(this);
    }


    /**
     * 输入框不可编辑
     */
    private void setFocusableFalse() {
        etSearch.setFocusable(false);
        etSearch.setFocusableInTouchMode(false);
    }

    @OnClick({
            R.id.fa_home_iv_head,
            R.id.et_search,
            R.id.fa_home_iv_zxing,
            R.id.fa_home_iv_more,
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fa_home_iv_head:
                onBackPressed();
                break;
            case R.id.et_search:
                UtilIntent.intentDIYLeftToRight(this,
                        SearchResourcesActivity.class,
                        android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.fa_home_iv_zxing:
                if (!PermissionReq.judgePermisson(this, Manifest.permission.CAMERA))
                    break;
                Intent intent = new Intent(this, ZXingActivity.class);
                this.startActivityForResult(intent, 103);
                break;
        }
    }

    //*******************************************toolbar初始化************************************

}
