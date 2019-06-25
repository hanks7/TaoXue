package com.taoxue.ui.module.classification.audioService;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.taoxue.ui.module.classification.CommitContent;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.PhoneListener;
import com.taoxue.utils.PhoneStateListener;
import com.taoxue.utils.SPUtil;

import java.io.FileDescriptor;
import java.util.List;


/**
 * Created by User on 2017/4/13.
 */

public class AudioPlayService extends Service {
    private MediaPlayer mediaPlayer;      //媒体播放器对象
    private MyPlayHelp myPlayHelp;
    public static final String EXIT_MEDIA_PLAY_STATE = "exitMediaPlayState";//退出时音频播放状态
    public static final String EXIT_MEDIA_PLAY_INDEX = "exitPlayIndex";//退出时播放下标
    public static final String EXIT_MEDIA_PLAY_RESOURCE_ID = "exitMediaResource_id";//退出时资源id
    public static final String EXIT_MEDIA_PLAY_PROGRESS_POSITION = "exitMediaProgressPosition";//退出时资源id

    private Context context;

 public  final   IAudioManager.Stub iAudioManagerInterface =new IAudioManager.Stub(){
         @Override
         public void play() throws RemoteException {
             myPlayHelp.play();
         }

       @Override
       public void playPre() throws RemoteException {
             myPlayHelp.playPre();
       }

       @Override
       public void playNext() throws RemoteException {
               myPlayHelp.playNext();
       }

       @Override
       public void pause() throws RemoteException {
               myPlayHelp.pause();
       }

       @Override
       public void stop() throws RemoteException {
              myPlayHelp.stop();
       }

       @Override
       public void playOrPause() throws RemoteException {

           LogUtils.D("播放或暂停执行");
            myPlayHelp.playOrPause();
       }

       @Override
       public void stopThread() throws RemoteException {
                 myPlayHelp.stopThread();
       }

       @Override
       public void onServiceDestroy() throws RemoteException {
                    myPlayHelp.onServiceDestroy();
       }

       @Override
       public void sendOnlyNotification() throws RemoteException {
//                  myPlayHelp.sendOnlyNotification();
       }

       @Override
       public Audio getAudio() throws RemoteException {
           return null;
       }

       @Override
       public void playByIndex(int index) throws RemoteException {
                 myPlayHelp.playByIndex(index);
       }

       @Override
       public void addAudio(Audio audio) throws RemoteException {

       }

       @Override
       public void addResource(Resource resource) throws RemoteException {
           LogUtils.D("TIANJIA");
           myPlayHelp.getAudioUrlManager().setAudioInfo(resource);
       }

       @Override
       public void playByProgressPosition(int progressPosition) throws RemoteException {
                 myPlayHelp.playByProgressPosition(progressPosition);
       }

       @Override
       public void playByUrl(String url) throws RemoteException {
            myPlayHelp.play(url);
       }

       @Override
       public void setGprsPlay(int gprsPlayType) throws RemoteException {
              myPlayHelp.setGprsPlay(gprsPlayType);
       }

       @Override
       public void setNetType(int netType) throws RemoteException {
                myPlayHelp.setNetType(netType);
       }

     @Override
     public void addAudios(List<Audio> audios) throws RemoteException {
         LogUtils.D("SERVICE中添加 audios");
            myPlayHelp.getAudioUrlManager().setAudios(audios);
     }

     @Override
     public void setPlayBefore(boolean playBefore) throws RemoteException {//进入前台或后台
         myPlayHelp.getAudioUrlManager().setPlayBefore(playBefore);
     }

     @Override
     public int getCurrentPlayProgress() throws RemoteException {
         return getCurrentPlayProgress();
     }

     @Override
     public void unRegisterPlayPositionCallback(CurrentPlayPositionCallBack playposition) throws RemoteException {
          if (playposition!=null){
              mCallBacks.unregister(playposition);
              myPlayHelp.viewUpdateManager.setPlaypositionCallBack(null);
          }
     }

     @Override
     public void registerPlayPositionCallback(CurrentPlayPositionCallBack playposition) throws RemoteException {
        if (playposition!=null) {
            mCallBacks.register(playposition);
            myPlayHelp.viewUpdateManager.setPlaypositionCallBack(playposition);
        }
     }

     @Override
     public void registerPlayingInfoCallBack(PlayingInfoCallBack playInfo) throws RemoteException {
          if (playInfo!=null){
              mInfoCallBacks.register(playInfo);
              myPlayHelp.viewUpdateManager.setPlayInfoCallBack(playInfo);
          }
     }

     @Override
     public void unRegisterPlayingInfoCallBack(PlayingInfoCallBack playInfo) throws RemoteException {
         if (playInfo!=null){
             mInfoCallBacks.unregister(playInfo);
             myPlayHelp.viewUpdateManager.setPlayInfoCallBack(null);
         }
     }

     @Override
     public void addFloatWindow() throws RemoteException {
//         myPlayHelp.createFloatView();
     }

     @Override
     public void removeFloadWindow() throws RemoteException {
//            myPlayHelp.removeFloatView();
     }
 };

  final   RemoteCallbackList<CurrentPlayPositionCallBack>mCallBacks=new RemoteCallbackList<>();
    final   RemoteCallbackList<PlayingInfoCallBack>mInfoCallBacks=new RemoteCallbackList<>();
//    void callback(int playPosition) {
//        final int N = mCallBacks.beginBroadcast();
//        for (int i=0; i<N; i++) {
//            try {
//                mCallBacks.getBroadcastItem(i).updateCurrentPlayPosition(playPosition);
////                mCallBacks.getBroadcastItem(i).actionPerformed(val);
//            }
//            catch (RemoteException e) {
//                // The RemoteCallbackList will take care of removing
//                // the dead object for us.
//            }
//        }
//        mCallBacks.finishBroadcast();
//    }
    @Override
    public void onCreate() {
        mediaPlayer = new MediaPlayer();
        context = this;
        myPlayHelp = new MyPlayHelp();
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.D("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        LogUtils.D("onbind"+"iAudioManagerInterface---》"+ iAudioManagerInterface);
//        myPlayHelp=new MyPlayHelp();
        return iAudioManagerInterface;
    }


    @Override
    public void onDestroy() {
        LogUtils.D("audioservice-------------------> 执行onDestroy");
        myPlayHelp.onServiceDestroy();
        super.onDestroy();
    }


    public class MyPlayHelp implements AudioPlayDelegate {
        private AudioUrlManager audioUrlManager;
        private ViewUpdateDelegate viewUpdateManager;
        private boolean isLoop = false;              //true 表示播放且页面处在前台
        private int currentProgressPosition = 0;             //音频播放进度
        private long duration;                    //音频总时长
        private boolean isPlayBefore;
        private PhoneListener mPhoneListener;//手机来电监听
        private String prepareAudioUrl = null;//处于准备中的音频url
        private NetWorkListener mNetworkChangeListener;
        private int gprsPlayType = 0;//表示需提醒用户使用GPRS  1：表示用户单次同意使用gprs, 2:表示用户一直同意使用gprs,即不再提醒用户使用gprs;
        private int netType = 0;//0 表示无网络   1：//表示wifi, 2:表示gprs




        private  AudioDiskCache diskCache;

        public void setLoop(boolean loop) {
            isLoop = loop;
        }

        private InnerThread updateThreadprogress; //线程
        private AudioBroadcastReceiver mAudioReceiver;   //音频消息显示

        public String getAudioUrl() {
            return audioUrlManager.getUrl();
        }

        public String getNextAudioUrl() {
            return audioUrlManager.getNextUrl();
        }

        public String getPreAudioUrl() {
            return audioUrlManager.getPreUrl();
        }



        public AudioUrlManager getAudioUrlManager() {
            return audioUrlManager;
        }
      ScreenListener screenListener;
        public MyPlayHelp() {
            viewUpdateManager = new ViewUpdateManager();
            audioUrlManager = new AudioUrlManager(viewUpdateManager, new audioState());
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//音频播放完成监听
                @Override
                public void onCompletion(MediaPlayer mp) {
                    currentProgressPosition = 0;
                    play(getNextAudioUrl());
                }
            });
            mAudioReceiver = new AudioBroadcastReceiver(this, audioUrlManager);
            IntentFilter intentFilter1 = new IntentFilter();
            intentFilter1.addAction(CommitContent.AUDIO_SERVICE_ACTION);
//            intentFilter1.addAction(AudioNotifacation.ACTION_BUTTON);
            intentFilter1.addAction(CommitContent.STOP_AUDIO_SERVICE_ACTION);
            intentFilter1.addAction(CommitContent.SEND_NOTIFICATION_ACTION);
            registerReceiver(mAudioReceiver, intentFilter1); //注册notification播放监听

              mPhoneListener=new PhoneListener(context);
              mPhoneListener.begin(new PhoneStateListener() {
                  @Override
                  public void callStateRinging(String phone) {
                       pause();
                  }

                  @Override
                  public void callStateIdle() {
                      play();
                  }

                  @Override
                  public void callStateOffHook() {

                  }
              });
            mNetworkChangeListener=new NetWorkListener(context);
              mNetworkChangeListener.begin(new NetWorkConnectStateListener() {
                  @Override
                  public void onWifi() {
                      netType=1;
                  }

                  @Override
                  public void onGPRS() {
                    netType=2;
                  }

                  @Override
                  public void onNoNet() {
                     netType=0;
                  }
              });


             screenListener=new ScreenListener(context);
              screenListener.begin(new ScreenStateListener() {
                  @Override
                  public void onScreenOn() {
                    LogUtils.D("开屏");
                    audioUrlManager.setPlayBefore(true);
                    startUpdateThreadProgress();
                  }

                  @Override
                  public void onScreenOff() {
                      LogUtils.D("锁屏");
                      audioUrlManager.setPlayBefore(false);
                  }

                  @Override
                  public void onUserPresent() {
                      LogUtils.D("解锁");
                      audioUrlManager.setPlayBefore(true);
                      startUpdateThreadProgress();
                  }
              });

        }


        AudioManager audioManager = null;
        AudioFocusLisenter afl = null;
        int mAudioFouus = -1;

        /**
         * 判断当前系统是否正在播放音乐或视频
         *
         * @param
         * @return
         */
        private void isPlay() {
            if (audioManager == null) {
                audioManager = (AudioManager) context.getSystemService(AUDIO_SERVICE);
            }
            LogUtils.D("audioManager---->" + audioManager);
            if (afl == null) {
                afl = new AudioFocusLisenter();
            }
            LogUtils.D("afl---->" + afl + "audioManager------>" + audioManager);
            if (audioManager != null) {
                mAudioFouus = audioManager.requestAudioFocus(afl, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                LogUtils.D("mAudioFouus----->" + mAudioFouus);
//            录音完成后释放该焦点。
            }
        }

//        private class AudioDevice implements AudioDeviceCallback{
//            @Override
//            public void onAudioDevicesAdded(AudioDeviceInfo[] addedDevices) {
//                super.onAudioDevicesAdded(addedDevices);
//            }
//
//            @Override
//            public void onAudioDevicesRemoved(AudioDeviceInfo[] removedDevices) {
//                super.onAudioDevicesRemoved(removedDevices);
//            }
//        }





        private class AudioFocusLisenter implements AudioManager.OnAudioFocusChangeListener {
            @Override
            public void onAudioFocusChange(int focusChange) {
                LogUtils.D("focusChange---->" + focusChange);
                if (focusChange == -1) {
                    pause();
                } else {
//                    play();
                }
            }
        }

        @Override
        public void onServiceDestroy() {
                 stop();
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
          mPhoneListener.unregisterListener();

            if (mAudioReceiver != null) {
                unregisterReceiver(mAudioReceiver);
            }

            mNetworkChangeListener.unregisterListener();
             screenListener.unregisterListener();
            stopSelf();
        }

//        @Override
//        public void sendOnlyNotification() {
//            AudioNotifacation.updateAudioNotification(getPlayState());
//        }

        private class audioState implements AudioStateDelegate {
            private boolean isEqualAudio;

            @Override
            public void isPlayBefore(boolean isPlayBefore) {
                viewUpdateManager.setPlayBefore(isPlayBefore);
                MyPlayHelp.this.isPlayBefore = isPlayBefore;
                LogUtils.D("isPlayBefore--->" + isPlayBefore);
                if (isPlayBefore && getPlayState() && updateThreadprogress == null) {
                    LogUtils.D("qiantai bofang ");
                    viewUpdateManager.sendPlaying();
                    startUpdateThreadProgress();
                }else if(isPlayBefore&&!getPlayState()) {
                    LogUtils.D("qiantai bofang, weibofang");
                  viewUpdateManager.sendPause();
                } else {
                    LogUtils.D("qiantai bofang, qita");
                    stopUpdateTHreadProgress();
                }
            }

            @Override
            public void getUrlPath(boolean isAudioUrl) {
                if (isAudioUrl) {
                    if (isEqualAudio) {
                        if (!getPlayState()) {
                            LogUtils.D("执行1");
                            play();
                        } else {
                            viewUpdateManager.sendPlaying();
                            viewUpdateManager.sendPlayIndex(audioUrlManager.getIndex());
                            LogUtils.D("执行2");
                        }
                    } else {
                        play();
                        LogUtils.D("执行3");
                    }
                }
            }

            @Override
            public void getAudioSet(int index, int currentPosition, boolean isEqualAudio) {
                LogUtils.D("isEqualAudio---->" + isEqualAudio);
                this.isEqualAudio = isEqualAudio;
                if (isEqualAudio) {
                    viewUpdateManager.sendAudioDuration(duration);
                    viewUpdateManager.sendPlayIndex(index);
                } else {
                    audioUrlManager.setIndex(0);
                    currentProgressPosition = 0;
                }
            }
        }


        private boolean getPlayState() {
            return mediaPlayer.isPlaying();
        }

        /*
           发消息
         */
        private void sendMsg(int what, Object obj) {
            Message msg = Message.obtain();
            msg.what = what;
            msg.obj = obj;
            LogUtils.D("what" + obj);
            handler.sendMessage(msg);
        }
private  int playPosition;

        public int getPlayPosition() {
            return playPosition;
        }

        public void setPlayPosition(int playPosition) {
            this.playPosition = playPosition;
        }


        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case CURRENT_POSITION: //更新音频进度
                        viewUpdateManager.sendAudioProgress((int)msg.obj);
                        break;
                }
            }
        };

        final static int CURRENT_POSITION = 1;    //消息what  指待当前进度

        private class InnerThread extends Thread {
            @Override
            public void run() {
                while (isLoop) {
                    try {
                        if (getPlayState()) {
                            sendMsg(CURRENT_POSITION, mediaPlayer.getCurrentPosition());
                        }
                        Thread.sleep(998);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * 开启线程
         */
        public synchronized void startUpdateThreadProgress() {
            if (updateThreadprogress == null && isPlayBefore) {
                LogUtils.D("开启线程");
                updateThreadprogress = new InnerThread();
                setLoop(true);
                updateThreadprogress.start();
            }
        }

        public synchronized void stopUpdateTHreadProgress() {
            if (updateThreadprogress != null) {
                setLoop(false);
                updateThreadprogress = null;
            }
        }



        @Override
        public void setNetType(int netType) {
            this.netType=netType;
        }

        @Override
        public void setGprsPlay(int gprsPlayType) {
            this.gprsPlayType = gprsPlayType;
        }






   private  void netTypeDo(){//网络类型判断
       if (prepareAudioUrl != null && prepareAudioUrl == audioUrlManager.getUrl()) {
           LogUtils.D("直接播放");
           startPlay();
       } else {
           viewUpdateManager.sendPlayIndex(audioUrlManager.getIndex());
           if (netType == 0) {//无网络
               viewUpdateManager.noEnableNet();
               if (getPlayState()) {
                   pause();
               }
               viewUpdateManager.sendAudioProgress(0);
               currentProgressPosition = 0;
               return;
           } else if (netType == 2) { //gprs
               if (getPlayState()) {//当播放时暂停
                   pause();
               }
               if (gprsPlayType == 0) {
                   viewUpdateManager.noWifiNet();
                   return;
               } else if (gprsPlayType == 2) {
               } else if (gprsPlayType == 1) {
                   gprsPlayType = 0;
               }
           }
       }
   }

        private void prepareAudio(String url) {//准备音频播放
            diskCache = AudioDiskCache.getInstance(context);
            FileDescriptor mFileDescriptor = diskCache.getFileDescriptor(url);
            if (mFileDescriptor != null) {
                preparePlay(url, mFileDescriptor);
            }else{
                netTypeDo();
                preparePlay(url,null);
            }
        }
        private  void preparePlay(String url,FileDescriptor mFileDescriptor){
            try {
                mediaPlayer.reset();//把各项参数恢复到初始状态
                LogUtils.D("reset");
                if (mFileDescriptor==null) {
                    mediaPlayer.setDataSource(url);
                }else {
                    mediaPlayer.setDataSource(mFileDescriptor);
                    LogUtils.D("从缓存中获取数据源");
                }
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                LogUtils.D("setDataSource");
                mediaPlayer.prepareAsync();  //进行缓冲
                LogUtils.D("currentProgressPosition---->" + currentProgressPosition);
                mediaPlayer.setOnPreparedListener(new PreparedListener(url));//注册一个监听器
                mediaPlayer.setOnErrorListener(new OnMediaErrorListener()); //在播放过程中出现异常的监听
                mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mp, int percent) {
                        LogUtils.D("percent-----》"+percent);
                        viewUpdateManager.sendUpdateProgress(percent);
                    }
                });
            } catch (Exception e) {
                viewUpdateManager.audioPlayException(e.toString());
            }
        }

        /**
         * 实现一个OnPrepareLister接口,当音乐准备好的时候开始播放
         */
        private final class PreparedListener implements MediaPlayer.OnPreparedListener {
                 String url;

            public PreparedListener(String url) {
                this.url = url;
            }

            @Override
            public void onPrepared(MediaPlayer mp) {
                startPlay();

                diskCache.addDiskCache(url);
                prepareAudioUrl = audioUrlManager.getUrl();
            }
        }

        private void startPlay() {
            isLoop = true;
            isPlay();//监听音视频
            LogUtils.D("播放状态------------------------------------------------------------------播放");
            mediaPlayer.start();    //开始播放
//                isPlayRecond = true;
            SPUtil.put(EXIT_MEDIA_PLAY_RESOURCE_ID, audioUrlManager.getAudioInfo().getResource_id() + "");
            duration = mediaPlayer.getDuration();
            viewUpdateManager.sendAudioDuration(duration);//发送总时长
            startUpdateThreadProgress(); //开启线程
            viewUpdateManager.sendPlayIndex(audioUrlManager.getIndex());//播放下标
            LogUtils.D("播放的下标时----------》"+audioUrlManager.getIndex());

            viewUpdateManager.sendPlaying();
            if (currentProgressPosition > 0) {    //如果音乐不是从头播放
                mediaPlayer.seekTo(currentProgressPosition);
            }
//            LogUtils.D("audioUrlManager.getAudioName()---->" + audioUrlManager.getAudioName());
//            AudioNotifacation.sendAudioNotification(AudioPlayService.this, audioUrlManager.getAudioInfo().getResource_picture(), audioUrlManager.getAudioName(), getPlayState());
        }

        private class OnMediaErrorListener implements MediaPlayer.OnErrorListener {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                viewUpdateManager.audioPlayException(CommitContent.MEDIA_PREPARE_EXCEPTION);
                return false;
            }
        }

        @Override
        public void play(String url) {
            if (url == null) return;
            if (url.startsWith("http")) {
                prepareAudio(url);
            } else {
                viewUpdateManager.audioPlayException(CommitContent.AUDIO_URLPATH_EXCEPTION_CODE, CommitContent.NO_AUDIO_URL);
            }
        }


        @Override
        public void play() {
            play(getAudioUrl());
        }

        @Override
        public void pause() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                LogUtils.D("暂停状态------------------------------------------------------------------暂停");
                currentProgressPosition = mediaPlayer.getCurrentPosition();
                mediaPlayer.pause();
                viewUpdateManager.sendPause();
                stopUpdateTHreadProgress();
                if (afl != null && audioManager != null) {
                    audioManager.abandonAudioFocus(afl);//取消音频焦点
                    LogUtils.D("afl--->" + afl);
                }
//                sendOnlyNotification();
            }
        }

        @Override
        public void playByIndex(int index) {
            if (audioUrlManager.getIndex() != index) {
                currentProgressPosition = 0;
                audioUrlManager.setIndex(index);
                play();
            } else {
                if (!getPlayState()) {
                    play();
                }
            }
        }


        @Override
        public void playByProgressPosition(int progressPosition) {
            currentProgressPosition = progressPosition;
            play();
        }


        @Override
        public void stop() {
            LogUtils.D("停止操作");
//            AudioNotifacation.cancelNotification();
            if (mediaPlayer != null) {
                mediaPlayer.pause();
                currentProgressPosition = 0;
                mediaPlayer.stop();
                viewUpdateManager.sendStop();
                stopUpdateTHreadProgress();
//                if (afl != null && audioManager != null) {
//                    audioManager.abandonAudioFocus(afl);//取消音频焦点
//                    LogUtils.D("afl--->" + afl);
//                }
//                try {
//                    mediaPlayer.prepare(); // 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        }

        @Override
        public void playPre() {
            String url = getPreAudioUrl();
            if (url != null) {
                currentProgressPosition = 0;
            }
            play(url);
        }

        @Override
        public void playNext() {
            String url = getNextAudioUrl();
            if (url != null) {
                currentProgressPosition = 0;
            }
            play(url);
        }

        @Override
        public void stopThread() {
            startUpdateThreadProgress();
        }

        @Override
        public void playOrPause() {
            if (getPlayState()) {
                pause();
            } else {
                play();
            }
        }

//        private  void removeFloatView(){
//            if(mFloatLayout != null)          {
//                //移除悬浮窗口
//                      mWindowManager.removeView(mFloatLayout);
//                mFloatLayout=null;
//                mFloatView=null;
//            }
//        }

//        //定义浮动窗口布局
//        LinearLayout mFloatLayout;
//        WindowManager.LayoutParams wmParams;
//        //创建浮动窗口设置布局参数的对象
//          WindowManager mWindowManager;
//        ImageView mFloatView;
//        private static final String TAG = "cc";
//        private void createFloatView()
//          {
//              try{
//              wmParams = new WindowManager.LayoutParams();
//               //获取的是WindowManagerImpl.CompatModeWrapper
//                mWindowManager = (WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);
//                Log.i(TAG, "mWindowManager--->" + mWindowManager);
//            //设置window type
//                wmParams.type = WindowManager.LayoutParams.TYPE_TOAST;
//            //设置图片格式，效果为背景透明
//               wmParams.format = PixelFormat.RGBA_8888;
//                //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
//                  wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//                  //调整悬浮窗显示的停靠位置为左侧置顶
//               wmParams.gravity = Gravity.LEFT | Gravity.TOP;
//                // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
//                  wmParams.x = 50;
//                   wmParams.y = 80;
//
//               //设置悬浮窗口长宽数据
//                 wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
//                  wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//
//                     /*// 设置悬浮窗口长宽数据
//73.        wmParams.width = 200;
//74.        wmParams.height = 80;*/
//
//                 LayoutInflater inflater = LayoutInflater.from(getApplication());
//                  //获取浮动窗口视图所在布局
//                   mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout, null);
//             //添加mFloatLayout
//               mWindowManager.addView(mFloatLayout, wmParams);
//                  Log.i(TAG, "添加mFloatLayout");
//                  mFloatLayout.bringToFront();
//
//                   //浮动窗口按钮
//                   mFloatView = (ImageView)mFloatLayout.findViewById(R.id.float_id);
//
//                  mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
//                           View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
//                       .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//                 Log.i(TAG, "Width/2--->" + mFloatView.getMeasuredWidth()/2);
//                  Log.i(TAG, "Height/2--->" + mFloatView.getMeasuredHeight()/2);
//                 //设置监听浮动窗口的触摸移动
//                   mFloatView.setOnTouchListener(new View.OnTouchListener()
//                {
//
//                          @Override
//                       public boolean onTouch(View v, MotionEvent event)
//                          {
//                               // TODO Auto-generated method stub
//                                 //getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
//                                  wmParams.x = (int) event.getRawX() - mFloatView.getMeasuredWidth()/2;
//                                Log.i(TAG, "RawX" + event.getRawX());
//                               Log.i(TAG, "X" + event.getX());
//                                 //减25为状态栏的高度
//                         wmParams.y = (int) event.getRawY() - mFloatView.getMeasuredHeight()/2 - 25;
//                                Log.i(TAG, "RawY" + event.getRawY());
//                            Log.i(TAG, "Y" + event.getY());
//                                  //刷新
//                                 mWindowManager.updateViewLayout(mFloatLayout, wmParams);
//                                 return false;  //此处必须返回false，否则OnClickListener获取不到监听
//                               }
//                    });
//
//                   mFloatView.setOnClickListener(new View.OnClickListener()
//                {
//
//                    @Override
//                          public void onClick(View v)
//                           {
//                               playOrPause();
//                                  // TODO Auto-generated method stub
//                             Toast.makeText(AudioPlayService.this, "onClick", Toast.LENGTH_SHORT).show();
//                              }
//                   });
//              }catch (Exception e){
//                  Log.i(TAG, "createFloatView: e--->"+e);
//
//              }
//               }


    }
}
