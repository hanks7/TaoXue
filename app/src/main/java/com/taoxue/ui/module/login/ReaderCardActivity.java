package com.taoxue.ui.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.BaseListModel;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.BindSuceessInfo;
import com.taoxue.ui.model.CheckSignModel;
import com.taoxue.ui.model.MyLibInfo;
import com.taoxue.ui.model.ReaderCodeModel;
import com.taoxue.ui.module.classification.CommitContent;
import com.taoxue.ui.module.mine.SeccessBindCardActivity;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.LogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;


public class ReaderCardActivity extends BaseActivity {

    @BindView(R.id.reader_skip_tv)
    TextView readerSkipTv;
    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.reader_code)
    EditText readerCode;
    @BindView(R.id.reader_codeBtn)
    TextView readerCodeBtn;
    @BindView(R.id.reader_xing_Edt)
    EditText readerXingEdt;
//    @BindView(R.id.reader_BTN)
//    RelativeLayout readerBTN;
    private ReaderCodeModel readerCodeModel; //读者证验证请求结果信息
//    /**
//     * 注册时用户名
//     */
//    private  String name;
    /**
     * 返回结果码
     */
    private  int requestCode=1;

    private  String mReaderCode;
    /**
     * 姓
     */
    private  String xing;


    private String user_id;

    private  List<MyLibInfo>myLibInfos;

    //判断是否从我的图书进入验证读者证的 默认false;
    private  boolean isReaderCard=false;

    private  boolean isVerificationReadCard=false;//获取到了读者证

    private  boolean isTestReaCard=false;//是否验证了读者证

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_reader_activity);
        ButterKnife.bind(this);
        getIntentData();
        onInit();
        topbar.setBackButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isReaderCard) {
                    setResult(CommitContent.UNGETREADERID,"","");
                }
               onBackPressed();
            }
        });
    }



    private void setResult(String reader_card_id,String name,String cgs_id){
        Intent intent=new Intent();
        if (!TextUtils.isEmpty(reader_card_id)) {
            Bundle bundle = new Bundle();
            bundle.putString("reader_card_id", reader_card_id);
            bundle.putString("cgs_id", cgs_id);
            bundle.putString("name",name);
            intent.putExtras(bundle);
        }

        this.setResult(requestCode,intent);
        finish();
    }


    /**
     * 获取传递用户名
     */
    private  void getIntentData(){

        Intent intent=getIntent();
        if (CommitContent.MY_LIB_CANSHU.equals(intent.getStringExtra(CommitContent.MY_LIB))){
            user_id=CommitContent.isLogin(this);
            LogUtils.D("获取参数");
            readerSkipTv.setVisibility(View.GONE);
            topbar.setTitle(getResources().getString(R.string.bind_reader_card));
            isReaderCard=true;
//            String user_id=CommitContent.isLogin(this);
//            if (user_id!=null){
//                queryMyLib(user_id);
//            }
        }
        LogUtils.D("isReaderCard--->"+isReaderCard);
}

    private String cgs_id;
    /**
     * 获取读者证的信息
     *
     * @param mReaderCode
     */
    private void initReaderCodeByUser(String mReaderCode) {
        Call<BaseResultModel<ReaderCodeModel>> call = HttpAdapter.getService().testReaderCode(mReaderCode,xing);
        call.enqueue(new OnResponseListener<BaseResultModel<ReaderCodeModel>>(this) {
            @Override
            protected void onSuccess(BaseResultModel baseResultModel) {
                isTestReaCard=true;
                if (baseResultModel.getCode()!=1){
                    showToast(baseResultModel.getMsg());
                    return;
                }

//                             showToast(baseResultModel.getMsg());
                readerCodeModel = (ReaderCodeModel) baseResultModel.getData();
                if (readerCodeModel != null & !("[]").equals(readerCodeModel+"")) {
                    bindReaderCardIdByUserId(readerCodeModel.getCgs_id());
                }

//                                     setResult(readerCodeModel.getReader_id(),readerCodeModel.getCgs_id());

//
//                    isVerificationReadCard=true;
//                    name=TaoXueApplication.get().getUserModel().getName();
//                    LogUtils.D("name---->"+name);
//                    cgs_id=readerCodeModel.getCgs_id();
//                    //判断注册时的姓名 与通过读者证获取的姓名是否相同
//                    if (name.equals(readerCodeModel.getRealname())) {
//
////                        readerXingEdt.setText(readerCodeModel.getRealname());
////                        readerXingEdt.setFocusable(false);
//                    } else {
//                       showToast("您的姓名与读者证上姓名不一致，您可以输入姓进行验证");
//                    }
//                }
            }
        });
    }


    //查询我的图书馆信息
    private  void queryMyLib(String user_id){
        Call<BaseListModel<MyLibInfo>> call = HttpAdapter.getService().queryMyLibInfo(user_id);
       call.enqueue(new OnResponseNoDialogListener<BaseListModel<MyLibInfo>>() {
           @Override
           protected void onSuccess(BaseListModel<MyLibInfo> myLibInfoBaseListModel) {
               if (myLibInfoBaseListModel!=null){
                   if (myLibInfoBaseListModel.getItem().get(0).getReader_card_id()!=null){
                    showToast("您已绑定过读者证，请返回");
                       readerCode.setText(CommitContent.nullToSting(myLibInfoBaseListModel.getItem().get(0).getReader_card_id()));
                       readerCodeBtn.setEnabled(false);
//                       myLibInfos=myLibInfoBaseListModel.getItem();
                   }else{
                    myLibInfos=myLibInfoBaseListModel.getItem();
                   }
               }
           }
       });
    }
    //绑定读者证    "1555232","125222","1201431098" 测试时使用
    private  void bindReaderCardIdByUserId(String cgs_id){
        LogUtils.D("cgs_id---->"+cgs_id+"user_id---->"+user_id+"mReaderCode---->"+mReaderCode);
        Call<CheckSignModel> call = HttpAdapter.getService().bindReaderCardIdByUserId(user_id,cgs_id,mReaderCode,xing);
       call.enqueue(new OnResponseListener<CheckSignModel>(this) {
           @Override
           protected void onSuccess(CheckSignModel checkSignModel) {
               if (checkSignModel.getCode()==1){
                   BindSuceessInfo info=new BindSuceessInfo();
                   info.setReader_card_id(mReaderCode);
                   info.setMyLib_name(readerCodeModel.getCgs_name());
                   info.setReaderName(xing);
                   Intent intent =new Intent();
                   Bundle bundle=new Bundle();
                   bundle.putSerializable(CommitContent.BIND_READER_CARD_ID_SUCCESS,info);
                   intent.putExtras(bundle);
                   launch(SeccessBindCardActivity.class,intent);
               finish();
               }else{
                   showToast(checkSignModel.getMsg());
               }
               LogUtils.D("checkSignModel.getCode()--->"+checkSignModel.getCode());

//               isTestReaCard=true;
//               isVerificationReadCard=true;
//               readerXingEdt.setText(CommitContent.nullToSting(TaoXueApplication.get().getUserModel().getName()));
           }
       });
    }

    /**
     * 验证读者证
     *
     * @param mReaderCode
     */
    private void initReaderCode(final String mReaderCode,final String xing) {
        Call<BaseResultModel<ReaderCodeModel>> call = HttpAdapter.getService().testReaderCode(mReaderCode,xing);
        call.enqueue(new OnResponseListener<BaseResultModel<ReaderCodeModel>>(this) {
                         @Override
                         protected void onSuccess(BaseResultModel baseResultModel) {
                             isTestReaCard=true;
                             if (baseResultModel.getCode()!=1){
                                 showToast(baseResultModel.getMsg());
                                 return;
                             }
                             readerCodeModel = (ReaderCodeModel) baseResultModel.getData();
                             setResult(mReaderCode,readerCodeModel.getRealname(), readerCodeModel.getCgs_id());

//                             LogUtils.D("baseResultModel--->"+baseResultModel.toString());
//
//                             LogUtils.D("readerCodeModel.getRealname()" + readerCodeModel.getRealname()+",name-->"+name);
//                             if (!TextUtils.isEmpty(name) && readerCodeModel != null &&  !("[]").equals(readerCodeModel+"") ){
//                                 isVerificationReadCard=true;
//                                 //判断注册时的姓名 与通过读者证获取的姓名是否相同
//                                 if (name.equals(readerCodeModel.getRealname())) {
////                                     setResult(readerCodeModel.getReader_id(),readerCodeModel.getCgs_id());
//                                     readerXingEdt.setText(readerCodeModel.getRealname());
//                                     readerXingEdt.setFocusable(false);
//                                 } else {
//                                     showToast("您输入的注册真实性别名与读者证上真实性名不一致，请输入您的姓");
////                                     readerCode.setFocusable(false);
//                                     readerXingEdt.setFocusable(true);
//                                 }
//                             }
                         }
                     });
            }
    @OnClick({R.id.reader_skip_tv
//            , R.id.reader_BTN
            ,R.id.reader_codeBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.reader_skip_tv:
                LogUtils.D("点击了跳转");
                    setResult(CommitContent.ZHIJIEZHUCE," "," ");
                break;
//            case R.id.reader_BTN:
//                LogUtils.I("点击了确定");
//
//                xing =readerXingEdt.getText().toString();
//                if (TextUtils.isEmpty(xing)){
//                    showToast("请输入姓");
//                    return;
//                }
//
//                if (!isTestReaCard){
//                    showToast("请先验证读者证");
//                    return;
//                }
//                if (!isVerificationReadCard){
//                    showToast("未获取到您验证的读者证信息");
//                    return;
//                }
//
//                if (isReaderCard){
//                    if (xing.trim().substring(0,1).equals(readerCodeModel.getRealname().trim().substring(0,1))){
//                        bindReaderCardIdByUserId(cgs_id);
//                    }else{
//                        showToast("您输入的姓不正确，请重新输入");
//                    }
//                    return;
//                }
//                        LogUtils.D("通过读者证获取的姓-->"+readerCodeModel.getRealname().trim().substring(0,1));
//                        if (xing.trim().substring(0,1).equals(readerCodeModel.getRealname().trim().substring(0,1))){
//                                setResult(readerCodeModel.getReader_id(), readerCodeModel.getCgs_id());
//                        }else{
//                            showToast("您输入的姓不正确，请重新输入");
//                        }
//                break;
            case  R.id.reader_codeBtn:
                xing=readerXingEdt.getText().toString();
                if (TextUtils.isEmpty(xing)){
                    showToast("请先输入姓名");
                  return;
                }


                    mReaderCode = readerCode.getText().toString();
                LogUtils.D("输入的读者证号-->"+mReaderCode);

                if (TextUtils.isEmpty(mReaderCode)) {
                  showToast("请输入读者证号");
                    return;
                };
                if (isReaderCard){
                    initReaderCodeByUser(mReaderCode);
                }else {
                        initReaderCode(mReaderCode,xing);
                }
                break;
        }
    }
}
