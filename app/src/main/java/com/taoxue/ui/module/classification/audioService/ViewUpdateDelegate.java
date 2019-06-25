package com.taoxue.ui.module.classification.audioService;

/**
 * Created by User on 2017/8/8.
 */

interface ViewUpdateDelegate {
    void audioPlayException(String exceptionMsg);
    void audioPlayException(int code,String msg);

    void sendAudioDuration(long duration);//发送音频总时长
    void sendAudioProgress(int progress);
    void sendPlaying();
    void sendPause();
    void sendStop();
    void setPlayBefore(boolean playBefore);
    void sendPlayIndex(int index);

    void sendNoPreAudio();
    void sendNoNextAudio();

    void sendNoAudioUrl();
    void successGetAudioInfo();
    void noEnableNet();//没有网络
    void noWifiNet();//没有wifi 有数据流量
    void sendUpdateProgress(int progress);//设置缓冲进度
    void setPlaypositionCallBack(CurrentPlayPositionCallBack playPositionCallBack);
    void setPlayInfoCallBack(PlayingInfoCallBack playInfo);
}
