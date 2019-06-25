package com.taoxue.ui.module.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.taoxue.MainActivity;
import com.taoxue.R;
import com.taoxue.app.Constants;
import com.taoxue.app.TaoXueApplication;
import com.taoxue.base.BaseActivity;
import com.taoxue.ui.module.login.welcome.WelcomeActivity;
import com.taoxue.ui.module.search.HomeLibActivity;
import com.taoxue.ui.module.yuejia.ResourceDetailActivity;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.StatusBarCompat;
import com.taoxue.utils.Ulog;
import com.taoxue.utils.UtilIntent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/22.
 *
 * @author yysleep
 */
public class PushActivity extends BaseActivity {
    @BindView(R.id.push_time_tv)
    TextView tvTime;
    int time = 1;
    final int totalCount = 5;
    boolean skiped;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        StatusBarCompat.getStatusBarHeight(this);
    }

    @Override
    protected void onInit() {
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            if (uri != null) {

                String host = uri.getHost();
                String dataString = intent.getDataString();
                final String id = uri.getQueryParameter("resource_id");
                final String cgs_id = uri.getQueryParameter("cgs_id");
                String path = uri.getPath();
                String path1 = uri.getEncodedPath();
                String queryString = uri.getQuery();

                LogUtils.i("host:", host);
                LogUtils.i("dataString:", dataString);
                LogUtils.i("id:", id);
                LogUtils.i("path:", path);
                LogUtils.i("path1:", path1);
                LogUtils.i("queryString:", queryString);
                if (host.equals("schemedemo")) {
                    launch(ResourceDetailActivity.class, id);finish();
                } else if (host.equals("libaryunited")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("ids", cgs_id);
                    UtilIntent.intentDIYLeftToRight(mActivity, HomeLibActivity.class, bundle);
                }
            }
        } else {
            setNext();
        }
    }

    /**
     * 跳转到下一个界面
     */
    private void setNext() {
//        if (Constants.IS_TEST) {
//            UtilIntent.intentDIYLeftToRight(this, MainActivity.class);
//            finish();
//            return;
//        }
        tvTime.setText(String.valueOf(String.valueOf(totalCount)));
        startCount();
    }

    private void startCount() {
        getWindow().getDecorView().postDelayed(runnable, 1000);
    }

    @OnClick(R.id.ui_push_skip)
    public void onClick(View view) {
        skiped = true;
        next();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (skiped)
                return;
            tvTime.setText(String.valueOf(String.valueOf(totalCount - time)));
            time++;
            if (time > totalCount) {
                next();
            } else {
                startCount();
            }
        }
    };

    private void next() {
        if (Constants.IS_TEST) {
            UtilIntent.intentDIYLeftToRight(this, WelcomeActivity.class);
            finish();
            return;
        }
        UtilIntent.intentDIYLeftToRight(this,
                TaoXueApplication.get().isFirstEnter() ? WelcomeActivity.class : MainActivity.class,
                android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    //跳转页面
    private void activityToDetail(Class clazz, String id, String type) {
        Ulog.i("activityToDetail", "clazz--" + clazz + "id--" + id + "type--" + type);
        launch(clazz, id);
    }
}
