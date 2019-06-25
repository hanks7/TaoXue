// IAudioManager.aidl
package com.taoxue.ui.module.classification.audioService;

import com.taoxue.ui.module.classification.audioService.Audio;
// Declare any non-default types here with import statements
import com.taoxue.ui.module.classification.audioService.Resource;
import com.taoxue.ui.module.classification.audioService.CurrentPlayPositionCallBack;
import com.taoxue.ui.module.classification.audioService.PlayingInfoCallBack;


interface IAudioManager {
         void addAudio(in Audio audio);
         void addAudios(in List<Audio>audios);
         void addResource(in Resource resource);
         void  play();//播放
         void  playPre();//播放上一首
         void  playNext();//播放下一首
         void  pause();//暂停
         void  playByIndex(int index);//播放指定下标
         void  stop();//停止播放
         void  playOrPause();//暂停或播放
         void  stopThread();//停止线程
         void  playByProgressPosition(int progressPosition);//播放指定下标
         void  onServiceDestroy();
         void sendOnlyNotification();
          Audio getAudio();
         void  playByUrl(String url);
          int  getCurrentPlayProgress();
         void setPlayBefore(boolean playBefore);

         void  setGprsPlay(int gprsPlayType);//设置数据流量是否可以播放
         void  setNetType(int netType);//0 表示无网络   1：//表示wifi, 2:表示gprs
         void registerPlayPositionCallback(in CurrentPlayPositionCallBack playposition);
         void unRegisterPlayPositionCallback(in CurrentPlayPositionCallBack playposition);

         void registerPlayingInfoCallBack(in PlayingInfoCallBack playInfo);
         void unRegisterPlayingInfoCallBack(in PlayingInfoCallBack playInfo);
         void addFloatWindow();
         void removeFloadWindow();
}
