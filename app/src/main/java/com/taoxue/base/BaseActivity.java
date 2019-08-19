package com.taoxue.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.taoxue.app.DialogInterface;
import com.taoxue.ui.module.classification.MessageEvent;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.UtilStatusbar;
import com.taoxue.utils.Ulog;
import com.taoxue.utils.UtilIntent;
import com.taoxue.utils.UtilToast;
import com.taoxue.utils.UtilTools;
import com.taoxue.utils.permission.PermissionReq;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;

import butterknife.ButterKnife;

/**
 * Created by CC on 2016/5/25.
 */

public class BaseActivity extends AppCompatActivity implements DialogInterface {
    public BaseActivity mActivity;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionReq.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setOnTranslucent();
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        EventBus.getDefault().register(this);
        ButterKnife.setDebug(true);
        mActivity = this;
        initDialog();
    }
    /**
     * 启用 透明状态栏(重写此方法可以取消透明)
     */
    protected void setOnTranslucent() {
        UtilStatusbar.enableTranslucentStatusbar(this);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        onInit();
    }

    protected void onInit() {

    }

    public void showToast(String text) {
        UtilToast.showText(text);
    }

    @Override
    public void onBackPressed() {
        dealOnBackPressed();
        super.onBackPressed();
    }

    protected void dealOnBackPressed() {
    }


    @Subscribe
    public void onEventMainThread(String string) {

    }


    @Subscribe
    public void onEventMainThread(MessageEvent event) {

    }

    @Subscribe
    public void onEventMainThread(MessageEvent.Duration event) {

    }

    @Override
    protected void onResume() {
        MobclickAgent.onResume(this);
        Log.i(Ulog.TAG + "activity", "(" + getClass().getSimpleName() + ".java:0)");
        super.onResume();
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public void launch(Class clazz) {
        UtilIntent.intentDIYLeftToRight(this, clazz);
    }

    public void launch(Class clazz, Intent intent) {
        intent.setClass(this, clazz);
        startActivity(intent);
    }


    public void launch(Class clazz, @NonNull Object obj) {
        LogUtils.i("launch", obj);
        Bundle bundle = new Bundle();
        if (obj != null) {
            if (obj instanceof Serializable) {
                bundle.putSerializable(getKek1(clazz), (Serializable) obj);
            } else {
                newThrow();
            }
        }
        UtilIntent.intentDIYLeftToRight(this, clazz, bundle);
    }

    public void launchForResult(Class clazz, int resultCode, @NonNull Object obj1, @NonNull Object obj2) {
        Bundle bundle = new Bundle();
        if (obj1 != null) {
            if (obj1 instanceof Serializable) {
                bundle.putSerializable(getKek1(clazz), (Serializable) obj1);
            } else {
                newThrow();
            }
        }
        if (obj2 != null) {
            if (obj2 instanceof Serializable) {
                bundle.putSerializable(getKek2(clazz), (Serializable) obj2);
            } else {
                newThrow();
            }
        }
        Intent intent = new Intent(this, clazz);
        intent.putExtras(bundle);
        startActivityForResult(intent, resultCode);
    }

    public void launch(Class clazz, @NonNull Object obj1, @NonNull Object obj2) {
        LogUtils.i("launch", obj1 + "     " + obj2);
        Bundle bundle = new Bundle();
        if (obj1 != null) {
            if (obj1 instanceof Serializable) {
                bundle.putSerializable(getKek1(clazz), (Serializable) obj1);
            } else {
                newThrow();
            }
        }
        if (obj2 != null) {
            if (obj2 instanceof Serializable) {
                bundle.putSerializable(getKek2(clazz), (Serializable) obj2);
            } else {
                newThrow();
            }
        }
        UtilIntent.intentDIYLeftToRight(this, clazz, bundle);
    }

    private void newThrow() {
        throw new IllegalArgumentException("argument must is String Or Serializable," +
                "if argument is int or float or double or boolean or double or byte " +
                "you can cast argument to  String");
    }

    public Serializable getIntentKey1() {
        return getIntent().getSerializableExtra(getKek1(getClass()));
    }

    public Serializable getIntentKey2() {
        return getIntent().getSerializableExtra(getKek2(getClass()));
    }

    private String getKek1(Class clazz) {
        return UtilTools.getActivityKey1(clazz);
    }


    private String getKek2(Class clazz) {
        return UtilTools.getActivityKey2(clazz);
    }


    //    将String 为null 值时转化为空
    public String nullToSting(String str) {
        String s = TextUtils.isEmpty(str) ? "" : str.trim();
        if ("null".equals(s)) {
            return " ";
        }
        return s;
    }

    private ProgressDialog dialog;//显示等待的dialog

    private void initDialog() {
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//转盘
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("正在加载，请稍后……");

    }

    @Override
    public void showDialog() {
        if (dialog == null) return;
        dialog.show();
    }

    @Override
    public void dismissDialog() {
        if (dialog == null) return;
        dialog.dismiss();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

}
