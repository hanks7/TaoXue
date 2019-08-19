package com.taoxue.ui.module.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.ui.model.BaseBean;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.YzmBean;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class ForgotPasswordActivity extends BaseActivity {

    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.forget_pw_photo)
    EditText forgetPwPhoto;
    @BindView(R.id.forget_pw_code)
    EditText forgetPwCode;
    @BindView(R.id.forget_pw_codeBtn)
    TextView forgetPwCodeBtn;
    @BindView(R.id.forget_pw_pw)
    EditText forgetPwPw;
    @BindView(R.id.forget_pw_newpw)
    EditText forgetPwNewpw;
    @BindView(R.id.forget_pw_BTN)
    RelativeLayout forgetPwBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            forgetPwCodeBtn.setTextColor(getResources().getColorStateList(R.color.color666));
            forgetPwCodeBtn.setClickable(false);
            forgetPwCodeBtn.setText(millisUntilFinished / 1000 + "秒后可重新发送");
        }

        @Override
        public void onFinish() {
//            forgetPwCodeBtn.setTextColor(getResources().getColorStateList(R.color.color666));
            forgetPwCodeBtn.setText("重新获取验证码");
            forgetPwCodeBtn.setClickable(true);
        }
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
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobile)) {
            return false;
        } else {
            return mobile.matches(telRegex);
        }
    }

    private TimeCount timeCount;
    private String phoneCode;

    /**
     * 发送验证码
     *
     * @param phone
     */
    private void initCode(String phone) {
        Call<BaseResultModel<YzmBean>> call = HttpAdapter.getService().forgetPwSendSMS(phone);
        call.enqueue(new OnResponseListener<BaseResultModel<YzmBean>>(this) {
            @Override
            protected void onSuccess(BaseResultModel baseResultModel) {
                if (baseResultModel.getCode() == 1) {
                    showToast(baseResultModel.getMsg());
                    YzmBean yzm = (YzmBean) baseResultModel.getData();
//                    LogUtils.D("cbb--->" + yzm.toString());
                    phoneCode = yzm.getYzm();
                    LogUtils.D("cbbcode--->" + phoneCode);
                } else {
                    getFailureCode();
                }
            }

            @Override
            protected void onError(int code, String msg) {
                getFailureCode();
            }


        });
    }

    private void getFailureCode() {
//        showToast(msg);
        timeCount.cancel();
        forgetPwCodeBtn.setTextColor(getResources().getColorStateList(R.color.color666));
        forgetPwCodeBtn.setText("重新获取验证码");
        forgetPwCodeBtn.setClickable(true);
    }

    private String code, mobile, pwd, newPwd;

    private void updateForgetPw() {
        Call<BaseBean> call = HttpAdapter.getService().updateforgetPw(mobile, code, pwd, newPwd);
        call.enqueue(new OnResponseListener<BaseBean>(this) {
            @Override
            protected void onSuccess(BaseBean baseBean) {
                if (baseBean.getCode() == 1) {
                    showToast("密码设置成功，请重新登录");
                    finish();
                } else {
                    showToast(baseBean.getMsg());
                }
            }
        });


    }

    @OnClick({R.id.forget_pw_codeBtn, R.id.forget_pw_BTN})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forget_pw_BTN:
                code = forgetPwCode.getText().toString();
                mobile = forgetPwPhoto.getText().toString();
                pwd = forgetPwPw.getText().toString();
                newPwd = forgetPwNewpw.getText().toString();
                if (TextUtils.isEmpty(mobile) || TextUtils.isEmpty(code)) {
                    showToast("请先验证手机号");
                    return;
                }
                if (!phoneCode.equals(code)) {
                    showToast("您输入的验证码不正确，请重新输入");
                    return;
                }
                if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(newPwd)) {
                    showToast("请输入密码");
                    return;
                }

                if (!pwd.equals(newPwd)) {
                    showToast("请输入一致密码");
                } else {
                    updateForgetPw();
                }

                break;
            case R.id.forget_pw_codeBtn:
                String phone = forgetPwPhoto.getText().toString().trim();
                if (!phone.equals("") && phone != null) {
                    if (isMobileNO(phone)) {
                        timeCount = new TimeCount(60000, 1000);
                        timeCount.start();
                        initCode(phone);
                    } else {
                        showToast("请输入正确的手机号码");
                    }
                    ;
                }
                break;
        }
    }
}
