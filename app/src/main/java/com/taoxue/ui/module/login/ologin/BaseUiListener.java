package com.taoxue.ui.module.login.ologin;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 2017/9/11.
 */

public abstract class BaseUiListener implements IUiListener {
    private static final String TAG = "cc";
    private Context context;

    public BaseUiListener(Context context) {
        this.context = context;
    }


public abstract void getAgreeInfo(Map<String,String>map);

    @Override
    public void onComplete(Object response) {
//        Toast.makeText(context, "授权成功", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "response:" + response);
        JSONObject obj = (JSONObject) response;
        try {
            String openID = obj.getString("openid");
            String accessToken = obj.getString("access_token");
            String expires = obj.getString("expires_in");
            Map<String,String>map=new HashMap<>();
            map.put("openid",openID);
            map.put("access_token",accessToken);
            map.put("expires_in",expires);
            getAgreeInfo(map);
//            QQAgreeInfo info=new QQAgreeInfo();
//            info.setOpenID(openID);
//            info.setAccessToken(accessToken);
//            info.setExpires(expires);
//            qqloginIm.getAgree(info);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(UiError uiError) {
        Toast.makeText(context, "授权失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {
        Toast.makeText(context, "授权取消", Toast.LENGTH_SHORT).show();
    }
}
