package com.taoxue.ui.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.BaseModel;
import com.taoxue.ui.model.CheckSignModel;
import com.taoxue.ui.model.YzmBean;
import com.taoxue.ui.module.classification.HttpRequest;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class RelationPhoneActivity extends BaseActivity {


    @BindView(R.id.relphone_skip_tv)
    TextView relphoneSkipTv;
    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.relphone_name_Edt)
    EditText relphoneNameEdt;
    @BindView(R.id.relphone_PW_Edt)
    EditText relphonePWEdt;
    @BindView(R.id.relphone_pw_visible_iv)
    ImageView relphonePwVisibleIv;
    @BindView(R.id.relphone_phone_code_Edt)
    EditText relphonePhoneCodeEdt;
    @BindView(R.id.relphone_codeBtn)
    TextView relphoneCodeBtn;
    @BindView(R.id.relphone_BTN)
    Button relphoneBTN;

    String access_token;
    String weinxinOpenid;
    private String mobile;
    private String pwd;
    String code;
    TimeCount timeCount;
    public static final int RELATION_PHONE_WEIXIN_REQUEST_CODE = 28;
    public static final int RELATION_PHONE_WEIXIN_RESULT_CODE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation_phone);
        ButterKnife.bind(this);
        getIntentData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(true);
    }

    private void setResult(boolean isFinish){
        Intent intent=new Intent();
        Bundle bundle = new Bundle();
        bundle.putBoolean("finish",isFinish);
        intent.putExtras(bundle);
        this.setResult(LoginActivity.LOGIN_WEIXIN_RESULT_CODE,intent);
        finish();
    }
    private void setResult(Intent data) {
        this.setResult(LoginActivity.LOGIN_WEIXIN_RESULT_CODE,data);
        finish();
    }


    private void getIntentData() {
        weinxinOpenid = (String) getIntentKey1();
        access_token=(String)getIntentKey2();
        LogUtils.D("weinxinOpenid--->"+weinxinOpenid);
        LogUtils.D("access_token--->"+access_token);
    }
    /*
         **
                 * 验证手机格式
         */
    public static boolean isMobileNO(String mobile) {
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


    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
//            registerCodeBtn.setTextColor(getResources().getColorStateList(R.color.color666));
            relphoneCodeBtn.setClickable(false);
            relphoneCodeBtn.setText(millisUntilFinished / 1000 + "秒后可重新发送");
        }

        @Override
        public void onFinish() {
            relphoneCodeBtn.setTextColor(getResources().getColorStateList(R.color.color666));
            relphoneCodeBtn.setText("重新获取验证码");
            relphoneCodeBtn.setClickable(true);
        }
    }
    private String phoneCode;
    /**
     * 发送验证码
     *
     * @param phone
     */
    private void initCode(String phone) {
        HttpRequest.sendMobileCode(phone, this, new HttpRequest.RequestResultCallBack() {
            @Override
            public void onSuccess(String msg, BaseModel baseModel) {
                showToast(msg);
                YzmBean yzm = (YzmBean) baseModel;
//                    LogUtils.D("cbb--->" + yzm.toString());
                phoneCode = yzm.getYzm();
                LogUtils.D("cbbcode--->" + phoneCode);
            }

            @Override
            public void onFailure(String msg) {
                timeCount.cancel();
                showToast(msg);
                relphoneCodeBtn.setTextColor(getResources().getColorStateList(R.color.color666));
                relphoneCodeBtn.setText("重新获取验证码");
                relphoneCodeBtn.setClickable(true);
            }
        });
    }


    private  void weixinBindPhone(){//微信绑定手机号
        Call<CheckSignModel>call= HttpAdapter.getService().weixinRelationPhone(weinxinOpenid,mobile,pwd," "," ");
        call.enqueue(new OnResponseNoDialogListener<CheckSignModel>() {
            @Override
            protected void onSuccess(CheckSignModel checkSignModel) {
                if (checkSignModel.getCode()==1){
                    launchForResult(RelationReaderCardActivity.class,RELATION_PHONE_WEIXIN_REQUEST_CODE,weinxinOpenid,access_token);
//                    checkWeixinLogin(false);
                }else{
                    showToast(checkSignModel.getMsg());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RELATION_PHONE_WEIXIN_RESULT_CODE){
                if (data!=null) {
                    setResult(data);
                }
        }
    }

    boolean  eyeOpen=false;
    @OnClick({R.id.relphone_skip_tv, R.id.relphone_pw_visible_iv, R.id.relphone_codeBtn, R.id.relphone_BTN})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relphone_skip_tv:
                setResult(true);
//                launchForResult(RelationReaderCardActivity.class,RELATION_PHONE_WEIXIN_REQUEST_CODE,weinxinOpenid,access_token);
                break;
            case R.id.relphone_pw_visible_iv:
                if ( eyeOpen ){
                    //密码 TYPE_CLASS_TEXT 和 TYPE_TEXT_VARIATION_PASSWORD 必须一起使用
                    relphonePWEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                    imageView.setImageResource( R.mipmap.eye_close );
                    eyeOpen = false ;
                }else {
                    //明文
                    relphonePWEdt.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
//                    imageView.setImageResource( R.mipmap.eye_open );
                    eyeOpen = true ;
                }
                break;
            case R.id.relphone_codeBtn:
                String phone = relphoneNameEdt.getText().toString().trim();
                if (!phone.equals("") && phone != null) {
                    if (isMobileNO(phone)) {
                        timeCount = new TimeCount(60000, 1000);
                        timeCount.start();
                        initCode(phone);
                    } else {
                        showToast("请输入正确的手机号码");
                    }
                    ;
                }else{
                    showToast("请先输入手机号");
                }
                break;
            case R.id.relphone_BTN:
                code = relphonePhoneCodeEdt.getText().toString();
                mobile = relphoneNameEdt.getText().toString();
                pwd = relphonePWEdt.getText().toString();
                if (mobile==null){
                    showToast("请先输入手机号");
                    return;
                }

                if (pwd==null){
                    showToast("请先输入密码");
                }
                if (phoneCode==null){
                    showToast("请先验证手机号");
                    return;
                }
                if (code==null){
                    showToast("请先输入验证码");
                }

                if (code.equals(phoneCode)) {
                     weixinBindPhone();
                    LogUtils.D("跳转到验证cardCode页中");
                } else {
                    showToast("输入的验证码错误,请重新输入");
                }
//                }
                break;
        }
    }
}
