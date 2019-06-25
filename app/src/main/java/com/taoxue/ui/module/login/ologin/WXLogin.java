package com.taoxue.ui.module.login.ologin;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 2017/9/11.
 */

public class WXLogin {
  IWXAPI WXapi;
    public String WX_APP_ID;
    private Context context;
   public  String WX_APP_SECRET;

    // 获取用户个人信息
    private String GetUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";

    public  static WXLogin wxLogin;

    public WXLogin(Context context,String appId,String appSecret) {
        this.WX_APP_ID = appId;
        this.context=context;
        this.WX_APP_SECRET=appSecret;
    }

    public static WXLogin newInstance(Context context,String appId,String appSecret) {
    if (wxLogin==null){
        wxLogin=new WXLogin(context,appId,appSecret);
    }
        return wxLogin;
    }



WXLoginListener wxAgreeListener;
    /**
     * 登录微信
     */
    public void WXLogin(WXLoginListener wxAgreeListener) {
        WXapi = WXAPIFactory.createWXAPI(context, WX_APP_ID, true);
        WXapi.registerApp(WX_APP_ID);
        Log.d(TAG, "WXLogin: denglu");
        
////先判断是否安装微信APP,按照微信的说法，目前移动应用上微信登录只提供原生的登录方式，需要用户安装微信客户端才能配合使用。
//        if (!WXUtils.isWXAppInstalled()) {
//            ToastUtils.showToast("您还未安装微信客户端");
//            return;
//        }




        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo";
        WXapi.sendReq(req);
        this.wxAgreeListener=wxAgreeListener;
    }

 public void getWXUserInfo(Map<String,String>map,WXLoginListener wxLoginListener){
      String access_token=map.get(LoginChar.ACCESS_TOKEN);
        String openid=map.get(LoginChar.OPENID);
        String get_user_info_url = getUserInfoUrl(
                access_token, openid);
        getUserInfo(get_user_info_url,wxLoginListener);
 }

    public void getWXUserInfo(String openid,String access_token,WXLoginListener wxLoginListener){
        String get_user_info_url = getUserInfoUrl(
                access_token, openid);
        getUserInfo(get_user_info_url,wxLoginListener);
    }
      //授权成功信息
    public void onWXAgreeInfo(Map<String,String>map){
        Log.i(TAG, "map------>"+map);
        wxAgreeListener.onWXInfo(map);


    }
    //授权失败信息
   public void onWXAgreeError(String msg){
       Toast.makeText(context,"授权失败"+msg,Toast.LENGTH_LONG).show();

   }


    /**
     * 获取用户个人信息的URL（微信）
     *
     * @param access_token
     *            获取access_token时给的
     * @param openid
     *            获取access_token时给的
     * @return URL
     */
    private String getUserInfoUrl(String access_token, String openid) {
        String result = null;
      GetUserInfo = GetUserInfo.replace("ACCESS_TOKEN",
                urlEnodeUTF8(access_token));
        GetUserInfo = GetUserInfo.replace("OPENID", urlEnodeUTF8(openid));
        result = GetUserInfo;
        return result;
    }


    public String urlEnodeUTF8(String str) {
        String result = str;
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static final String TAG = "cc";
    /**
     * 通过拼接的用户信息url获取用户信息
     *
     * @param user_info_url
     */
    private void getUserInfo(String user_info_url, final WXLoginListener wxlistener) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.httpGet(user_info_url, new HttpResponseHandler() {
            @Override
            public void onSuccess(String re) {
                try {
                    JSONObject response=new JSONObject(re);
                    System.out.println("获取用户信息:" + response);
                    Log.i(TAG, "onSuccess: response--->"+response);
//                    onSuccess: response--->{"openid":"oK98M0nikPQbgEHLIEBn3ghUuVyY","nickname":"彬彬","sex":1,"language":"zh_CN","city":"Hefei","province":"Anhui","country":"CN","headimgurl":"http:\/\/wx.qlogo.cn\/mmopen\/vi_32\/fcnvXSfjba8GJ6O8uI6HvibGQORKHJUJBrD0f1nzgBwbwHDT76v8G1k8Qc877H2mkB3ZkHZDzLfziaBwfpxG1xrg\/0","privilege":[],"unionid":"oEwx2xNp3CTmQ2sq-A-GTel3ZjnY"}
                    Map<String,String>map=new HashMap<String, String>();
                    if (!response.equals("")) {
                        String openid = response.getString("openid");
                        String nickname = response.getString("nickname");
                        String headimgurl = response.getString("headimgurl");
                        map.put("openid",response.getString("openid"));
                        map.put("nickname",response.getString("nickname"));
                        map.put("sex",response.getString("sex"));
                        map.put("province",response.getString("province"));
                        map.put("headimgurl",response.getString("headimgurl"));
                        map.put("unionid",response.getString("unionid"));
                        wxlistener.onWXInfo(map);
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String msg) {
               Toast.makeText(context,"登录失败",Toast.LENGTH_LONG).show();
            }
        });

    }

}
