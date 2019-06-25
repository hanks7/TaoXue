package com.taoxue.ui.module.login.ologin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.Tencent;

import java.util.Map;

/**
 * Created by User on 2017/9/7.
 */

public class TecentLogin {
    BaseUiListener baseUiListener;
    UserInfo mUserInfo;
    Activity context;
    private Tencent mTencent;
    public static TecentLogin tencentLogin;

    public static TecentLogin getTencentLogin() {
        return tencentLogin;
    }

    public void init(Context context, String AppId) {
        mTencent = Tencent.createInstance(AppId, context.getApplicationContext());
    }

    public TecentLogin(Activity context, String AppId) {
        this.context = context;
        init(context, AppId);
    }

    public static TecentLogin newInstance(Activity context, String AppId) {
        if (tencentLogin == null) {
            tencentLogin = new TecentLogin(context, AppId);
        }
        return tencentLogin;
    }


    public void login(BaseUiListener baseUiListener) {
        this.baseUiListener = baseUiListener;
        if (!mTencent.isSessionValid()) {
            mTencent.login(context, "all", baseUiListener);
        }
    }


    public void onActivityResultData(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, baseUiListener);
    }

    private static final String TAG = "cc";

    public void getUserInfo(Map<String, String> map, UserInfoUiListener userinfo) {
        String openID = map.get("openid");
        String accessToken = map.get("access_token");
        String expires = map.get("expires_in");
        getUserInfo(openID, accessToken, expires, userinfo);
    }

    public void getUserInfo(String openID, String accessToken, String expires, UserInfoUiListener userinfo) {
        mTencent.setOpenId(openID);
        mTencent.setAccessToken(accessToken, expires);
        QQToken qqToken = mTencent.getQQToken();
        mUserInfo = new UserInfo(context, qqToken);
        Log.i(TAG, "onComplete:mUserInfo--->" + mUserInfo);
        mUserInfo.getUserInfo(userinfo);
    }


}
