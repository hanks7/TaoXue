package com.taoxue.ui.module.login.ologin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 2017/9/11.
 */

public  class BaseWXLoginActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    private BaseResp resp = null;
    private String WX_APP_ID = WXLogin.wxLogin.WX_APP_ID;
    // 获取第一步的code后，请求以下链接获取access_token
    private String GetCodeRequest = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    private String WX_APP_SECRET = WXLogin.wxLogin.WX_APP_SECRET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, WX_APP_ID, false);
        api.handleIntent(getIntent(), this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        finish();
    }
    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        String result = "";
        if (resp != null) {
            this.resp = resp;
        }
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
//                result = "发送成功";
//                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                String code = ((SendAuth.Resp) resp).code;

            /*
             * 将你前面得到的AppID、AppSecret、code，拼接成URL 获取access_token等等的信息(微信)
             */
                String get_access_token = getCodeRequest(code);
                AsyncHttpClient client = new AsyncHttpClient();
                client.httpGet(get_access_token, new HttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i(TAG, "---->response--->"+response);
                        //    ->response--->{"access_token":"6jaaXmUocGGYdB10ApEszwDTc-P44M-yCg7VXZKunzTqKYm5h7-lVMN-zxwow_mJR8ve_hIjPnbJD1JRveNhLA","expires_in":7200,"refresh_token":"xeA1QxcI_bgvbAGXZUe4m-mOBzE0lD4tYGwO-9yqtcojW_91UJnMerzphATnUrLBPsOqwVBxMdxj0qb4F3y8Gg","openid":"oK98M0nikPQbgEHLIEBn3ghUuVyY","scope":"snsapi_userinfo","unionid":"oEwx2xNp3CTmQ2sq-A-GTel3ZjnY"}
                        try {
                            JSONObject obj=new JSONObject(response);
                              Map<String,String>map=new HashMap<String, String>();
                            if (!response.equals("")) {
                                String access_token = obj
                                        .getString(LoginChar.ACCESS_TOKEN);
                                String openid = obj.getString(LoginChar.OPENID);
                                 map.put(LoginChar.ACCESS_TOKEN,access_token);
                                map.put(LoginChar.OPENID,openid);
                                WXLogin.wxLogin.onWXAgreeInfo(map);
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        WXLogin.wxLogin.onWXAgreeError(msg);
                    }
                });
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "发送取消";
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "发送被拒绝";
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                finish();
                break;
            default:
                result = "发送返回";
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }

    private static final String TAG = "cc";



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        finish();
    }

    /**
     * 获取access_token的URL（微信）
     *
     * @param code
     *            授权时，微信回调给的
     * @return URL
     */
    private String getCodeRequest(String code) {
        String result = null;
        GetCodeRequest = GetCodeRequest.replace("APPID",
               WXLogin.wxLogin.urlEnodeUTF8(WX_APP_ID));
        GetCodeRequest = GetCodeRequest.replace("SECRET",
                WXLogin.wxLogin.urlEnodeUTF8(WX_APP_SECRET));
        GetCodeRequest = GetCodeRequest.replace("CODE", WXLogin.wxLogin.urlEnodeUTF8(code));
        result = GetCodeRequest;
        return result;
    }




}
