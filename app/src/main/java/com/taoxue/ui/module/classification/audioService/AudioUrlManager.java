package com.taoxue.ui.module.classification.audioService;

import com.taoxue.utils.LogUtils;
import com.taoxue.utils.SPUtil;

import java.util.List;

/**
 * Created by User on 2017/8/8.
 */

class AudioUrlManager {
    private Resource audioInfo;
    private List<Audio> audios;

    private int  index=0;//当前音频播放下标
    private String  url;//当前音频播放路径
    private String  nextUrl;//下一首播放音频
    private String  preUrl;//上一首音频
    private  boolean isEqualAudio=false;//音频文件是否相同
    boolean  isPlayBefore;//判断是否处于前台


    private  AudioStateDelegate audioStateDelegate;
    private  ViewUpdateDelegate viewUpdateDelegate;
    public AudioStateDelegate getAudioStateDelegate() {
        return audioStateDelegate;
    }




    public AudioUrlManager(ViewUpdateDelegate viewUpdateDelegate,AudioStateDelegate audioStateDelegate) {
        this.viewUpdateDelegate=viewUpdateDelegate;
        this.audioStateDelegate=audioStateDelegate;
}




    private int getAudioCount(){//获取音频总的数量
        return audios.size();
    }

    private boolean  isNext(){//判断是否有下一首
        if (getAudioCount()>0&&index<getAudioCount()-1) {
            LogUtils.D("getAudioCount()--->"+getAudioCount()+"index--->"+index);
            return true;
        }
        return false;
    }
    public String getNextUrl() {
        if (isNext()) {
            index++;
            return getUrl();
        }else{
            viewUpdateDelegate.sendNoNextAudio();
            return null;
        }
    }


    public String getPreUrl() {
        if (index>0&&getAudioCount()>0) {
            index--;
            return getUrl();
        }else{
            viewUpdateDelegate.sendNoPreAudio();
            return  null;
        }
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }




    public String getUrl() {
//        LogUtils.D("audios---->"+audios.toString());
        if (audios != null && !("").equals(audios) && audios.size() > 0) {
            return audios.get(index).getUrl();
        }else{
            viewUpdateDelegate.sendNoAudioUrl();
            return  null;
        }
    }


    public Resource getAudioInfo() {
        return audioInfo;
    }

    public void setAudioInfo(Resource audioInfo) {
        LogUtils.D("audioInf---->"+audioInfo.toString());
        this.audioInfo = audioInfo;
        LogUtils.D(("((String) SPUtil.get(AudioPlayService.EXIT_MEDIA_PLAY_RESOURCE_ID,\"00\")"+(String) SPUtil.get(AudioPlayService.EXIT_MEDIA_PLAY_RESOURCE_ID,"00")));
        if (getAudioInfo() != null) {
            LogUtils.D("getAudioInfo() != null");
            if (((String) SPUtil.get(AudioPlayService.EXIT_MEDIA_PLAY_RESOURCE_ID,"00")).equals(audioInfo.getResource_id())) {
                LogUtils.D("相同音频");
                isEqualAudio = true;
                audioStateDelegate.getAudioSet(index,0,isEqualAudio);
//                int spIndex=(int)SPUtil.get(AudioPlayerService.EXIT_MEDIA_PLAY_INDEX,0);
//                int currentPosition=(int)SPUtil.get(AudioPlayerService.EXIT_MEDIA_PLAY_PROGRESS_POSITION,0);
//                if (spIndex!=index){
//                      audioStateDelegate.getAudioSet(spIndex,currentPosition,isEqualAudio);
//                }else {
//                    audioStateDelegate.getAudioSet(index,-1,isEqualAudio);
//                }
            } else {
                isEqualAudio = false;
                LogUtils.D("不同音频");
                audioStateDelegate.getAudioSet(index,0,isEqualAudio);
            }
        }
        viewUpdateDelegate.setPlayBefore(true);
        viewUpdateDelegate.successGetAudioInfo();

    }

    public void setPlayBefore(boolean playBefore) {
        isPlayBefore = playBefore;
        LogUtils.D("isplayBefore--->"+isPlayBefore);
        audioStateDelegate.isPlayBefore(isPlayBefore);
    }

    public boolean isPlayBefore() {
        return isPlayBefore;
    }

    public List<Audio> getAudios() {
        return audios;
    }

    public String getAudioName() {
        return audios.get(index).getResource_name();
    }

    public String getAudioPictureUrl() {
        return audioInfo.getResource_picture();
    }


    public void setAudios(List<Audio> audios){
        LogUtils.D("audios---->"+audios.toString());
        this.audios = audios;
        audioStateDelegate.getUrlPath(true);
    };

}
