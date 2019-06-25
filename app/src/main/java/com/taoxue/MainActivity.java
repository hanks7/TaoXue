package com.taoxue;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.taoxue.app.TaoXueApplication;
import com.taoxue.base.BaseActivity;
import com.taoxue.base.BaseFragment;
import com.taoxue.ui.module.classification.ClassificationFragment;
import com.taoxue.ui.module.classification.CommitContent;
import com.taoxue.ui.module.classification.MessageEvent;
import com.taoxue.ui.module.classification.audioService.IAudioManager;
import com.taoxue.ui.module.home.HomeFragment;
import com.taoxue.ui.module.home.ZXingActivity;
import com.taoxue.ui.module.mine.MineFragment;
import com.taoxue.ui.module.yuejia.SpecialFragment;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.SPUtil;
import com.taoxue.utils.permission.PermissionReq;
import com.taoxue.utils.permission.PermissionResultTask;
import com.taoxue.utils.update.UtilUpdate;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.taoxue.ui.module.setting.SettingActivity.IS_SETTING_AUTO_UPDATED;

public class MainActivity extends BaseActivity {


    BaseFragment fragOne;
    BaseFragment fragTwo;
    BaseFragment fragThree;
    BaseFragment fragFour;

    List<Fragment> fragments;
    Fragment currentFragment;

    @BindView(R.id.ac_main_1)
    ImageView acMain1;
    @BindView(R.id.ac_main_2)
    ImageView acMain2;
    @BindView(R.id.ac_main_3)
    ImageView acMain3;
    @BindView(R.id.ac_main_4)
    ImageView acMain4;
    private long mHomekeyTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tao_xue);
        ButterKnife.bind(this);
        update();//检查更新
//        attemptToBindService();//注册音频Service
    }




    /**
     * 提示更新
     */
    private void update() {
        if (!(boolean) SPUtil.get(IS_SETTING_AUTO_UPDATED, true)) return;//是否自动更新
        UtilUpdate.judgeUpdate(this, null);
    }

    @Override
    protected void onInit() {
        fragments = new ArrayList<>();
        //初始化Fragment
        fragments.add(fragOne = (HomeFragment) Fragment.instantiate(this, HomeFragment.class.getName()));
        fragments.add(fragTwo = (ClassificationFragment) Fragment.instantiate(this, ClassificationFragment.class.getName()));
        fragments.add(fragThree = (SpecialFragment) Fragment.instantiate(this, SpecialFragment.class.getName()));
        fragments.add(fragFour = (MineFragment) Fragment.instantiate(this, MineFragment.class.getName()));
        currentFragment = fragOne;
        switchFragment(null, fragOne);

    }


    /**
     * 切换Fragment
     *
     * @param from
     * @param to
     */
    private void switchFragment(Fragment from, Fragment to) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (from == null) {
            transaction.add(R.id.frag_container, to);
        } else {
            if (!getSupportFragmentManager().getFragments().contains(to)) {
                transaction.add(R.id.frag_container, to);
            }
            transaction.hide(from);
            transaction.show(to);
        }
        transaction.commitAllowingStateLoss();
    }

    @OnClick({R.id.home_fral_one, R.id.home_fral_two, R.id.home_fral_three, R.id.home_fral_four, R.id.button3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_fral_one:
                goToFragment(0);
                break;
            case R.id.home_fral_two:
                goToFragment(1);
                break;
            case R.id.home_fral_three:
                goToFragment(2);
                break;
            case R.id.home_fral_four:
                goToFragment(3);
                break;
            case R.id.button3:
                PermissionReq.with(this)
                        .permissions(Manifest.permission.CAMERA)
                        .result(new PermissionResultTask(Manifest.permission.CAMERA, this) {
                            @Override
                            public void onGranted() {
                                Intent intent = new Intent(MainActivity.this, ZXingActivity.class);
                                startActivityForResult(intent, 103);
                            }
                        })
                        .request();
                break;
        }
    }
    /**
     * 跳转到相应的fragment;
     *
     * @param position
     */
    public void goToFragment(int position) {
        switch (position) {
            case 0:
                acMain1.setImageResource(R.mipmap.footer_sy_active);
                acMain2.setImageResource(R.mipmap.footer_fl);
                acMain3.setImageResource(R.mipmap.footer_y);
                acMain4.setImageResource(R.mipmap.footer_my);


                if ((System.currentTimeMillis() - mHomekeyTime) < 1000) {
                    EventBus.getDefault().post(new MessageEvent(""));
                }
                mHomekeyTime = System.currentTimeMillis();
                break;
            case 1:
                acMain1.setImageResource(R.mipmap.footer_sy);
                acMain2.setImageResource(R.mipmap.footer_fl_active);
                acMain3.setImageResource(R.mipmap.footer_y);
                acMain4.setImageResource(R.mipmap.footer_my);
                break;
            case 2:
                acMain1.setImageResource(R.mipmap.footer_sy);
                acMain2.setImageResource(R.mipmap.footer_fl);
                acMain3.setImageResource(R.mipmap.footer_y_active);
                acMain4.setImageResource(R.mipmap.footer_my);
                break;
            case 3:
                acMain1.setImageResource(R.mipmap.footer_sy);
                acMain2.setImageResource(R.mipmap.footer_fl);
                acMain3.setImageResource(R.mipmap.footer_y);
                acMain4.setImageResource(R.mipmap.footer_my_active);
                break;
        }
        switchFragment(currentFragment, fragments.get(position));
        if (fragments.size() == position + 1) {
            CommitContent.isLogin(MainActivity.this);
        }

        currentFragment = fragments.get(position);
    }


    /**
     * 扫尾二维码回调放回
     *
     * @param requestCode
     * @param resultCode
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
//        showToast(resultCode + "");
    }


    private long mkeyTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mkeyTime) > 2000) {
                mkeyTime = System.currentTimeMillis();
                showToast("再按一次退出程序");
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }


    /**
     * 尝试与服务端建立连接
     */
    IAudioManager mAudioManager;
    boolean mBound = false;

    private void attemptToBindService() {
        Intent intent = new Intent();
        intent.setAction("com.taoxue.aidl");//注册中使用
        intent.setPackage("com.taoxue");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mAudioManager = IAudioManager.Stub.asInterface(service);
            LogUtils.D("service 链接了");
            if (mAudioManager != null) {
                mBound = true;
                TaoXueApplication.audioManager = mAudioManager;
                LogUtils.D("mAudioManager---->" + mAudioManager);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    public void stopAudioService() {
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
    }


}
