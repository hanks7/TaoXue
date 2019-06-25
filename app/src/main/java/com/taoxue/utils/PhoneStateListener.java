package com.taoxue.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import com.taoxue.ui.module.classification.CommitContent;
import com.taoxue.ui.module.classification.audioService.AudioPlayDelegate;

/**
 * Created by User on 2017/5/2.
 * 音乐播放时
 * 来电监听
 */

public interface PhoneStateListener {
       void callStateRinging(String phone); //等待接电话
       void callStateIdle();//电话挂断
       void callStateOffHook();//通话中

}
