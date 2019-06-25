package com.taoxue.ui.module.login.ologin;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 2017/9/11.
 */

public abstract class UserInfoUiListener implements IUiListener {
    private Context context;
    private static final String TAG = "cc";
    public UserInfoUiListener(Context context) {
        this.context = context;
    }
  public  abstract void onUserInfo(Map<String,String>map);
    @Override
    public void onComplete(Object response) {
        JSONObject obj = (JSONObject) response;
//        QQInfoBean qqbean=new QQInfoBean();
        Map<String,String>map=new HashMap<>();
        try{
        map.put("ret",obj.getString("ret"));
            map.put("city",obj.getString("city"));
            map.put("nickname",obj.getString("nickname"));
            map.put("figureurl",obj.getString("figureurl"));
            map.put("gender",obj.getString("gender"));
            map.put("msg",obj.getString("msg"));
            map.put("province",obj.getString("province"));
            map.put("is_lost",obj.getString("is_lost"));
            map.put("figureurl_1",obj.getString("figureurl_1"));
            map.put("figureurl_2",obj.getString("figureurl_2"));
            map.put("figureurl_qq_1",obj.getString("figureurl_qq_1"));
            map.put("figureurl_qq_2",obj.getString("figureurl_qq_2"));
            onUserInfo(map);
//            qqbean.setRet(obj.getString("ret"));
//            qqbean.setCity(obj.getString("city"));
//            qqbean.setNickname(obj.getString("nickname"));
//            qqbean.setFigureurl(obj.getString("figureurl"));
//            qqbean.setGender(obj.getString("gender"));
//            qqbean.setMsg(obj.getString("msg"));
//            qqbean.setProvince(obj.getString("province"));
//            qqbean.setIs_lost(obj.getString("is_lost"));
//            qqbean.setFigureurl_1(obj.getString("figureurl_1"));
//            qqbean.setFigureurl_2(obj.getString("figureurl_2"));
//            qqbean.setFigureurl_qq_1(obj.getString("figureurl_qq_1"));
//            qqbean.setFigureurl_qq_2(obj.getString("figureurl_qq_2"));
//            qqloginIm.loginSuccess(qqbean);
        }catch (Exception e){
            Log.i(TAG, "e--->"+e);
        }
    }

    @Override
    public void onError(UiError uiError) {
        Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {
        Toast.makeText(context, "登录取消", Toast.LENGTH_SHORT).show();
    }

}
