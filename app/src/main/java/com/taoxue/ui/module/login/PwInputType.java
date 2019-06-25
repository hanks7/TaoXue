package com.taoxue.ui.module.login;

import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import com.taoxue.utils.LogUtils;

/**
 * Created by User on 2017/8/11.
 */

public class PwInputType {
    private static EditText editText;
    private  boolean eyeOpen;

    public static EditText getEditText() {
        return editText;
    }

    public PwInputType(EditText editText) {
        this.editText = editText;
        eyeOpen=false;
    }
    public static  PwInputType pwInputType;


     public static PwInputType newInstance(EditText editText) {
        if (pwInputType==null||getEditText().hashCode()!=editText.hashCode()){
            LogUtils.D("pwInputType");
            pwInputType=new PwInputType(editText);
        }
        return pwInputType;
    }


   public  void onTextPassWord(){//密码形式
       LogUtils.D("密码");
       editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
   }

    public  void onTextVisiblePassword(){
        LogUtils.D("明文");
        editText.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
    }
    public void changeInputType(){
    if ( eyeOpen ){
        //密码 TYPE_CLASS_TEXT 和 TYPE_TEXT_VARIATION_PASSWORD 必须一起使用
          onTextPassWord();
//                    imageView.setImageResource( R.mipmap.eye_close );
        eyeOpen = false ;
    }else {
        //明文
        onTextVisiblePassword();
//                    imageView.setImageResource( R.mipmap.eye_open );
        eyeOpen = true ;
    }
}

}
