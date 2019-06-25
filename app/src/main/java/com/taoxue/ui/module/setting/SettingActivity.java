package com.taoxue.ui.module.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.app.TaoXueApplication;
import com.taoxue.base.BaseActivity;
import com.taoxue.ui.module.classification.CommitContent;
import com.taoxue.ui.module.login.LoginActivity;
import com.taoxue.ui.module.mine.ModifyPasswordActivity;
import com.taoxue.ui.view.zcw.togglebutton.ToggleButton;
import com.taoxue.utils.SPUtil;
import com.taoxue.utils.UtilSystem;
import com.taoxue.utils.UtilToast;
import com.taoxue.utils.Utildialog;
import com.taoxue.utils.glide.GlideCacheUtil;
import com.taoxue.utils.update.UtilUpdate;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {



    @BindView(R.id.setting_tv_delete)
    TextView mTvDelete;

    @BindView(R.id.setting_tv_version)
    TextView mTvVersion;

    @BindView(R.id.setting_check_update)
    ToggleButton mCheckUpdate;

    @BindView(R.id.setting_check_message_push)
    ToggleButton mCheckMessagePush;

    @BindView(R.id.setting_check_audio)
    ToggleButton mCheckAudio;

    /**
     * 设置页_是否自动更新的标识
     */
    public final static String IS_SETTING_AUTO_UPDATED ="1546546";
    /**
     * 设置页_音效开关
     */
    public final static String  IS_SETTING_AUDIO="1546547";
    /**
     * 设置页_消息推送
     */
    public final static String  IS_SETTING_MESSAGE_PUSH="1546548";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        CommitContent.setColorLeftSilde(this);//设置向右滑动退出
        mTvDelete.setText(GlideCacheUtil.getInstance().getCacheSize(this) + "");
        mTvVersion.setText(UtilSystem.getVersionName());
        isAutoUpate(mCheckUpdate, IS_SETTING_AUDIO);//音效开关
        isAutoUpate(mCheckMessagePush, IS_SETTING_MESSAGE_PUSH);//消息推送
        isAutoUpate(mCheckAudio, IS_SETTING_AUTO_UPDATED);//是否自动更新
    }

    /**
     * 是否自动更新
     */
    private void isAutoUpate(ToggleButton mCheckUpdate, final String tag) {

        mCheckUpdate.setToggleOn((boolean) SPUtil.get(tag, true));
        if ((boolean) SPUtil.get(tag, true)) {
            mCheckUpdate.setToggleOn();
        } else {
            mCheckUpdate.setToggleOff();
        }
        mCheckUpdate.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                SPUtil.put(tag, on);
            }
        });

    }

    @OnClick({
            R.id.setting_rl_delete,
            R.id.setting_rl_use_guide,
            R.id.setting_rl_common_problem,
            R.id.setting_rl_about_us,
            R.id.btn_quit,
            R.id.update_pw
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_rl_delete:
                Utildialog.show(SettingActivity.this, "清理缓存" + GlideCacheUtil.getInstance().getCacheSize(this) + "", new Runnable() {
                    @Override
                    public void run() {
                        GlideCacheUtil.getInstance().clearImageAllCache(SettingActivity.this);
                        mTvDelete.setText("");
                    }
                });
                break;
            case R.id.setting_rl_use_guide:
                break;
            case R.id.setting_rl_common_problem:
                break;
            case R.id.setting_rl_about_us:
                UtilUpdate.judgeUpdate(this, new UtilUpdate.Runnable() {
                    @Override
                    public void run(String str) {
                        UtilToast.showText(str);
                    }
                });
                break;
            case R.id.update_pw://修改密码
                launch(ModifyPasswordActivity.class);
                break;
            case R.id.btn_quit:
                Utildialog.show(this, "退出登录将删除用户信息,确认退出登录吗?", new Runnable() {
                    @Override
                    public void run() {
                        TaoXueApplication.get().quitLogin();
                        launch(LoginActivity.class);
                        finish();
                    }
                });
                break;
        }
    }
}
