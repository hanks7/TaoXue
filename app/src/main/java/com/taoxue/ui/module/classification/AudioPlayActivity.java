package com.taoxue.ui.module.classification;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;


import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.ui.model.AudioResourceBean;
import com.taoxue.ui.model.StateBean;
import com.taoxue.ui.model.VolumnBean;
import com.taoxue.ui.model.UrlPath;
import com.taoxue.ui.module.classification.audioService.Audio;
import com.taoxue.ui.module.classification.audioService.CurrentPlayPositionCallBack;
import com.taoxue.ui.module.classification.audioService.IAudioManager;
import com.taoxue.ui.module.classification.audioService.PlayingInfoCallBack;
import com.taoxue.ui.module.classification.audioService.Resource;
import com.taoxue.ui.module.yuejia.AudioRcsAdapter;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.Utilshare;
import com.taoxue.utils.glide.UtilGlide;
import com.uuzuche.lib_zxing.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AudioPlayActivity extends BaseActivity {

    @BindView(R.id.tv_topTitle)
    TextView audiplayTopbar;
//    @BindView(R.id.audioplay_author_tv)
//    TextView audioplayAuthorTv;
    @BindView(R.id.audioplay_circleview_iv)
    ImageView audioplayCircleviewIv;
    @BindView(R.id.audioplay_collection_iv)
    ImageView audioplayCollectionIv;
    @BindView(R.id.audioplay_ping_lun_iv)
    ImageView audioplayPingLunIv;
    @BindView(R.id.audioplay_ping_fen_iv)
    ImageView audioplayPingFenIv;
    @BindView(R.id.audioplay_more_iv)
    ImageView audioplayMoreIv;
    @BindView(R.id.audioplay_current_position_tv)
    TextView audioplayCurrentPositionTv;
    @BindView(R.id.audioplay_play_progress_sb)
    SeekBar audioplayPlayProgressSb;
    @BindView(R.id.audioplay_durtion_tv)
    TextView audioplayDurtionTv;
    @BindView(R.id.previous_iv)
    ImageView previousIv;
    @BindView(R.id.play_play_pause_iv)
    ImageView playPauseIv;
    @BindView(R.id.next_iv)
    ImageView nextIv;
    @BindView(R.id.audioplay_ll)
    LinearLayout audioplayLl;

    AudioResourceBean model;
    UrlPath urlPath;
    private boolean isSeekBar = false;
    private int durition;
    private boolean isCollection=false;//是否收藏
    private  boolean isGiveThumbed=false;//是否点赞
    private  boolean  isComment=false;//是否评论
    private List<VolumnBean> urls;
    private String audioUrl;
    double rat=0.7;//
    private  int currentIndex=0;

    private IAudioManager mAudioManager;
    Utilshare utilshare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        Android全屏显示时，状态栏显示在最顶层,不隐藏。
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
////            //透明底部导航栏
////            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_audio_play);
        ButterKnife.bind(this);

        utilshare=new Utilshare(this);
        attemptToBindService();
//        mAudioManager= BaseApplication.audioManager;
        LogUtils.D("mAudioManager---->"+mAudioManager);

    }




    @Override
    protected void onResume() {
        super.onResume();
        sendAudioPlayIsForwardBroacast(true);
    }
    private void newRemoteException(){
        showToast("播放异常");
    }


    /**
     * 尝试与服务端建立连接
     */
    boolean mBound = false;

    private void attemptToBindService() {
        Intent intent = new Intent();
        intent.setAction("com.taoxue.aidl");//注册中使用
        intent.setPackage("com.taoxue");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mAudioManager = IAudioManager.Stub.asInterface(service);
            LogUtils.D("service 链接了");
            if (mAudioManager != null) {
                mBound = true;
                getIntentData();
//                BaseApplication.audioManager = mAudioManager;
//                LogUtils.D("mAudioManager---->" + mAudioManager);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    public void stopAudioService() {
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
    }


    private  void  getIntentData(){
     urlPath=(UrlPath) getIntentKey1();
     model=(AudioResourceBean) getIntentKey2();
        try {
            mAudioManager.registerPlayPositionCallback(playPositionCallBack);
            mAudioManager.registerPlayingInfoCallBack(infoCallBack);
        }catch (Exception e){
            newRemoteException();
        }
     if (urlPath!=null&&urlPath.getUrlModel().size()>0&&model!=null){
         urls=urlPath.getUrlModel().get(0).getVolumn();
         sendAudioInfoBroacast(model);
         audioUrl = urls.get(0).getUrl();
         updateView();
     }else{
         showToast("当前无音频资源");
     }

 }


   private PlayingInfoCallBack infoCallBack=new PlayingInfoCallBack.Stub(){
       @Override
       public void playInfo(int code, String msg) throws RemoteException {
           LogUtils.D("code---->"+code+"--->msg---->"+msg);
            audioPlayState(code,msg);
       }
   };
    private CurrentPlayPositionCallBack playPositionCallBack=new CurrentPlayPositionCallBack.Stub() {
        @Override
        public void updateCurrentPlayPosition(int position) throws RemoteException {
            audioplayCurrentPositionTv.setText(intToTime(position) + "");
            if (durition > 0) {
                int currentProgress = position * 100 / durition;
                audioplayPlayProgressSb.setProgress(currentProgress);
            }
        }
    };


    @Override
    protected void onPause() {
        super.onPause();
    }



    @Override
    protected void onStop() {
        super.onStop();
        sendAudioPlayIsForwardBroacast(false);//发送进入后台信息
    }

    @Override
    protected void onDestroy() {
        if (playPositionCallBack!=null){
            try{
                mAudioManager.unRegisterPlayPositionCallback(playPositionCallBack);
                mAudioManager.unRegisterPlayingInfoCallBack(infoCallBack);
            }catch (Exception e){
                newRemoteException();
            };
        }
        stopAudioService();
        super.onDestroy();
    }
    //获取音频总时长
    @Override
    public void onEventMainThread(MessageEvent.Duration event) {
        LogUtils.D("总时长");
        durition = (int) event.getCurent();
        audioplayDurtionTv.setText(intToTime(durition));
    }
    private void updateView(){
        sendAudioPlayIsForwardBroacast(true);
        if ("1".equals(urlPath.getIs_collection())){
            audioplayCollectionIv.setImageResource(R.mipmap.icon_collection_true);
            isCollection=true;
        }else {
            audioplayCollectionIv.setImageResource(R.mipmap.uncollection);
            isCollection=false;
        }

        if ("1".equals(urlPath.getIs_praise())){
            audioplayPingFenIv.setImageResource(R.mipmap.icon_praise_true);
         isGiveThumbed=true;
        }else{
            audioplayPingFenIv.setImageResource(R.mipmap.give_thumb);
            isGiveThumbed=false;
        }

        if ("1".equals(urlPath.getIs_comment())){
            isComment=true;
        }else{
            isComment=false;
        }

        audioplayCircleviewIv.getLayoutParams().height=(int)(DisplayUtil.screenWidthPx*rat);
        audioplayCircleviewIv.getLayoutParams().width=(int)(DisplayUtil.screenWidthPx*rat);

        UtilGlide.showImageViewBlur(this,R.mipmap.image_default,model.getResource_picture().replace("_150",""),audioplayLl);
        UtilGlide.loadImgForIvHeadWithBorder(this,model.getResource_picture().replace("_150",""),audioplayCircleviewIv);
        audiplayTopbar.setText(nullToSting(urlPath.getUrlModel().get(0).getVolumn().get(0).getResource_name()));
        audioplayPlayProgressSb.setOnSeekBarChangeListener(new OnSeekBarChange());
    }



    //音频进度条效果
    private class OnSeekBarChange implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            if (mAudioManager!=null){
                try {
                    mAudioManager.stopThread();
                }catch (RemoteException e){
                    newRemoteException();
                }
            }
            isSeekBar = true;
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            sendAudioPlayBroacast((durition * seekBar.getProgress()) / 100);
        }
    }


    //获取音音频播放状态
    private  void audioPlayState(int code, String msg){
        if (code==CommitContent.AUDIO_UPDATE_PROGRESS_CODE){
            audioplayPlayProgressSb.setSecondaryProgress(Integer.parseInt(msg));
            LogUtils.D("获取到缓存进度"+msg);
        }else if (code==CommitContent.AUDIO_TOTAL_DURATION_CODE){
            LogUtils.D("总时长");
            durition = Integer.parseInt(msg);
            audioplayDurtionTv.setText(intToTime(durition));
        } else if (code==CommitContent.AUDIO_PLAY_STATE_CODE){
            if (msg.equals(CommitContent.PLAY_MSG)) {//播放时
                playPauseIv.setImageResource(R.mipmap.audio_pause);
            } else if (msg.equals(CommitContent.PAUSE_MSG)) {//暂停
                playPauseIv.setImageResource(R.mipmap.audio_play);

            } else if (msg.equals(CommitContent.STOP_MSG)) {//停止
                audioplayPlayProgressSb.setProgress(0);
                playPauseIv.setImageResource(R.mipmap.audio_play);
                audioplayCurrentPositionTv.setText("00:00");
            }

        } else if(code==CommitContent.AUDIO_URLPATH_EXCEPTION_CODE){
            if (msg.equals(CommitContent.NO_AUDIO_URL)) {//没有音频文件资源
                showToast("没有音频资源");
                if (isSeekBar) {
                    isSeekBar = false;
                    audioplayPlayProgressSb.setProgress(0);
                }
            }else if (msg.equals(CommitContent.EXIST_AUDIO_URL)) {  //存在音频路径
                if (isSeekBar) {
                    isSeekBar = false;
                }
            }else  if(msg.equals(CommitContent.NO_GET_AUDIO_URL)){
                showToast("服务未获取到音频路径");
            }
        }else if (code==CommitContent.AUDIO_PLAY_EXCEPTION_CODE){//播放异常
            showToast(msg);
        }else  if (code==CommitContent.AUDIO_PLAY_INFO_SHOW_CODE){//
            showToast(msg);
        }else if (code==CommitContent.AUDIO_PLAY_INDEX_CODE){//播放下标信息
             int index=Integer.parseInt(msg);
            currentIndex=index;
            LogUtils.D("播放下标---》"+index);
            if (dialogRecyclerView!=null){//修改弹出的recycleView的颜色
                audioRcsAdapter.setCurrentIndex(currentIndex);
            }
            LogUtils.D("Index---->"+currentIndex);
            audiplayTopbar.setText(nullToSting(urlPath.getUrlModel().get(0).getVolumn().get(currentIndex).getResource_name()));
        }else  if (code==CommitContent.AUDIO_PLAY_GET_AUDIO_INFO_CODE){//service中获取到音频信息
            sendAudioUrlBroacast(urlPath);//发送音频列表
        }else if(code==CommitContent.AUDIO_NET_CODE){
            if (msg.equals(CommitContent.NO_ENABLE_NET_MSG)){//没有网络时
                showToast("没有网络,请打开网络");
            }else if(msg.equals(CommitContent.NO_WIFI_NET_MSG)){//有数据流量没有wifi时
                showNet();
            }
        }
    };



    @Override
    public void onEventMainThread(MessageEvent event) {
        if (event.getCode()==CommitContent.AUDIO_UPDATE_PROGRESS_CODE){
           audioplayPlayProgressSb.setSecondaryProgress(Integer.parseInt(event.getMessage()));
            LogUtils.D("获取到dier进度"+event.getMessage());
        }
    else if (event.getCode()==CommitContent.AUDIO_PLAY_STATE_CODE){
         if (event.getMessage().toString().equals(CommitContent.PLAY_MSG)) {//播放时
             playPauseIv.setImageResource(R.mipmap.audio_pause);
         } else if (event.getMessage().toString().equals(CommitContent.PAUSE_MSG)) {//暂停
             playPauseIv.setImageResource(R.mipmap.audio_play);

         } else if (event.getMessage().toString().equals(CommitContent.STOP_MSG)) {//停止
             audioplayPlayProgressSb.setProgress(0);
             playPauseIv.setImageResource(R.mipmap.audio_play);
             audioplayCurrentPositionTv.setText("00:00");
         }

     } else if(event.getCode()==CommitContent.AUDIO_URLPATH_EXCEPTION_CODE){
        if (event.getMessage().toString().equals(CommitContent.NO_AUDIO_URL)) {//没有音频文件资源
             showToast("没有音频资源");
             if (isSeekBar) {
                 isSeekBar = false;
                 audioplayPlayProgressSb.setProgress(0);
             }
         }else if (event.getMessage().toString().equals(CommitContent.EXIST_AUDIO_URL)) {  //存在音频路径
            if (isSeekBar) {
                isSeekBar = false;
            }
        }else  if(event.getMessage().toString().equals(CommitContent.NO_GET_AUDIO_URL)){
            showToast("服务未获取到音频路径");
        }
     }else if (event.getCode()==CommitContent.AUDIO_PLAY_EXCEPTION_CODE){//播放异常
         showToast(event.getMessage().toString());
     }else  if (event.getCode()==CommitContent.AUDIO_PLAY_INFO_SHOW_CODE){//
         showToast(event.getMessage().toString());
     }else if (event.getCode()==CommitContent.AUDIO_PLAY_INDEX_CODE){//播放下标信息
        final int index=Integer.parseInt(event.getMessage().toString());
         audiplayTopbar.setText(nullToSting(urlPath.getUrlModel().get(index).getVolumn().get(0).getResource_name()));
         if (dialogRecyclerView!=null){//修改弹出的recycleView的颜色
           audioRcsAdapter.setCurrentIndex(index);
         }
         if (currentIndex!=index){
             currentIndex=index;
         }
         LogUtils.D("currentIndex---->"+currentIndex);
     }else  if (event.getCode()==CommitContent.AUDIO_PLAY_GET_AUDIO_INFO_CODE){//service中获取到音频信息
         sendAudioUrlBroacast(urlPath);//发送音频列表
     }else if(event.getCode()==CommitContent.AUDIO_NET_CODE){
         if (event.getMessage().toString().equals(CommitContent.NO_ENABLE_NET_MSG)){//没有网络时
             showToast("没有网络,请打开网络");
         }else if(event.getMessage().toString().equals(CommitContent.NO_WIFI_NET_MSG)){//有数据流量没有wifi时
                 showNet();
         }
     }
    }

    Dialog netDialog;
    public void showNet() {
        netDialog = new Dialog(this, R.style.ActionSheetDialogStyle_NoAnimation);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_audio_net, null);
        TextView jixuPlay=(TextView) inflate.findViewById(R.id.no_wifi_play_tv);
        TextView noPlay=(TextView) inflate.findViewById(R.id.no_wifi_no_play_tv);
        TextView deletePlay=(TextView) inflate.findViewById(R.id.delete_play_tv);
        jixuPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.D("点击了继续");
               sendAudioNetBroacast(1);
                netDialog.dismiss();
            }
        });
        noPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.D("点击了不再提醒");
                sendAudioNetBroacast(2);
                netDialog.dismiss();
            }
        });
          deletePlay.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  sendAudioNetBroacast(0);
                  netDialog.dismiss();
              }
          });
        //将布局设置给Dialog
        netDialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = netDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.CENTER);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.width = (int) (DisplayUtil.screenWidthPx * 0.8);
//        lp.y = 30;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        netDialog.show();//显示对话框
    }



    //获取音频当前播放时长
    public void onEventMainThread(String str) {
        LogUtils.D(str + "" + "str");
        int current = Integer.parseInt(str);
        audioplayCurrentPositionTv.setText(intToTime(current) + "");
        if (durition > 0) {
            int currentProgress = current * 100 / durition;
            audioplayPlayProgressSb.setProgress(currentProgress);
        }
    }
    private String intToTime(int time) {
        String fen = (time / (60 * 1000)) + "";
        String miao = ((time / 1000) % 60) + "";
        fen = fen.length() == 1 ? 0 + fen : fen;
        miao = miao.length() == 1 ? 0 + miao : miao;
        return fen + ":" + miao;
    }
    private void sendAudioUrlBroacast(UrlPath url) {
        if (url==null){
            showToast("无音频数据");
            return;
        }
        List<VolumnBean>urlModels=url.getUrlModel().get(0).getVolumn();
           if (urlModels.size()<=0){
               showToast("无音频数据");
               return;
           }
        List<Audio>audios=new ArrayList<>();
        for (VolumnBean urlModel:urlModels){
            Audio audio=new Audio();
            audio.setUrl(urlModel.getUrl());
            audio.setResource_name(urlModel.getResource_name());
            audios.add(audio);
        }

        if (mAudioManager!=null){
            try {
                mAudioManager.addAudios(audios);
            }catch (RemoteException e){
                newRemoteException();
            };
        }
    }

    private void sendAudioPlayBroacast(int position) {//发送播放位置的广播
        if (mAudioManager!=null){
            try {
                mAudioManager.playByProgressPosition(position);
            }catch (RemoteException e){
                newRemoteException();
            };
        }
    }

    private void sendAudioInfoBroacast(AudioResourceBean resourceModel) {//发送音频信息广播
       if (resourceModel==null){
           showToast("无音频数据");
           return;
       }
        Resource resource=new Resource();
        resource.setResource_name(resourceModel.getResource_name());
        resource.setResource_id(resourceModel.getResource_id());
        resource.setResource_picture(resourceModel.getResource_picture());
          LogUtils.D("resource---->"+resource);
        if (mAudioManager!=null){
            try {
                LogUtils.D("添加resource");
                mAudioManager.addResource(resource);
            }catch (RemoteException e){
                LogUtils.D("异常");
                newRemoteException();
            };
        }

    }

    private void sendAudioNetBroacast(int gprsType) {//发送继续阅读
        if (mAudioManager!=null){
            try {
                mAudioManager.setGprsPlay(gprsType);
                if (gprsType!=0){
                    mAudioManager.play();
                }
            }catch (RemoteException e){
                newRemoteException();
            };
        }
    }


    private void sendAudioPlayIsForwardBroacast(boolean isForward) { //发送音频播放页面是否处于前台
        if (mAudioManager!=null){
            try {
                mAudioManager.setPlayBefore(isForward);
            }catch (RemoteException e){
                newRemoteException();
            };
        }
    }


    private void sendAudioBroacast(String str) {
//            Intent intent = new Intent(CommitContent.AUDIO_SERVICE_ACTION);
//            intent.putExtra(CommitContent.ACTIVITY_VIEW_PLAY_TAG, str);
//            sendBroadcast(intent);
    }
  private  void sendAudioPlayIndex(int index){
      if (mAudioManager!=null){
          try {
              mAudioManager.playByIndex(index);
          }catch (RemoteException e){
              newRemoteException();
          };
      }

  }


    private void requestCollection(ImageView imageView){//收藏
        String user_id=CommitContent.isLogin(AudioPlayActivity.this);
        if (user_id!=null){
            HttpRequest.addOrCancelCollection(model.getResource_id(), model.getGys_id(),imageView,new HttpRequest.StateCallBack(){
                @Override
                public void onSuccess(StateBean msg) {
                }
            });
        }
    }

    private  void  requestGiveThumb() {//点赞
        String user_id = CommitContent.isLogin(AudioPlayActivity.this);
        if (user_id != null) {
            HttpRequest.giveThumbs(model.getResource_id(), model.getGys_id(), audioplayPingFenIv, new HttpRequest.StateCallBack() {
                @Override
                public void onSuccess(StateBean msg) {
                }
            });
        }
    }

private RecyclerView dialogRecyclerView;

 AudioRcsAdapter  audioRcsAdapter;
    private  void setAdapter(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dialogRecyclerView.setLayoutManager(linearLayoutManager);
        audioRcsAdapter=new AudioRcsAdapter(this,urlPath.getUrlModel().get(0).getVolumn(),currentIndex);
        dialogRecyclerView.setAdapter(audioRcsAdapter);
         audioRcsAdapter.onClick(new AudioRcsAdapter.ClickCallBack() {
             @Override
             public void onClickIndex(int position) {
                 sendAudioPlayIndex(position);
             }
         });//item  click时间
    }

Dialog dialog;
    public void show(){
        dialog = new Dialog(this,R.style.ActionSheetDialogStyle);
        //填充对话框的布局
     View   inflate = LayoutInflater.from(this).inflate(R.layout.audio_list, null);
        dialogRecyclerView=(RecyclerView) inflate.findViewById(R.id.audiolist_recyclerview);
          setAdapter();
        TextView tv=(TextView) inflate.findViewById(R.id.audiolist_close_tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.height=(int)(DisplayUtil.screenWidthPx*0.8);
        lp.width= LinearLayout.LayoutParams.MATCH_PARENT;
//        lp.y = 30;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }


//  private   AudioListDialog  audioListDialog;
    @OnClick({R.id.audioplay_collection_iv, R.id.audioplay_ping_lun_iv, R.id.audioplay_ping_fen_iv,
            R.id.audioplay_more_iv, R.id.previous_iv, R.id.play_play_pause_iv, R.id.next_iv, R.id.audioplay_ll,R.id.audio_share})
    public void onViewClicked(View view) {
            switch (view.getId()) {
            case R.id.audioplay_collection_iv:
                    requestCollection(audioplayCollectionIv);
                break;
            case R.id.audioplay_ping_lun_iv://
                launch(CommentActivity.class, model);
                break;
            case R.id.audioplay_ping_fen_iv: //点赞
                    requestGiveThumb();
                break;
            case R.id.audioplay_more_iv:
                if (urlPath!=null) {
                    show();
                }else{
                    LogUtils.D("urlPath 为空");
                }
                break;
            case R.id.previous_iv:
                if (mAudioManager!=null){
                    try {
                        mAudioManager.playPre();
                    }catch (RemoteException e){
                        newRemoteException();
                    };
                }
//                sendAudioBroacast(CommitContent.PLAY_PRE_MSG);//播放上一首
                break;
            case R.id.play_play_pause_iv:
                if (mAudioManager!=null){
                    try {
                        mAudioManager.playOrPause();
                    }catch (RemoteException e){
                        newRemoteException();
                    };
                }
                break;
            case R.id.next_iv:
                if (mAudioManager!=null){
                    try {
                        mAudioManager.playNext();
                    }catch (RemoteException e){
                        newRemoteException();
                    };
                }
                break;
            case R.id.audioplay_ll:
                break;
                case R.id.audio_share:
                    utilshare.show();
                    break;
        }
    }
}
