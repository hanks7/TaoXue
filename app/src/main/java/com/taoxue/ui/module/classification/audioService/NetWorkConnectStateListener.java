package com.taoxue.ui.module.classification.audioService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;

import com.taoxue.utils.LogUtils;


/**
 * Created by User on 2017/8/14.
 */

public interface NetWorkConnectStateListener {
         void  onWifi();//wifi 打开时
         void  onGPRS();//wifi 关闭，数据流量打开时
        void   onNoNet();//未连接网络时
}
