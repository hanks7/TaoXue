package com.taoxue.ui.module.classification.audioService;

import com.taoxue.ui.module.classification.CommitContent;
import com.taoxue.ui.module.classification.MessageEvent;
import com.taoxue.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by User on 2017/8/8.
 */

 class ViewUpdateManager implements ViewUpdateDelegate{
    private boolean isPlayBefore;
    CurrentPlayPositionCallBack playPositionCallBack;
    PlayingInfoCallBack playInfo;

    @Override
    public void setPlaypositionCallBack(CurrentPlayPositionCallBack playPositionCallBack) {
        this.playPositionCallBack=playPositionCallBack;
    }

    @Override
    public void setPlayInfoCallBack(PlayingInfoCallBack playInfo) {
        this.playInfo=playInfo;
    }

    @Override
    public void setPlayBefore(boolean playBefore) {
        this.isPlayBefore=playBefore;
    }

    @Override
    public void audioPlayException(String exceptionMsg) {
        sendPlayStateInfo(CommitContent.AUDIO_PLAY_EXCEPTION_CODE,exceptionMsg);
    }

    @Override
    public void audioPlayException(int code, String msg) {
        sendPlayStateInfo(code,msg);
    }

    @Override
    public void sendAudioDuration(long duration) {
        sendPlayStateInfo(CommitContent.AUDIO_TOTAL_DURATION_CODE,duration+"");
//        sendCurrentAudioDurtion(duration);
    }



    @Override
    public void sendStop() {
        sendPlayStateInfo(CommitContent.AUDIO_PLAY_STATE_CODE,CommitContent.STOP_MSG);
    }

    @Override
    public void sendPlaying() {
        sendPlayStateInfo(CommitContent.AUDIO_PLAY_STATE_CODE,CommitContent.PLAY_MSG);
    }

    @Override
    public void sendPause() {
        sendPlayStateInfo(CommitContent.AUDIO_PLAY_STATE_CODE,CommitContent.PAUSE_MSG);
    }

    @Override
    public void sendAudioProgress(int progress) {
        sendcurrentAudioProgress(progress);
    }



    @Override
    public void sendPlayIndex(int index) {
        sendPlayStateInfo(CommitContent.AUDIO_PLAY_INDEX_CODE,index+"");
    }

    @Override
    public void sendNoPreAudio() {
        sendPlayStateInfo(CommitContent.AUDIO_PLAY_INFO_SHOW_CODE,"没有上一集");
    }

    @Override
    public void sendNoNextAudio() {
        sendPlayStateInfo(CommitContent.AUDIO_PLAY_INFO_SHOW_CODE,"没有下一集");
    }


    @Override
    public void sendNoAudioUrl() {
        sendPlayStateInfo(CommitContent.AUDIO_URLPATH_EXCEPTION_CODE,CommitContent.NO_AUDIO_URL);
    }

    @Override
    public void successGetAudioInfo() {
        sendPlayStateInfo(CommitContent.AUDIO_PLAY_GET_AUDIO_INFO_CODE,CommitContent.AUDIO_OK);
    }

    @Override
    public void noEnableNet() {
        sendPlayStateInfo(CommitContent.AUDIO_NET_CODE,CommitContent.NO_ENABLE_NET_MSG);
    }

    @Override
    public void noWifiNet() {
        sendPlayStateInfo(CommitContent.AUDIO_NET_CODE,CommitContent.NO_WIFI_NET_MSG);
    }

    @Override
    public void sendUpdateProgress(int pro) {
        sendPlayStateInfo(CommitContent.AUDIO_UPDATE_PROGRESS_CODE,pro+"");
    }

    //向播放页面发送播放状态信息
    private void sendPlayStateInfo(int code,String playState) {
        if (isPlayBefore ) {
            if (playInfo!=null){
                try{
                  playInfo.playInfo(code,playState);
//                    playInfo.playCode(code);
                }catch (Exception e){
                sendPlayStateInfo(CommitContent.AUDIO_REOTEEXCETION_CODE,"播放异常");
                };

            }
        }
    }

    private void sendcurrentAudioProgress(int progress) {//发送音频进度信息
        if (isPlayBefore) {
            if (playPositionCallBack!=null){
                try{
                    playPositionCallBack.updateCurrentPlayPosition(progress);
                }catch (Exception e){
                    sendPlayStateInfo(CommitContent.AUDIO_REOTEEXCETION_CODE,"播放异常");
                };
            }
        }
    }
}
