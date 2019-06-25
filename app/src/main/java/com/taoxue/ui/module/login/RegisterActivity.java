package com.taoxue.ui.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.app.TaoXueApplication;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.ui.model.BaseModel;
import com.taoxue.ui.model.CheckSignModel;
import com.taoxue.ui.model.UserModel;
import com.taoxue.ui.model.YzmBean;
import com.taoxue.ui.module.classification.CommitContent;
import com.taoxue.ui.module.classification.HttpRequest;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

//import com.taoxue.ui.model.String;

/**
 * 注册
 * Created by User on 2017/4/1.
 */

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.topbar)
    TopBar topbar;
    //    @BindView(R.id.register_name)
//    EditText registerName;
    @BindView(R.id.register_photo)
    EditText registerPhoto;
    @BindView(R.id.register_code)
    EditText registerCode;
    @BindView(R.id.register_codeBtn)
    TextView registerCodeBtn;
    @BindView(R.id.register_pw)
    EditText registerPw;
    //    @BindView(R.id.register_newpw)
//    EditText registerNewpw;
    @BindView(R.id.register_BTN)
    RelativeLayout registerBTN;
    @BindView(R.id.register_checkbox)
    CheckBox registerCheckbox;

    private String reader_card_id;
    private String cgs_id;
//    private int reqCode = 1;
    private String mobile;
    private String pwd;
    String code;
    MobileCode mobileCode;//验证码定时器


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_register);
        ButterKnife.bind(this);
//        registerBTN.setPressed(false);
        registerBTN.setEnabled(false);
    }

    @Override
    protected void onInit() {
        super.onInit();
//        name = registerName.getText().toString();
        code = registerCode.getText().toString();
        mobile = registerPhoto.getText().toString();
        pwd = registerPw.getText().toString();
//        newPwd = registerNewpw.getText().toString();

        //判断 editView是否均为空
        EditTextNotNullListener eitnull=new EditTextNotNullListener();
        eitnull.from(registerBTN).mobile(registerPhoto).mobileCode(registerCode).password(registerPw).load();
    }

    private void setResult(){
        this.setResult(LoginActivity.registerResultCode);
        finish();
    }


    @OnClick({R.id.register_BTN, R.id.register_codeBtn,R.id.register_pw_visible_iv, R.id.register_checkbox, R.id.register_xieyi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_BTN:
//                showToast("点击了下一步");
//                name = registerName.getText().toString();
                code = registerCode.getText().toString();
                mobile = registerPhoto.getText().toString();
                pwd = registerPw.getText().toString();
             boolean isCheck=registerCheckbox.isChecked();

                  if (!isCheck){
                      showToast("请选择用户协议");
                      return;
                  }

                  HttpRequest.checkMESCode(mobile, code, new HttpRequest.CheckResultCallBack() {
                      @Override
                      public void onSuccess() {
                                  HttpRequest.UserRegister(mobile, pwd, new HttpRequest.RequestBaseModelCallBack() {
                                      @Override
                                      public void onSuccess(BaseModel baseModel) {
                                          UserModel userModel=(UserModel) baseModel;
                                          TaoXueApplication.get().setUserModel(userModel);
                                          setResult();
                                      }

                                      @Override
                                      public void onUnSuccess(String msg) {

                                      }
                                  });
                      }
                  });
                break;
            case R.id.register_codeBtn:
                final String phone = registerPhoto.getText().toString().trim();
             mobileCode=new MobileCode(this,phone,registerCodeBtn){
                    @Override
                    protected void sendPhoneCode(String mobile) {
                        initCode(phone);
                    }
                };
                break;
            case R.id.register_pw_visible_iv:
                PwInputType.newInstance(registerPw).changeInputType();
                break;
            case R.id.register_checkbox:

                break;
            case R.id.register_xieyi://用户协议
                 launch(UserAgreementActivity.class);
                break;
        }
    }

    /**
     * 注册
     */
    private void initRegister(String phone, String nikeName, String pwd, String reader_card_id, String cgs_id
    ) {
        Call<CheckSignModel> call = HttpAdapter.getService().getRegister(phone, nikeName, pwd, reader_card_id, cgs_id);
        call.enqueue(new OnResponseListener<CheckSignModel>(this) {
            @Override
            protected void onSuccess(CheckSignModel checkSignModel) {
                showToast(checkSignModel.getMsg());
                LogUtils.D("注册状况");
                if (checkSignModel.getCode() == 1) {
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        reader_card_id = data.getStringExtra("reader_card_id");
        cgs_id = data.getStringExtra("cgs_id");
      String  name=data.getStringExtra("name");
        LogUtils.D("注册");
        switch (requestCode) {
            case 1:
                if (!reader_card_id.equals(CommitContent.UNGETREADERID)) { //验证读者证页中 点击了返回
                    LogUtils.D("注册");
                    if (reader_card_id.equals(CommitContent.ZHIJIEZHUCE)) {//验证读者证页中 点击了跳过 直接注册
                        reader_card_id = "";
                    }
                    initRegister(mobile, name, pwd, reader_card_id, cgs_id);
                }
                break;
        }
    }

    /**
     * 发送验证码
     *
     * @param phone
     */
    private void initCode(String phone) {
        HttpRequest.sendMobileCode(phone, this, new HttpRequest.RequestResultCallBack() {
            @Override
            public void onSuccess(String msg, BaseModel baseModel) {
//                showToast(msg);
//                YzmBean yzm =(YzmBean) baseModel;
////                    LogUtils.D("cbb--->" + yzm.toString());
//                phoneCode = yzm.getYzm();

            }

            @Override
            public void onFailure(String msg) {
               mobileCode.cancelTimeCount();//取消定时器
                showToast(msg);
                registerCodeBtn.setTextColor(getResources().getColorStateList(R.color.white));
                registerCodeBtn.setText("重新获取验证码");
                registerCodeBtn.setClickable(true);
            }
        });
//
//        Call<BaseResultModel<YzmBean>> call = HttpAdapter.getService().sendvalidate(phone);
//        call.enqueue(new OnResponseListener<BaseResultModel<YzmBean>>(this) {
//            @Override
//            protected void onSuccess(BaseResultModel baseResultModel) {
//                if (baseResultModel.getCode()==1) {
//
//                }else{
//                    getCodeFailure(baseResultModel.getMsg());
//                }
//            }
//
//            @Override
//            protected void onFailure(int code) {
//
//            }
//        });

    }

}
