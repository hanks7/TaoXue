package com.taoxue.ui.module.classification.resourceLib;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.base.basecommon.tabs.ZTabLayout;
import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.ui.model.BaseModel;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.DrDataInfoModel;
import com.taoxue.ui.model.LibInfoModel;
import com.taoxue.ui.model.LibResourceModel;
import com.taoxue.ui.model.ResourceLibModel;
import com.taoxue.ui.module.classification.HttpRequest;
import com.taoxue.ui.module.classification.vpFragment.ViewPagerAdapter;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.glide.UtilGlide;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class ResourceLibraryActivity extends BaseActivity {

    @BindView(R.id.introduction_topbar)
    TopBar introductionTopbar;
    @BindView(R.id.resource_lib_image_iv)
    ImageView resourceLibImageIv;
    @BindView(R.id.resource_lib_title_tv)
    TextView resourceLibTitleTv;
    @BindView(R.id.resource_lib_supplier_tv)
    TextView resourceLibSupplierTv;
    @BindView(R.id.resource_lib_total_resource_tv)
    TextView resourceLibTotalResourceTv;
    @BindView(R.id.resource_lib_total_reading_tv)
    TextView resourceLibTotalReadingTv;
    @BindView(R.id.resource_lib_jieshao_ll)
    LinearLayout resourceLibJieshaoLl;
    @BindView(R.id.resource_lib_tablayout)
    ZTabLayout resourceLibTablayout;
    @BindView(R.id.resource_lib_viewpager)
    ViewPager resourceLibViewpager;

    DrDataInfoModel drModel;
    LibInfoModel libInfoModel;
    LibResourceModel resourceModel;
    private String[] mTitles = new String[]{"详情", "资源"};
    String resource_id;
    int page_NO = 1;
    int page_size = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_library);
        ButterKnife.bind(this);
        getIntentData();


    }

    private void getIntentData() {
        resource_id = (String) getIntentKey1();
        if (resource_id == null) {
            showToast("没有资源");
        } else {
            queryResourceLibDetail();
        }

//        queryResourceLib();
    }

    //查询资源库详情
    public void queryResourceLibDetail() {
        Call<BaseResultModel<LibInfoModel>> call = HttpAdapter.getService().queryResourceLibDeatail(resource_id, page_NO + "", page_size + "");
        call.enqueue(new OnResponseListener<BaseResultModel<LibInfoModel>>(this) {
            @Override
            protected void onSuccess(BaseResultModel<LibInfoModel> libModel) {

                if (libModel.getCode() == 1 && null != libModel.getData()) {
                    libInfoModel = libModel.getData();
                    drModel = libModel.getData().getDr_data_info();
                    if (drModel != null) {
                        initView();//初始化页面
                        if (null != libInfoModel.getResource()) {
                            resourceModel = libInfoModel.getResource();
                            initPager();
                        }
                        return;
                    }
                }

                showToast(libModel.getMsg());
            }
        });
    }


//    //查询资源库详情
//    private void queryResourceLib() {
//        HttpRequest.queryResourceLibDetail(this, new HttpRequest.RequestBaseModelCallBack() {
//            @Override
//            public void onSuccess(BaseModel baseModel) {
//                model = (ResourceLibModel) baseModel;
//                initView();
//            }
//
//            @Override
//            public void onUnSuccess(String msg) {
//                showToast(msg);
//            }
//        });
//    }

    private void initView() {
        UtilGlide.loadImg(this, drModel.getLogo().replace("_150",""), resourceLibImageIv);
        resourceLibTitleTv.setText(nullToSting(drModel.getName()));
        resourceLibSupplierTv.setText(nullToSting(drModel.getGys_name()));
        resourceLibTotalResourceTv.setText(nullToSting(drModel.getResource_num()));
        resourceLibTotalReadingTv.setText(nullToSting(drModel.getVisit_num()));
    }

    private void initPager() {
        mTitles[1] = "资源" + "(" + drModel.getResource_num() + ")";
        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mTitles);
        ResourceLibDetailFragment rldf = new ResourceLibDetailFragment();
        rldf.setArgumentsObj(libInfoModel);
        vpAdapter.addFrament(rldf);
        ResourceLibResoFragment cf = new ResourceLibResoFragment();
        cf.setArgumentsObj(resourceModel);
        vpAdapter.addFrament(cf);
//        CommentFragment cf1=new CommentFragment();
//        cf1.setArgumentsObj(data);
//        LogUtils.D("data--->"+data.toString());
//        vpAdapter.addFrament(cf1);
        resourceLibTablayout.addTab(resourceLibTablayout.newTab().setText(mTitles[0]));
        resourceLibTablayout.addTab(resourceLibTablayout.newTab().setText(mTitles[1]));

        resourceLibViewpager.setAdapter(vpAdapter);
        resourceLibTablayout.setupWithViewPager(resourceLibViewpager);
        resourceLibViewpager.setCurrentItem(0);

    }
}
