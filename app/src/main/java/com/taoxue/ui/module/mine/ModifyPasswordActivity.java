package com.taoxue.ui.module.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.ui.model.BaseBean;
import com.taoxue.ui.view.TopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class ModifyPasswordActivity extends BaseActivity {

    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.modify_pw_old_pw)
    EditText modifyPwOldPw;
    @BindView(R.id.modify_pw_photo)
    EditText modifyPwPhoto;
    @BindView(R.id.modify_pw_pw)
    EditText modifyPwPw;
    @BindView(R.id.modify_pw_newpw)
    EditText modifyPwNewpw;
    @BindView(R.id.modify_pw_BTN)
    RelativeLayout modifyPwBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);
    }

    private void updateModifyPw() {
        Call<BaseBean> call = HttpAdapter.getService().modifyPwDo(oldPw, mobile, pwd, newPwd);
        call.enqueue(new OnResponseListener<BaseBean>(this) {
            @Override
            protected void onSuccess(BaseBean baseBean) {
                if (baseBean.getCode() == 1) {
                    showToast("密码修改成功");
                    finish();
                } else {
                    showToast(baseBean.getMsg());
                }
            }
        });
    }

    private String oldPw, mobile, pwd, newPwd;

    @OnClick(R.id.modify_pw_BTN)
    public void onClick() {
        oldPw = modifyPwOldPw.getText().toString();
        mobile = modifyPwPhoto.getText().toString();
        pwd = modifyPwPw.getText().toString();
        newPwd = modifyPwNewpw.getText().toString();
        if (TextUtils.isEmpty(oldPw)) {
            showToast("请输入原密码");
            return;
        }

        if (TextUtils.isEmpty(mobile)) {
            showToast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(newPwd)) {
            showToast("请输入密码");
            return;
        }

        if (!pwd.equals(newPwd)) {
            showToast("请输入一致密码");
        } else {
            updateModifyPw();
        }

    }
}
