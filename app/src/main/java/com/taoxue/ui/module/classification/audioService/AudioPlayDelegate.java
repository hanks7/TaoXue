package com.taoxue.ui.module.classification.audioService;

import android.media.MediaPlayer;

/**
 * Created by User on 2017/8/8.
 */

public  interface AudioPlayDelegate {
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
//      void sendOnlyNotification();
      void  play(String url);
      void  setGprsPlay(int gprsPlayType);//设置数据流量是否可以播放
      void  setNetType(int netType);//0 表示无网络   1：//表示wifi, 2:表示gprs




}
