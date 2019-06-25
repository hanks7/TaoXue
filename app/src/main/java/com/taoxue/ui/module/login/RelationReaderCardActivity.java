package com.taoxue.ui.module.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.app.TaoXueApplication;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.ui.model.BaseModel;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.CheckSignModel;
import com.taoxue.ui.model.ReaderCodeModel;
import com.taoxue.ui.model.UserModel;
import com.taoxue.ui.module.classification.HttpRequest;
import com.taoxue.utils.HttpUtils;
import com.taoxue.utils.LogUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class RelationReaderCardActivity extends BaseActivity {



    @BindView(R.id.reader_skip_tv)
    TextView readerSkipTv;
    @BindView(R.id.relReaderCard_name_Edt)
    EditText relReaderCardNameEdt;
    @BindView(R.id.relReaderCard_phone_code_Edt)
    EditText relReaderCardPhoneCodeEdt;
    @BindView(R.id.relReaderCard_codeBtn)
    TextView relReaderCardCodeBtn;
    String access_token;
    String weinxinOpenid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation_reader_card);
        ButterKnife.bind(this);
        getIntentData();
    }
    private void setResult(boolean isFinish){
            Intent intent=new Intent();
            Bundle bundle = new Bundle();
            bundle.putBoolean("finish",isFinish);
            intent.putExtras(bundle);
        this.setResult(RelationPhoneActivity.RELATION_PHONE_WEIXIN_RESULT_CODE,intent);
        finish();
    }


    private void getIntentData() {
        weinxinOpenid = (String) getIntentKey1();
        access_token = (String) getIntentKey2();
        LogUtils.D("weinxinOpenid--->"+weinxinOpenid);
        LogUtils.D("access_token--->"+access_token);
    }

    private  void queryWeixiInfo(){
        LogUtils.D("kashi");
        AsyncTask<String,String,Map<String,String>> task=new AsyncTask<String, String, Map<String, String>>() {
            @Override
            protected Map<String, String> doInBackground(String... params) {
                Map<String,String>map=null;
                try{
                    String url=LoginActivity.WX_userinfo_Url+"openid="+weinxinOpenid+"&access_token="+access_token;
                    LogUtils.D("url---->"+url);
                    String s= HttpUtils.doGet(url);
                    if (s==null){
                        return null;
                    }
                    LogUtils.D("S---->"+s);
                    JSONObject obj=new JSONObject(s);

                    String nickname=obj.getString("nickname");
                    String sex= obj.getString("sex");
                    String headimgurl= obj.getString("headimgurl");
                    map=new HashMap<>();
                    map.put("openid",weinxinOpenid);
                    map.put("nickname",nickname);
                    map.put("sex",sex);
                    map.put("headimgurl",headimgurl);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return map;
            }

            @Override
            protected void onPostExecute(Map<String, String> map) {//执行跳转
                LogUtils.D("map---->"+map);
                if (null==map){
                    showToast("登录异常");
                }else{
//                    weixinRegister(map,access_token);
                }
            }
        };
        task.execute();

    }
//    private  void weixinRegister(Map<String,String>map, final String token){
//        final String oppenid=map.get("openid");
//        String nickname=map.get("nickname");
//        String  sex=map.get("sex");
//        String   headimgurl=map.get("headimgurl");
//        Call<CheckSignModel>call=HttpAdapter.getService().saveWeixinInfo(oppenid,nickname,headimgurl,sex);
//        call.enqueue(new OnResponseNoDialogListener<CheckSignModel>() {
//            @Override
//            protected void onSuccess(CheckSignModel checkSignModel) {
//                if (checkSignModel.getCode()==1){
//                    checkWeixinLogin();
//                }else{
//                    showToast(checkSignModel.getMsg());
//                }
//            }
//        });
//
//
//    }


    //绑定读者证    "1555232","125222","1201431098" 测试时使用
    private  void bindReaderCardIdByUserId(String user_id,String cgs_id){
        LogUtils.D("cgs_id---->"+cgs_id+"user_id---->"+user_id+"mReaderCode---->"+mReaderCode);
        Call<CheckSignModel> call = HttpAdapter.getService().bindReaderCardIdByUserId(user_id,cgs_id,mReaderCode,name);
        call.enqueue(new OnResponseListener<CheckSignModel>(this) {
            @Override
            protected void onSuccess(CheckSignModel checkSignModel) {
                if (checkSignModel.getCode()==1){
                   setResult(true);
//                    finish();
                }else{
                    showToast(checkSignModel.getMsg());
                }
                LogUtils.D("checkSignModel.getCode()--->"+checkSignModel.getCode());
            }
        });
    }
    private  void checkWeixinLogin() {
        HttpRequest.checkWeixinLogin(weinxinOpenid, access_token, new HttpRequest.RequestBaseModelCallBack() {
            @Override
            public void onSuccess(BaseModel baseModel) {
                UserModel model=(UserModel) baseModel;
                TaoXueApplication.get().setUserModel(model);
                bindReaderCardIdByUserId(model.getUser_id(),readerCodeModel.getCgs_id());
            }

            @Override
            public void onUnSuccess(String msg) {
                showToast(msg);
//                queryWeixiInfo(oppenid, access_token);
            }
        });
    }
   ReaderCodeModel readerCodeModel;
    /**
     * 获取读者证的信息
     *
     * @param mReaderCode
     */
    private void initReaderCodeByUser(String mReaderCode) {
        Call<BaseResultModel<ReaderCodeModel>> call = HttpAdapter.getService().testReaderCode(mReaderCode,name);
        call.enqueue(new OnResponseListener<BaseResultModel<ReaderCodeModel>>(this) {
            @Override
            protected void onSuccess(BaseResultModel baseResultModel) {
                if (baseResultModel.getCode()!=1){
                    showToast(baseResultModel.getMsg());
                    return;
                }
                readerCodeModel = (ReaderCodeModel) baseResultModel.getData();
                if (readerCodeModel != null & !("[]").equals(readerCodeModel+"")) {
                    checkWeixinLogin();
                }
            }
        });
    }

    String name;
    String mReaderCode;

    @OnClick({R.id.reader_skip_tv, R.id.relReaderCard_codeBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reader_skip_tv:
                  setResult(true);
                break;
            case R.id.relReaderCard_codeBtn:
                name=relReaderCardNameEdt.getText().toString();
                if (TextUtils.isEmpty(name)){
                    showToast("请先输入姓名");
                    return;
                }
                mReaderCode = relReaderCardPhoneCodeEdt.getText().toString();
                LogUtils.D("输入的读者证号-->"+mReaderCode);

                if (TextUtils.isEmpty(mReaderCode)) {
                    showToast("请输入读者证号");
                    return;
                };

                    initReaderCodeByUser(mReaderCode);
                break;
        }
    }
}
