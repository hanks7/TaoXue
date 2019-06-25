package com.taoxue.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.TelephonyManager;

import com.taoxue.ui.module.classification.audioService.AudioPlayDelegate;
import com.taoxue.ui.module.classification.CommitContent;

/**
 * Created by User on 2017/5/2.
 * 音乐播放时
 * 来电监听
 */

public class PhoneListener {
    private Context mContext;
    private  PhoneStateListener phoneStateListener;
   private  PhoneBroadcastReceiver phoneBroadcastReceiver;

    public PhoneListener(Context mContext) {
        this.mContext = mContext;
        phoneBroadcastReceiver=new PhoneBroadcastReceiver();
    }

    public  void begin(PhoneStateListener phoneStateListener){
         this.phoneStateListener=phoneStateListener;
        registerListener();
     }
     public  void registerListener(){
         IntentFilter intentFilter = new IntentFilter();
         intentFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
         intentFilter.setPriority(Integer.MAX_VALUE);
         mContext.registerReceiver(phoneBroadcastReceiver, intentFilter);
     }

    /**
     * 停止screen状态监听
     */
    public void unregisterListener() {
        if (phoneBroadcastReceiver!=null){
        mContext.unregisterReceiver(phoneBroadcastReceiver);
        }
    }
    private class PhoneBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
//            LogUtils.D("[Broadcast]" + action);
            //呼入电话
            if (action.equals(CommitContent.B_PHONE_STATE)) {
//                LogUtils.D("[Broadcast]PHONE_STATE");
                doReceivePhone(context, intent);
            }
        }

        /**
         * 处理电话广播.
         *
         * @param context
         * @param intent
         */
        public void doReceivePhone(Context context, Intent intent) {
            String phoneNumber = intent.getStringExtra(
                    TelephonyManager.EXTRA_INCOMING_NUMBER);
            TelephonyManager telephony =
                    (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            int state = telephony.getCallState();
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
//                    LogUtils.D("[Broadcast]等待接电话=" + phoneNumber);
                    phoneStateListener.callStateRinging(phoneNumber);
//                    binder.pause();
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
//                    LogUtils.D("[Broadcast]电话挂断=" + phoneNumber);
                    phoneStateListener.callStateIdle();
//                    binder.play();
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
//                    LogUtils.D("[Broadcast]通话中=" + phoneNumber);
                    phoneStateListener.callStateOffHook();
                    break;
            }
        }
    }
}
