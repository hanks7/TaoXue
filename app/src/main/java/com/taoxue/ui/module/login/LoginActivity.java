package com.taoxue.ui.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.app.Constants;
import com.taoxue.app.BaseApplication;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.BaseModel;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.UserModel;
import com.taoxue.ui.module.classification.HttpRequest;
import com.taoxue.ui.module.login.ologin.LoginChar;
import com.taoxue.ui.module.login.ologin.TecentLogin;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.LogUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录
 * Created by User on 2017/4/1.
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_name_Edt)
    EditText loginNameEdt;
    @BindView(R.id.login_PW_Edt)
    EditText loginPWEdt;
    @BindView(R.id.login_changepw)
    TextView loginChangepw;
    @BindView(R.id.QQlogin_Btn)
    ImageView QQloginBtn;
    @BindView(R.id.Wxlogin_Btn)
    ImageView WxloginBtn;
    @BindView(R.id.topbar)
    TopBar topBar;

    private UMShareAPI mShareAPI;
    String QQ_APPID = "101397990";
    public static  final   String  LOGIN_READER_CARD="LOGINREADERCARD";
    public static   int registerResultCode=1111;
    public static   int LOGIN_READER_CARD_CODE=2616;

    String Mobile_Login_Type="mobile";
    String Reader_Card_Login_Type="library";
    String WeiXin_Login_Type="weixin";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_login);
        mShareAPI = UMShareAPI.get(this);//初始化umeng社会化分享
        topBar.setBtnTopRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                  UtilIntent.intentDIYLeftToRight(mActivity, RegisterActivity.class);
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,registerResultCode);
            }
        });
    }

    @Override
    protected void onInit() {
        super.onInit();
        test();
    }

    /**
     * 测试时的自动登陆
     *
     * @return
     */
    private void test() {
        if (Constants.IS_TEST) {
            loginNameEdt.setText("18963787473");//18963787473  123
            loginPWEdt.setText("123456");
        }

    }

    String qqexpires;
    TecentLogin tecentLogin;


    @OnClick({
            R.id.login_pw_visible_iv,
            R.id.btn_back,
            R.id.login_BTN,
            R.id.login_changepw,
            R.id.QQlogin_Btn,
            R.id.Wxlogin_Btn,
            R.id.login_reader_card
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.login_BTN://登陆
                String name = loginNameEdt.getText().toString().trim();
                String pwd = loginPWEdt.getText().toString().trim();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
                    showToast("请先输入用户名或密码");
                } else {
                    initLogin(name, pwd, Mobile_Login_Type);
                }
                break;
            case R.id.login_changepw://忘记密码
                launch(ForgotPasswordActivity.class);
                break;
            case R.id.QQlogin_Btn://qq登录
//                showToast("qq");
                mShareAPI.getPlatformInfo(this, SHARE_MEDIA.QQ, new UMAuthListener() {
                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        String openid = map.get("openid");//微博没有
                        String access_token = map.get("access_token");
                        checkWeixinLogin(openid, access_token,map, LOGIN_QQ);
                        showToast(map.toString());
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        showToast("授权失败");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        showToast("授权取消");
                    }
                });

                LogUtils.D("qq登录");
//                tecentLogin = TecentLogin.newInstance(LoginActivity.this, QQ_APPID);
//                tecentLogin.login(new BaseUiListener(LoginActivity.this) {
//                    @Override
//                    public void getAgreeInfo(Map<String, String> map) {
//                        qqexpires = map.get("expires_in");
//                        openid = map.get("openid");
//                        access_token = map.get("access_token");
//                        checkWeixinLogin(openid, access_token, LOGIN_QQ);
//                    }
//                });

                break;
            case R.id.Wxlogin_Btn://-----------------------------------------------------------------------------------微信登录
               mShareAPI.getPlatformInfo(this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        String openid = map.get("openid");//微博没有
                        String access_token = map.get("access_token");
                        checkWeixinLogin(openid, access_token,map, LOGIN_WEIXIN);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        showToast("授权失败");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        showToast("授权取消");
                    }
                });
//
//                        WXLogin.newInstance(LoginActivity.this, "wx68db7c9878c023fc", "a573cc30a913b9b60a01401732d685b0").WXLogin(new WXLoginListener() {
//                            @Override
//                            public void onWXInfo(Map<String, String> data) {
//                                String oppenid = data.get("openid");
//                                String access_token = data.get("access_token");
////                        showToast("oppenid--->"+oppenid+"---access_token--->"+access_token);
//                                if (!TextUtils.isEmpty(oppenid)) {
//                                    LoginActivity.this.openid = oppenid;
//                                    LoginActivity.this.access_token = access_token;
//                                    checkWeixinLogin(oppenid, access_token, LOGIN_WEIXIN);
//                                } else {
//                                    showToast("暂无法使用该登录方式");
//                                }
//                            }
//                        });
                break;
            case R.id.login_pw_visible_iv:
                if (eyeOpen) {
                    //密码 TYPE_CLASS_TEXT 和 TYPE_TEXT_VARIATION_PASSWORD 必须一起使用
                    loginPWEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                    imageView.setImageResource( R.mipmap.eye_close );
                    eyeOpen = false;
                } else {
                    //明文
                    loginPWEdt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                    imageView.setImageResource( R.mipmap.eye_open );
                    eyeOpen = true;
                }
                break;
            case R.id.login_reader_card:
                Intent inten = new Intent(LoginActivity.this, ContactReaderCardActivity.class);
                inten.putExtra("type", LOGIN_READER_CARD);
                startActivityForResult(inten, LOGIN_READER_CARD_CODE);
                break;
        }
    }

    boolean eyeOpen = false;

    /**
     * 登录
     *
     * @param name
     * @param pw
     */
    private void initLogin(String name, String pw,String type) {
        HttpAdapter.getService().getLogin(name, pw,type).enqueue(new OnResponseListener<BaseResultModel<UserModel>>(this) {
            @Override
            protected void onSuccess(BaseResultModel<UserModel> resultModel) {
                showToast(resultModel.getMsg());
                BaseApplication.get().setUserModel(resultModel.getData());
                finish();
            }
        });
    }

    /**
     * umengQQ第三方登录的授权接口
     */
    UMAuthListener umdelAuthListener = new UMAuthListener() {

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            showToast("授权完成");
            mShareAPI.getPlatformInfo(LoginActivity.this, platform, new UMAuthListener() {

                @Override
                public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                    LogUtils.D("map=--->" + map);
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        LogUtils.i("key= " + entry.getKey(), " and value= " + entry.getValue());
                    }

                }

                @Override
                public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                    showToast("授权失败");
                }

                @Override
                public void onCancel(SHARE_MEDIA share_media, int i) {
                    showToast("授权取消");
                }
            });
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            showToast("Authorize fail");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            showToast("Authorize cancel");
        }
    };

    /**
     * 对于删除授权使用的接口是
     */
    private void deleteAuth() {
        mShareAPI.deleteOauth(this, SHARE_MEDIA.QQ, umdelAuthListener);
    }


    public static final int LOGIN_WEIXIN_REQUEST_CODE = 628;
    public static final int LOGIN_WEIXIN_RESULT_CODE = 62;
    public static final String WX_userinfo_Url = "https://api.weixin.qq.com/sns/userinfo?";


    /*
            {"openid":"oYZKow8-nx7a9oIhoyax-d5_-BVE",
     "nickname":"彬彬","sex":1,"language":"zh_CN","city":"Hefei",
     "province":"Anhui","country":"CN",
     "headIvurl":"http:\/\/wx.qlogo.cn\/mmopen\/Q3auHgzwzM4dicjpepwnqPmBCmibZKDzkVgzpnqdpf45WZkcVbMicbEiaXh3nWqulCngwLw6kGFUGDQBtpBBNlMQl1ibYMdsJevnbIe2FB3pvkao\/0",
     "privilege":[],"unionid":"oEwx2xNp3CTmQ2sq-A-GTel3ZjnY"}

     */
    String openid;
    String access_token;
    private final String LOGIN_QQ = "QQ";
    private final String LOGIN_WEIXIN = "weixin";

    private void checkWeixinLogin(final String openid, final String access_token,final Map<String, String> map,final String type) {
        HttpRequest.checkWeixinLogin(openid, access_token, new HttpRequest.RequestBaseModelCallBack() {
            @Override
            public void onSuccess(BaseModel baseModel) {
                BaseApplication.get().setUserModel((UserModel) baseModel);
                finish();
            }

            @Override
            public void onUnSuccess(String msg) {
                    weixinRegister(map,openid,type);
            }
        });
    }

    /**
     * 得到微信返回的结果,修改个人信息.
     *
     * @param map
     */
    private void weixinRegister(Map<String, String> map,String openid,String type) {
        String nickname = map.get("name");
        String sex;
        String headIvUrl;
        if (type.equals(LOGIN_WEIXIN)) {
            sex = map.get("gender");
            headIvUrl = map.get("iconurl");
        } else {
            sex = map.get(LoginChar.GENDER);
            headIvUrl = map.get(LoginChar.FIGUREURL);
        }
        HttpAdapter.getService().saveWeixinInfo(openid, nickname, headIvUrl, sex).enqueue(new OnResponseNoDialogListener<BaseResultModel<UserModel>>() {
            @Override
            protected void onSuccess(BaseResultModel<UserModel> checkSignModel) {
                if (checkSignModel.getCode() == 1) {
                    BaseApplication.get().setUserModel(checkSignModel.getData());
                    finish();
                } else {
                    showToast(checkSignModel.getMsg());
                }
            }
        });
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_WEIXIN_REQUEST_CODE) {
            if (resultCode == LOGIN_WEIXIN_RESULT_CODE) {
                boolean isFinish = data.getBooleanExtra("finish", false);
                if (isFinish) {
                    finish();
                }
            }
        } else {
            if (requestCode == com.tencent.connect.common.Constants.REQUEST_LOGIN) {
                tecentLogin.onActivityResultData(requestCode, resultCode, data);
            } else if (requestCode==registerResultCode){
                finish();
            }else if (requestCode==LOGIN_READER_CARD_CODE){
                finish();
            } else {
                LogUtils.D("微信登录");
                mShareAPI.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
