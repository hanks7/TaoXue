package com.taoxue.ui.module.login;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.utils.UtilToast;

/**
 * 默认剩余时间 60 秒  每秒更新一次
 */

public abstract class MobileCode {
    private Context context;
    private  TextView registerCodeBtn;
    private  String mobile;
    private TimeCount timeCount;

    public TimeCount getTimeCount() {
        return timeCount;
    }

    public void cancelTimeCount(){
        timeCount.cancel();
    }

    public MobileCode(Context context, String mobile, TextView registerCodeBtn) {
        this.context = context;
        this.registerCodeBtn=registerCodeBtn;
        this.mobile=mobile;
        loaderMobileCode();
    }

    /*
       **
               * 验证手机格式
       */
    public boolean isMobileNO(String mobile) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        if (mobile.length() == 11) {
            if (mobile.startsWith("1")) {
                return true;
            }
        }
        return false;
    }

    protected   void  onFailure(String msg){//失败信息
        UtilToast.showText(msg);
    };
    protected   void  onTimeFinish(){//完成
        registerCodeBtn.setTextColor(context.getResources().getColorStateList(R.color.white));
        registerCodeBtn.setText("重新获取验证码");
        registerCodeBtn.setClickable(true);
    };

    protected  abstract void  sendPhoneCode(String mobile);//发送手机验证码


    public    void loaderMobileCode(){
           if (TextUtils.isEmpty(mobile)){
               onFailure("请输入手机号");
               return;
           }
          if (!isMobileNO(mobile)){
              onFailure("请输入正确的手机号");
              return;
          }

     timeCount = new TimeCount(60000, 1000,registerCodeBtn);
        timeCount.start();
        sendPhoneCode(mobile);
    }

  private class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval,TextView registerCodeBtn) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            registerCodeBtn.setClickable(false);
            registerCodeBtn.setText(millisUntilFinished / 1000 + "秒后可重新发送");
        }

        @Override
        public void onFinish() {
            onTimeFinish();
        }
    }

}
