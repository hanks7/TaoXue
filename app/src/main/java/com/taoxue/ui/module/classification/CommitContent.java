package com.taoxue.ui.module.classification;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;
import com.taoxue.R;
import com.taoxue.app.BaseApplication;
import com.taoxue.base.BaseActivity;
import com.taoxue.ui.module.login.LoginActivity;
import com.taoxue.utils.LogUtils;

/**
 * Created by User on 2017/4/13.
 */

public class CommitContent {
    //判断是否登录  登录 返回  user_id
    public static String isLogin(Context context) {
        if (BaseApplication.get().isLogin()) {
            return BaseApplication.get().getUserModel().getUser_id();
        } else {
            if (context instanceof BaseActivity) {
                ((BaseActivity) context).showToast("请先登录");
                ((BaseActivity) context).launch(LoginActivity.class);
            }
            return null;
        }

    }

    //音乐常量
    public final static String PLAY_MSG = "play_msg";
    public final static String PAUSE_MSG = "pause_msg";
    public final static String STOP_MSG = "stop_msg";
    public final static String NO_AUDIO_URL = "no_audio_url";
    public final static String EXIST_AUDIO_URL = "EXIST_audio_url";
    public final static String NO_SET_AUDIO_URL = "no_set_audio_url";
    public final static String MEDIA_PREPARE_EXCEPTION = "音频播放异常";
    public final static String NO_GET_AUDIO_URL = "no_get_audio_url";

    public final static String NO_ENABLE_NET_MSG = "no_ENABLE_NET_url";
    public final static String NO_WIFI_NET_MSG = "no_wifi_NET_url";

    //音乐播放界面Action
    public final static String AUDIO_SERVICE_ACTION ="AUDIO_SERVICE_ACTION";
    public final static String ACTIVITY_VIEW_PLAY_TAG = "ACTIVITY_VIEW_PLAY_TAG";
    public final static String PLAY_OR_PLAUSE_MSG = "play_PLAUSE_msg";
    public final static String PLAY_NEXT_MSG = "play_next_msg";
    public final static String PLAY_PRE_MSG = "play_pre_msg";
    public final static String START_PLAY_MSG = "START_play_msg";
    public final static String AUDIO_PLAY_INDEX_MSG = "audio_play_index_msg";
    public final static String AUDIO_PLAY_INDEX_TAG = "audio_play_index_tag";
    public final static String  STOP_UPDATE_THREAD_PROGRESS ="stopUpdateTHreadProgress";
    public final static String  IS_EXIST_AUDIO_URL="isExistUrl";
    public final static String AUDIO_PLAY_TAG = "AUDIO_play_tag";
    public final static String PLAY_ACTIVITY_IS_FORWORD = "play_ACTIVITY_IS_FORWORD";
    public final static String PLAY_ACTIVITY_IS_FORWORD_TAG = "play_ACTIVITY_IS_FORWORD_tag";

    public final static String AUDIO_START_PLAY = "AUDIO_play_START";
    public final static String AUDIO_PLAY_URL = "audio_play_url";
    public final static String AUDIO_PLAY_URL_TAG = "audio_play_url_tag";
    public final static String AUDIO_RESOURCE_ID = "AUDIO_RESOURCE_ID";
    public final static String AUDIO_INFO = "AUDIO_info";
    public final static String AUDIO_INFO_TAG = "AUDIO_info_tag";
    public final static String AUDIO_OK="OK";
    public final static String JIXU_READ = "jixu_read";
    public final static String JIXU_READ_TAG = "jixu_read_tag";


    public final static String SEND_NOTIFICATION_ACTION ="SEND_NOTIFICATION_ACTION";//发送通知的广播

   //结束音频service
    public final static String STOP_AUDIO_SERVICE_ACTION = "stop_audio_service_action";

     //音频 service中的
    public final static int AUDIO_PLAY_STATE_CODE = 112;
    public final static int AUDIO_PLAY_EXCEPTION_CODE=113;
    public final static int AUDIO_URLPATH_EXCEPTION_CODE=114;
    public final static int AUDIO_PLAY_INFO_SHOW_CODE=115;
    public final static int AUDIO_PLAY_INDEX_CODE=116;
    public final static int AUDIO_PLAY_GET_AUDIO_INFO_CODE=117;
    public final static int AUDIO_NET_CODE=118;
    public final static int AUDIO_UPDATE_PROGRESS_CODE=119;
    public final static int AUDIO_TOTAL_DURATION_CODE=120;
    public final static int AUDIO_REOTEEXCETION_CODE=121;//远程异常


    //音频播放
    public static class PlayerMsg {
        final static int PAUSE_MSG = 11;
        final static int PLAY_MSG = 21;
        final static int STOP_MSG = 31;
    }

    //注册页跳转传参
    public final static String REGISTER_READER = "registerToReaderCard";

    //    将String 为null 值时转化为空
    public static String nullToSting(String str) {
        String s=TextUtils.isEmpty(str) ? "" : str.trim();
        if ("null".equals(s)){
            return " ";
        }
        return s;
    }

    //未获取读者证号时注册
    public final static String UNGETREADERID = "UnGetReadId";

    //直接注册
    public final static String ZHIJIEZHUCE = "zHIJIEZHUCe";

   //详情页跳转至图片查看页传参；
    public  final  static  String IMAGE_SCAN="imagescan";
    //详情页跳转至pdf查看页传参；
    public  final  static  String PDF_SCAN="pdfcan";

    //跳转 参数
    public  final  static  String MY_LIB="mylib";
    public  final  static  String MY_LIB_CANSHU="mylibcanshu";

    //跳转传参；
    public  final  static  String BIND_READER_CARD_ID_SUCCESS="bind_reader_card_id_success";


  //电话监听时
    public final static String B_PHONE_STATE =
            TelephonyManager.ACTION_PHONE_STATE_CHANGED;



    public static   String  millisToDate(String time){
        //毫秒转换为日期
//        UtilDate.format(time,"yyyy-MM-dd hh:mm:ss");
//        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(Long.parseLong(time));
        return time+"";
    }





    private  static   SlidrConfig mSlidrConfig1;
    private  static  SlidrConfig mSlidrConfig;
    private  static  SlidrConfig mSlidrConfig2;

    public static void  setLeftSilde(Activity activity){

        if (mSlidrConfig==null) {
         SlidrConfig.Builder mBuilder;
            int primary = activity.getResources().getColor(R.color.colorPrimary);
            int secondary = activity.getResources().getColor(R.color.black);
            mBuilder = new SlidrConfig.Builder()
                    .primaryColor(primary)//滑动时状态栏的渐变结束的颜色
                    .secondaryColor(secondary)//滑动时状态栏的渐变开始的颜色
                    .scrimColor(Color.BLACK)//滑动时Activity之间的颜色
                    .position(SlidrPosition.LEFT)//从左边滑动
                    .scrimStartAlpha(0.8f)//滑动开始时两个Activity之间的透明度
                    .scrimEndAlpha(0f)//滑动结束时两个Activity之间的透明度
                    .velocityThreshold(5f)//超过这个滑动速度，忽略位移限定值就切换Activity
                    .distanceThreshold(.35f)//滑动位移占屏幕的百分比，超过这个间距就切换Activity
                    .edge(true);
            mSlidrConfig = mBuilder.build();
        }
        Slidr.attach(activity, mSlidrConfig);
    }

    public static void  setColorLeftSilde(Activity activity){//侧边划出
        if (mSlidrConfig1==null) {
            SlidrConfig.Builder mBuilder;
            int primary = activity.getResources().getColor(R.color.colorEndPrimary);
            int secondary = activity.getResources().getColor(R.color.colorPrimary);
            mBuilder = new SlidrConfig.Builder()
                    .primaryColor(primary)//滑动时状态栏的渐变结束的颜色
                    .secondaryColor(secondary)//滑动时状态栏的渐变开始的颜色
                    .scrimColor(Color.BLACK)//滑动时Activity之间的颜色
                    .position(SlidrPosition.LEFT)//从左边滑动
                    .scrimStartAlpha(0.8f)//滑动开始时两个Activity之间的透明度
                    .scrimEndAlpha(0f)//滑动结束时两个Activity之间的透明度
                    .velocityThreshold(5f)//超过这个滑动速度，忽略位移限定值就切换Activity
                    .distanceThreshold(.35f)//滑动位移占屏幕的百分比，超过这个间距就切换Activity
                    .edge(true);
            mSlidrConfig1 = mBuilder.build();
        }
        Slidr.attach(activity, mSlidrConfig1);
    }

    public static void  setDetailColorLeftSilde(Activity activity){//侧边划出
        if (mSlidrConfig2==null) {
            SlidrConfig.Builder mBuilder;
            int primary = activity.getResources().getColor(R.color.colorPrimary);
            int secondary = activity.getResources().getColor(R.color.colorPrimary);
            mBuilder = new SlidrConfig.Builder()
                    .primaryColor(primary)//滑动时状态栏的渐变结束的颜色
                    .secondaryColor(secondary)//滑动时状态栏的渐变开始的颜色
                    .scrimColor(Color.BLACK)//滑动时Activity之间的颜色
                    .position(SlidrPosition.LEFT)//从左边滑动
                    .scrimStartAlpha(0.8f)//滑动开始时两个Activity之间的透明度
                    .scrimEndAlpha(0f)//滑动结束时两个Activity之间的透明度
                    .velocityThreshold(5f)//超过这个滑动速度，忽略位移限定值就切换Activity
                    .distanceThreshold(.35f)//滑动位移占屏幕的百分比，超过这个间距就切换Activity
                    .edge(false);
            mSlidrConfig2 = mBuilder.build();
        }
        Slidr.attach(activity, mSlidrConfig2);
    }

    public static void addTextChange(final Context context,final EditText commentContentEt) {
        commentContentEt.addTextChangedListener( new TextWatcher() {
            private CharSequence temp;
            private  int count;
            private StaticLayout staticLayout;
            private  boolean isEdit=false;
            // private int editStart;
            // private int editEnd;
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                temp = s.toString().trim();
                LogUtils.D("temp--->"+temp);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN ) { //当版本大于=16时
                    if (!isEdit&&!TextUtils.isEmpty(temp)){
                        commentContentEt.setBackground(context.getResources().getDrawable(R.drawable.shape_comment_commit_bg));
                        commentContentEt.setPadding(20,0,20,0);
                        LogUtils.D("true");
                        isEdit = true;
                    }
                    if (isEdit&&TextUtils.isEmpty(temp)){
                        commentContentEt.setBackground(context.getResources().getDrawable(R.drawable.shape_comment_commit));
                        commentContentEt.setPadding(20,0,20,0);
                        LogUtils.D("false");
                        isEdit = false;
                    }
                }
                staticLayout=null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) { //当版本大于=16时
                    staticLayout = new StaticLayout(temp, commentContentEt.getPaint(),
                            commentContentEt.getWidth()-40, Layout.Alignment.ALIGN_CENTER,
                            commentContentEt.getLineSpacingMultiplier(), commentContentEt.getLineSpacingExtra(), true);
//                staticLayout.getHeight();
                    count = staticLayout.getLineCount();
                    if (count>2) {
//                    commentContentEt.getLayoutParams().height=staticLayout.getHeight();
                        commentContentEt.setLines(count);
                    }else{
                        commentContentEt.setLines(2);
                    }
                };
                LogUtils.D("count--->"+count);
            }
        });
    }

}
