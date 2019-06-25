package com.taoxue.ui.module.classification.audioService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.taoxue.ui.module.classification.CommitContent;
import com.taoxue.utils.AudioNotifacation;
import com.taoxue.utils.LogUtils;

/**
 * Created by User on 2017/5/9.
 */

public class AudioBroadcastReceiver extends BroadcastReceiver {
   private AudioPlayDelegate binder;
    private AudioUrlManager  urlManager;
    public AudioBroadcastReceiver(AudioPlayDelegate  binder,AudioUrlManager  urlManager) {
        this.binder=binder;
        this.urlManager=urlManager;
        LogUtils.D("AudioBroadcastReceiver注册成功");
    }
    //处理通知中的操作
private  void  doAudioNotification(Intent intent){
    String tag=intent.getStringExtra(AudioNotifacation.INTENT_BUTTONID_TAG);
    LogUtils.D("tag--->"+tag);
    if(tag!=null){
        if(tag.equals(AudioNotifacation.BUTTON_PALY_ID)){
            binder.playOrPause();
            LogUtils.D("播放或暂停");
        }else if (tag.equals(AudioNotifacation.BUTTON_DELETE_ID)){
//            AudioNotifacation.cancelNotification();
            binder.stop();
        }
    }
}


    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        LogUtils.D("action"+action+"");
        if(action==null){
            return;
        }
        if (action.equals(AudioNotifacation.ACTION_BUTTON)){
            doAudioNotification(intent);
        }else if (CommitContent.SEND_NOTIFICATION_ACTION.equals(action)){
//            binder.sendOnlyNotification();
        }
    }
}
