package com.taoxue.ui.module.classification;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.common.base.basecommon.BaseAdapter.Base.RvViewHolder;
import com.common.base.basecommon.BaseAdapter.RvComAdapter;
import com.common.base.basecommon.BaseAdapter.listener.InitViewCallBack;
import com.common.base.basecommon.BaseAdapter.listener.OnItemAdapterClickListener;
import com.common.base.basecommon.tabs.ZTabLayout;
import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.FileUrl;
import com.taoxue.ui.model.MyLibBean;
import com.taoxue.ui.model.MyLibInfo;
import com.taoxue.ui.model.ResourceModel;
import com.taoxue.ui.model.VolumnBean;
import com.taoxue.ui.model.UrlPath;
import com.taoxue.ui.module.classification.vpFragment.BookDeatilFragment;
import com.taoxue.ui.module.classification.vpFragment.CommentFragment;
import com.taoxue.ui.module.classification.vpFragment.ViewPagerAdapter;
import com.taoxue.ui.module.home.PlayActivity;
import com.taoxue.ui.view.StarBar;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.AliPay.AliPayUtil;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.UtilIntent;
import com.taoxue.utils.UtilToast;
import com.taoxue.utils.glide.UtilGlide;
import com.txt.readerlibrary.TxtReader;
import com.uuzuche.lib_zxing.DisplayUtil;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by User on 2017/4/10.
 */

public class BookIntroductionActivity extends BaseActivity {

    @BindView(R.id.introduction_topbar)
    TopBar introductionTopbar;
    @BindView(R.id.book_image_iv)
    ImageView bookImageIv;
    @BindView(R.id.book_title_tv)
    TextView bookTitleTv;
    @BindView(R.id.book_start_read_btn)
    Button startReadBtn;
    @BindView(R.id.book_jieshao_ll)
    LinearLayout bookJieshaoLl;
    @BindView(R.id.book_tablayout)
    ZTabLayout bookTablayout;
    @BindView(R.id.boo_viewpager)
    ViewPager booViewpager;
    @BindView(R.id.book_starbar_pinfen)
    StarBar bookStarbar;
    @BindView(R.id.book_pingfen_coun_tv)
    TextView bookPingfenCounTv;
    @BindView(R.id.book_supplier_tv)
    TextView bookSupplierTv;
    @BindView(R.id.book_total_reading_tv)
    TextView bookTotalReadingTv;
    @BindView(R.id.book_collection_iv)
    ImageView bookCollectionIv;
    @BindView(R.id.book_collection_coumn_tv)
    TextView bookCollectionCoumnTv;
    @BindView(R.id.book_giv_thumb_iv)
    ImageView bookGivThumbIv;
    @BindView(R.id.book_giv_thumb_coumn_tv)
    TextView bookGivThumbCoumnTv;


    RecyclerView commitView;
    @BindView(R.id.book_price_tv)
    TextView bookPriceTv;


    private MyLibBean myLibBean;//我的图书馆信息
    private String id;
    private ResourceModel data;
    private Context context = this;
    private boolean isResource = false;
    private File mFile;
    /**
     * 文件路径
     */
    private List<VolumnBean> urls;

    private String[] mTitles = new String[]{"详情", "评论"};
    //    private CommentDialog dialog;
    private String fileType;

    private int checked;//选择哪个图书馆小标
    private List<MyLibInfo> myLibInfos; //选择的图书馆信息

    RecyclerView dialogRecyclerView;
    SparseArray<RadioButton> mRadioBtns = new SparseArray<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_introduction);
        ButterKnife.bind(this);
        getIntentData();//得到intent中的数据
        //    /判断SD卡是否存在
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            //路径  /storage/emulated/0/Download
            mFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            LogUtils.D("mFile--->" + mFile.getAbsolutePath());
        }
//        //设置向右滑动退出
        CommitContent.setColorLeftSilde(this);
//    addLayout();
    }

    private void initPager() {
        mTitles[1] = "评论" + "(" + data.getComment_num() + ")";
        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mTitles);
        BookDeatilFragment bdf = new BookDeatilFragment();
        bdf.setArgumentsObj(data);
        vpAdapter.addFrament(bdf);
        CommentFragment cf = new CommentFragment();
        cf.setArgumentsObj(data);
        LogUtils.D("data--->" + data.toString());
        vpAdapter.addFrament(cf);
//        CommentFragment cf1=new CommentFragment();
//        cf1.setArgumentsObj(data);
//        LogUtils.D("data--->"+data.toString());
//        vpAdapter.addFrament(cf1);
        bookTablayout.addTab(bookTablayout.newTab().setText(mTitles[0]));
        bookTablayout.addTab(bookTablayout.newTab().setText(mTitles[1]));

        booViewpager.setAdapter(vpAdapter);
        bookTablayout.setupWithViewPager(booViewpager);
        booViewpager.setCurrentItem(0);

    }

    /**
     * 跳转到视频播放界面
     * @param urlBean
     */
    private void intentPlay(UrlPath urlBean) {
        Bundle bundle = new Bundle();
        bundle.putString("user_id", CommitContent.isLogin(this));
        bundle.putString("resource_id", id);
        UtilIntent.intentDIY(this,PlayActivity.class,bundle);
    }

    //获取文件路径
    private void initFileUrl(String resource_id, final String user_id) {
         HttpAdapter.getService().getFileUrl2(resource_id, user_id).enqueue(new OnResponseNoDialogListener<BaseResultModel<FileUrl>>() {
            @Override
            protected void onSuccess(BaseResultModel<FileUrl> model) {
                if (model.getCode() == 1 && null != model.getData().getItem() && !"[]".equals(model.getData().getItem() + "") && model.getData().getItem().size() > 0) {
                    UrlPath urlBean = new UrlPath();
                    FileUrl  seclectModel = model.getData();
                    urlBean.setUrlModel(model.getData().getItem());

                    if (null != seclectModel && !seclectModel.equals("null")) {
                        urlBean.setIs_collection(seclectModel.getIs_collection());
                        urlBean.setIs_comment(seclectModel.getIs_comment());
                        urlBean.setIs_praise(seclectModel.getIs_praise());
                    }
                    toActivityByFileType(urlBean);
                } else {
                    noLibDialogShow(model.getMsg());
                }

            }
        });
    }

    String  yuurl="http://117.71.57.47:10000/resource/uploadFiles/baidutts.apk";
    //根据文件类型跳转至不同的activity
    private void toActivityByFileType(UrlPath urlBean) {
        if (fileType.equals("audio")) {
            launch(AudioPlayActivity.class, urlBean, data);
        } else if (fileType.equals("image")) {
            launch(ImageScanActivity.class, urlBean, data);
        } else if (fileType.equals("video")) {
            intentPlay(urlBean);
        } else if (fileType.equals("doc")) {
            if (urlBean.getUrlModel().get(0).getVolumn().get(0).getUrl().substring(urlBean.getUrlModel().get(0).getVolumn().get(0).getUrl().lastIndexOf(".") + 1).equals("txt")) {
              TxtReader.getTxtReader().openBookByUrl(urlBean.getUrlModel().get(0).getVolumn().get(0).getUrl(), BookIntroductionActivity.this);
            } else {
                launch(DocReadActivity.class, urlBean, data);
            }
        }
    }






    private class OnItemClick implements OnItemAdapterClickListener {
        @Override
        public void onItemClick(View view, RvViewHolder holder, int position, int viewType) {
            mRadioBtns.get(position).setChecked(true);
            checked = position;
            for (int i = 0; i < mRadioBtns.size(); i++) {
                if (i != position) {
                    mRadioBtns.get(i).setChecked(false);
                }
            }
        }
    }

    private void setRecycleViewAdapter()//
    {
        RvComAdapter rvCommonAdapter = new RvComAdapter.Builder<>(this, R.layout.my_lib_choice_item, myLibInfos)
                .setOnItemAdapterClickListener(new OnItemClick())
                .into(dialogRecyclerView,new InitViewCallBack<MyLibInfo>() {
                    @Override
                    public void convert(RvViewHolder viewHolder, MyLibInfo myLibInfo, int i) {
                        viewHolder.setText(R.id.my_lib_name_tv, myLibInfo.getName());
                        final RadioButton rb = viewHolder.getView(R.id.my_lib_set_rb);
                        if (i == 0) {
                            rb.setChecked(true);
                        }

                        mRadioBtns.put(i, rb);

                    }
                });
    }
//    Dialog dialog; //图书馆选择
//    public void show() {
//        dialog = new Dialog(this, R.style.ActionSheetDialogStyle_NoAnimation);
//        //填充对话框的布局
//        View inflate = LayoutInflater.from(this).inflate(R.layout.my_lib_choice, null);
//        dialogRecyclerView = (RecyclerView) inflate.findViewById(android.R.id.keyboardView);
//        setRecycleViewAdapter();
//        ImageView closeTv = (ImageView) inflate.findViewById(R.id.my_lib_delete_iv);
//        closeTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        final CheckBox cb = (CheckBox) inflate.findViewById(R.id.my_lib_checkbox);
//        cb.setChecked(true);
//        Button sureTv = (Button) inflate.findViewById(android.R.id.button1);
//        sureTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LogUtils.D("checkd---->" + checked);
//                if (cb.isChecked()) {
//                    SPUtil.put(SPUtil.MY_PAY_CGS_ID, myLibInfos.get(checked).getCgs_id());
//                }
//                isinitFileUrlBySPUtil = false;
//                String user_id = CommitContent.isLogin(BookIntroductionActivity.this);
//                initFileUrl(data.getResource_id(), user_id, myLibInfos.get(checked).getCgs_id());
//                dialog.dismiss();
//            }
//        });
//        //将布局设置给Dialog
//        dialog.setContentView(inflate);
//        //获取当前Activity所在的窗体
//        Window dialogWindow = dialog.getWindow();
//        //设置Dialog从窗体底部弹出
//        dialogWindow.setGravity(Gravity.CENTER);
//        //获得窗体的属性
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        lp.width = (int) (DisplayUtil.screenWidthPx * 0.85);
////        lp.y = 30;//设置Dialog距离底部的距离
////       将属性设置给窗体
//        dialogWindow.setAttributes(lp);
//
//        dialog.show();//显示对话框
//    }


    Dialog payDialog;

    private void zhifubaoPayResult(String result){//支付宝支付结果
        LogUtils.D("fanhuizhifujieguo");
        payDialog.dismiss();
        final String user_id=CommitContent.isLogin(this);
        Call<BaseResultModel>call=HttpAdapter.getService().zhifubaoPayResult(result);
        call.enqueue(new OnResponseNoDialogListener<BaseResultModel>() {
            @Override
            protected void onSuccess(BaseResultModel baseResultModel) {
                LogUtils.D("baseResultModel---->"+baseResultModel.toString());
                if (baseResultModel.getCode()==1){
                    initFileUrl(id,user_id);
                }
            }
        });
    }



    /**
     * 支付宝支付
     */
    private void pay(String str) {
//        String str2= "alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2017041506732697&biz_content=%7B%22body%22%3A%22%E5%87%BA%E7%A7%9F%E8%BD%A6%E8%AE%A2%E5%8D%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%22cz20170814102940syratjzm%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E4%B9%98%E5%AE%A2App%E6%94%AF%E4%BB%98%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fadmin.ananchuxing.com%2FtaxiOrder%2FcallBack.do&sign=Qg9AUJIshcEYMzerbLtup6eXkVFUXdNlYpKVCZr9%2FcrzXSnrV17WW9A2G8QxzEbdFzXfuQwl5drk4t4ouWJ3ZyE061r75Jt%2B9euVYnj%2FoIpCNwdWZPO%2B1BLy33BvxVh7L2On3YAQclDHnHkb8G2BQIiXpqt6%2B8Stx7NcNv0rbmEJWJvtyraeLRvxe4cmRArDN9gr%2FquG4QIt5bo7NPHKlmhkTyrFQShUNvP9QVqXLjjp4G7zVMnsDjw5%2Fag79yKYVBB5YDexsrwmBRzLFnRtUree17HbTr9XJs%2BzlCwyzHh1WXtYohwWx6HdcUWJl3OcX3kaAmVsEFGD%2FjxKo0cHuA%3D%3D&sign_type=RSA2&timestamp=2017-08-14+10%3A31%3A56&version=1.0";

        AliPayUtil.Builder builder = new AliPayUtil.Builder(BookIntroductionActivity.this);
        builder.setPayCallBackListener(new AliPayUtil.Builder.PayCallBackListener() {
            @Override
            public void onPayCallBack(int status, String resultStatus, String progress) {
                //支付失败  status--->0resultStatus--->0progress--->支付失败
                //支付成功   status--->9000resultStatus--->9000progress--->支付成功
                LogUtils.D("status--->"+status+"resultStatus--->"+resultStatus+"progress--->"+progress);
//                 if (status==)
                if (status==9000) {//支付成功
                    zhifubaoPayResult(progress);
                }
            }
        });
        builder.doPay(str);
    }


    private  void zhifubaoPay(){
        String user_id=CommitContent.isLogin(this);
        Call<BaseResultModel<String>>call=HttpAdapter.getService().zhifubaoPay(user_id,id);
        call.enqueue(new OnResponseNoDialogListener<BaseResultModel<String>>() {
            @Override
            protected void onSuccess(BaseResultModel<String> baseResultModel) {
                LogUtils.D("baseResultModel--->"+baseResultModel.toString());
                if (baseResultModel.getCode()==1){
                    LogUtils.D("baseResultModel--->"+baseResultModel.getData().toString());
                    pay(baseResultModel.getData());
                }
            }
        });
    }

    public void payShow() {//微信  支付宝支付 dialog
        payDialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_pay, null);
        RelativeLayout weinPay = (RelativeLayout) inflate.findViewById(R.id.weixin_pay);

        RelativeLayout aliPay = (RelativeLayout) inflate.findViewById(R.id.ali_pay);
        weinPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog.dismiss();
                showToast("正在计算中");
            }
        });
        aliPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhifubaoPay();
//                showToast("正在计算中");
            }
        });
        ImageView tv = (ImageView) inflate.findViewById(R.id.pay_delete_iv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog.dismiss();
            }
        });
        //将布局设置给Dialog
        payDialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = payDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
//        lp.y = 30;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        payDialog.show();//显示对话框
    }


    //查询我的图书馆信息
    private void queryMyLib(String user_id) {
        HttpRequest.queryReadCardPayInfo(user_id, id, new HttpRequest.RequestMyLibCallBack() {
            @Override
            public void onSuccess(List<MyLibInfo> myLibInfos) {
                BookIntroductionActivity.this.myLibInfos = myLibInfos;
//                show();
            }

            @Override
            public void onUnSuccess(String msg) {
                payShow();
                showToast(msg);
            }
        });
    }
    Dialog noLibDialog;

    public void noLibDialogShow(String msg) {
        noLibDialog = new Dialog(this, R.style.ActionSheetDialogStyle_NoAnimation);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_no_get_lib, null);
        Button sureBtn=(Button)inflate.findViewById(R.id.sure_pay_btn) ;
        final Button cancelBtn=(Button)inflate.findViewById(R.id.no_pay_btn) ;
        ImageView deleteIv=(ImageView)inflate.findViewById(R.id.my_lib_delete_iv);
        TextView payInfoTv=(TextView)inflate.findViewById(R.id.pay_info_tv);
        payInfoTv.setText(nullToSting(msg));
        deleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noLibDialog.dismiss();
            }
        });

        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payShow();
                noLibDialog.dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noLibDialog.dismiss();
            }
        });

        //将布局设置给Dialog
        noLibDialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = noLibDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.CENTER);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.width = (int) (DisplayUtil.screenWidthPx * 0.85);
//        lp.y = 30;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);

        noLibDialog.show();//显示对话框
    }








    boolean isinitFileUrlBySPUtil = false;

    @OnClick({R.id.book_collection_iv, R.id.book_giv_thumb_iv, R.id.book_start_read_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.book_collection_iv:
                break;
            case R.id.book_giv_thumb_iv:
                break;
            case R.id.book_start_read_btn:
                String user_id=null;
                if ("0.00".equals(data.getCharge_value())){
                    user_id=" ";
                }else{
                    user_id = CommitContent.isLogin(BookIntroductionActivity.this);
                }
                if (id!=null&&user_id!=null) {
                    initFileUrl(id,user_id);
                }
//                String cgs_id = (String) SPUtil.get(SPUtil.MY_PAY_CGS_ID, "");
//                if (TextUtils.isEmpty(cgs_id)) {
//                    if (null != user_id) {
//                        queryMyLib(user_id);
//                    }
//                } else {
//                    isinitFileUrlBySPUtil = true;
//                    initFileUrl(data.getResource_id(), user_id, cgs_id);
//                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(id)) {
            initLocation();

        } else {
            showToast("当前无资源");
        }
    }

    /**
     * 得到intent中的数据
     *
     * @return
     */
    private void getIntentData() {
        id = (String) getIntentKey1();
        LogUtils.D("id---->" + id);
    }
    /**
     * 初始化定位
     */
    private void initLocation() {
        AMapLocationClient mLocationClient = new AMapLocationClient(this);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    LogUtils.i("aMapLocation",aMapLocation);
                    LogUtils.i("aMapLocation.getErrorCode() ",aMapLocation.getErrorCode() );
                    if (aMapLocation.getErrorCode() == 0) {
                        String city = aMapLocation.getCity();
                        String province = aMapLocation.getProvince();
                        initResourceDetail( id, province, city);
                    } else {
                        //定位失败
                        UtilToast.showText("定位失败");
                        initResourceDetail( id, null, null);
                    }
                }
            }
        });
        mLocationClient.startLocation();
    }

    //获取资源详情页
    private void initResourceDetail(String id,String province,String city) {
        isResource = false;
        Call<BaseResultModel<ResourceModel>> call = HttpAdapter.getService().getResourceDetail(id,province,city);
        call.enqueue(new OnResponseListener<BaseResultModel<ResourceModel>>(this) {
            @Override
            protected void onSuccess(BaseResultModel<ResourceModel> model) {

                if (model.getCode() == 1 && null != model.getData() && !"[]".equals(model.getData())) {
                    data = model.getData();
                    initView();
                } else {
                    showToast(model.getMsg());
                }
            }

            @Override
            protected void onFailure(String msg) {
                super.onFailure(msg);
            }
        });
    }

    private void initView() {
        fileType = data.getFile_type();

        if (fileType.equals("audio")) {
            introductionTopbar.setTitle("音频介绍");
        } else if (fileType.equals("image")) {
            introductionTopbar.setTitle("图片介绍");
        } else if (fileType.equals("video")) {
            introductionTopbar.setTitle("视频介绍");
        } else if (fileType.equals("doc")) {
            introductionTopbar.setTitle("图书介绍");
        } else {
            introductionTopbar.setTitle("其他");

        }


        isResource = true;
//        Html.fromHtml(data.getCatalog())
        bookTitleTv.setText(nullToSting(data.getResource_name()));
//        <p font='red'>这是测试内容</p>

        String pic_url = data.getResource_picture().replace("_150", "");
        LogUtils.D("pic_url--->" + pic_url);
        UtilGlide.loadImg(this, pic_url, bookImageIv);
//
        bookPriceTv.setText(nullToSting(data.getCharge_value())+"元"); //设置费用
        bookSupplierTv.setText(data.getGys_name());//供应商
        float score = Float.parseFloat(data.getAvg_score());
        BigDecimal b = new BigDecimal(score * 2);
        float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();


        bookPingfenCounTv.setText(f1 + "");//评分数量

        bookStarbar.setStarMark(score);//评分
        bookStarbar.setChangMark(false);

        bookCollectionCoumnTv.setText(nullToSting(data.getCollection_num()));//收藏数量
        bookGivThumbCoumnTv.setText(nullToSting(data.getPraise_num()));//点赞数量
//            bookSupplierTv.setText(nullToSting(data.get));book_total_reading_tv
        bookTotalReadingTv.setText(nullToSting(data.getVisit_num()));//阅读数量
        //将当前数据传递  点击后收藏
//            addBookBtn.setData(data);
//                        bookSuitYearTv.setText(CommitContent.nullToSting(data.get));
        //将当前数据传递到自定义评论控件中
//            commitView.setModel(data);
        initPager();
    }


}


