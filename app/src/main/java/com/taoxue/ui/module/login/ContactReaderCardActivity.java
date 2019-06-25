package com.taoxue.ui.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.app.TaoXueApplication;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.BaseListModel;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.CheckSignModel;
import com.taoxue.ui.model.LibrayInfoBean;
import com.taoxue.ui.model.ProvinceBean;
import com.taoxue.ui.model.UserModel;
import com.taoxue.ui.module.mine.MyLibraryActivity;
import com.taoxue.ui.view.TopBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactReaderCardActivity extends BaseActivity {

    @BindView(R.id.introduction_topbar)
    TopBar introductionTopbar;
    @BindView(R.id.sp_province)
    Spinner spProvince;
    @BindView(R.id.sp_lib)
    Spinner spLib;
    @BindView(R.id.et_reader_card)
    EditText etReaderCard;
    @BindView(R.id.et_pw)
    EditText etPw;
    @BindView(R.id.r_checkbox)
    CheckBox rCheckbox;
    @BindView(R.id.r_xieyi)
    TextView rXieyi;
    @BindView(R.id.guanlian_BTN)
    RelativeLayout guanlianBTN;

    String selectedLibray;
    String libCgs_id;

    String type;//"mycard"代表关联读者证
    @BindView(R.id.text_login)
    TextView textLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_reader_card);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if (type.equals(LoginActivity.LOGIN_READER_CARD)) {
            introductionTopbar.setTitle("读者证登录");
            textLogin.setText("进入阅读");
        }
        getProvince();
        onClick();

    }

    private void onClick() {
        guanlianBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!rCheckbox.isChecked()) {
                    showToast("请先阅读用户协议及隐私条款");
                    return;
                }
                if (libCgs_id == null || libCgs_id.equals("")) {
                    showToast("请选择图书馆");
                    return;
                }
                String Reader_card_id = etReaderCard.getText().toString();
                String pw = etPw.getText().toString();
                if (Reader_card_id == null || Reader_card_id.equals("")) {
                    showToast("请输入读者证");
                    return;
                }
                if (pw == null || pw.equals("")) {
                    showToast("请输入密码");
                    return;
                }
                if (type.equals(LoginActivity.LOGIN_READER_CARD)){
                    readCardLogin(Reader_card_id,pw);
                }else{
                HttpAdapter.getService().saveBindingReaderCardId(Reader_card_id, libCgs_id, pw).enqueue(new OnResponseNoDialogListener<CheckSignModel>() {
                    @Override
                    protected void onSuccess(CheckSignModel checkSignModel) {
                        if (checkSignModel.getCode() == 1) {
//                        showToast("读者证绑定成功");
                            ContactReaderCardActivity.this.setResult(MyLibraryActivity.GUANLIAN_READER_CARD);
                            finish();
                        } else {
                            showToast(checkSignModel.getMsg());
                        }
                    }
                });
                }

            }
        });


    }
  public void readCardLogin(String card_id ,String pw){
    HttpAdapter.getService().getLoginByCgsId(libCgs_id,card_id,pw,"library").enqueue(new OnResponseNoDialogListener<BaseResultModel<UserModel>>() {
        @Override
        protected void onSuccess(BaseResultModel<UserModel> userModelBaseResultModel) {
             if (userModelBaseResultModel.getCode()==1&&userModelBaseResultModel.getData()!=null){
                 TaoXueApplication.get().setUserModel(userModelBaseResultModel.getData());
                 ContactReaderCardActivity.this.setResult(LoginActivity.LOGIN_READER_CARD_CODE);
                 finish();
             }else{
                 showToast(userModelBaseResultModel.getMsg());
             }
        }
    });


  }

    public void getProvince() {
        HttpAdapter.getService().qryAllLibraryProvince().enqueue(new OnResponseNoDialogListener<BaseListModel<ProvinceBean>>() {
            @Override
            protected void onSuccess(BaseListModel<ProvinceBean> model) {
                if (model.getItem() != null && model.getItem().size() > 0) {
                    setProvinceSpinner(model.getItem());
                    initLib();

                }
            }
        });

    }

    public void initLib() {
        String[] mItems = new String[1];
        mItems[0] = "请选择图书馆";
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//绑定 Adapter到控件
        spLib.setAdapter(adapter);
    }


    public void setProvinceSpinner(List<ProvinceBean> beans) {
        // 建立数据源
        final String[] mItems = new String[beans.size() + 1];
        mItems[0] = "请选择省";
        for (int i = 0; i < beans.size(); i++) {
            mItems[i + 1] = beans.get(i).getProvince();
        }

// 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//绑定 Adapter到控件
        spProvince.setAdapter(adapter);
        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (pos == 0) {
                    initLib();
                } else {
                    getLib(mItems[pos]);
                }
//              Toast.makeText(ContactReaderCardActivity.this, "你点击的是:"+mItems[pos], 2000).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

    }

    public void getLib(String str) {
        HttpAdapter.getService().qryLibraryByProvince(str).enqueue(new OnResponseNoDialogListener<BaseListModel<LibrayInfoBean>>() {
            @Override
            protected void onSuccess(BaseListModel<LibrayInfoBean> model) {
                if (model.getItem() != null && model.getItem().size() > 0) {
                    setLibSpinner(model.getItem());
                }
            }
        });

    }

    public void setLibSpinner(final List<LibrayInfoBean> beans) {
        // 建立数据源
        final String[] mItems = new String[beans.size() + 1];
        mItems[0] = "请选择图书馆";
        for (int i = 0; i < beans.size(); i++) {
            mItems[i + 1] = beans.get(i).getName();
        }

// 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//绑定 Adapter到控件
        spLib.setAdapter(adapter);
        spLib.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (pos!=0){
                selectedLibray = mItems[pos];
                libCgs_id = beans.get(pos-1).getCgs_id();
                }

//              Toast.makeText(ContactReaderCardActivity.this, "你点击的是:"+mItems[pos], 2000).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

    }


}
