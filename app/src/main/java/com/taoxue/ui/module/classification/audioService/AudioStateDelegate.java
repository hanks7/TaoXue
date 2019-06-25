package com.taoxue.ui.module.classification.audioService;

/**
 * Created by User on 2017/8/8.
 */

interface AudioStateDelegate {
    void   isPlayBefore(boolean  isPlayBefore);//判断是否处于前台
    void   getAudioSet(int index,int currentPosition,boolean isEqualAudio);
    void   getUrlPath(boolean isAudioUrl);
}
