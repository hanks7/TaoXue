package com.taoxue.ui.module.home;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.taoxue.MainActivity;
import com.taoxue.R;
import com.taoxue.base.BaseFragment;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.ui.adapter.GvZXSXAdapter;
import com.taoxue.ui.adapter.LsvERBAdapter;
import com.taoxue.ui.adapter.LsvMainFeiAdapter;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.homefrag.ApiOneBean;
import com.taoxue.ui.module.classification.MessageEvent;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.utils.StringUtils;
import com.taoxue.ui.module.search.SearchLibActivity;
import com.taoxue.ui.module.search.SearchResourcesActivity;
import com.taoxue.ui.module.search.WebHomeActivity;
import com.taoxue.ui.module.yuejia.ResourceDetailActivity;
import com.taoxue.ui.view.PopWinMenu;
import com.taoxue.ui.view.TranslucentActionBar;
import com.taoxue.ui.view.TranslucentScrollView;
import com.taoxue.ui.view.UPMarqueeView;
import com.taoxue.ui.view.refresh.MyRefreshLayout;
import com.taoxue.utils.UtilIntent;
import com.taoxue.utils.UtilToast;
import com.taoxue.utils.banner.BannerHelp;
import com.taoxue.utils.glide.UtilGlide;
import com.taoxue.utils.permission.PermissionReq;
import com.taoxue.utils.permission.PermissionResultTask;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;


/**
 * Created by CC on 2016/11/16.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.fa_home_convenientBanner)
    ConvenientBanner convenientBanner;
    @BindView(R.id.fa_home_lsv_erb)
    ListView mLsvMeiTian;
    @BindView(R.id.fa_home_lsv_free)
    ListView mLsvMianFei;

    @BindView(R.id.fa_home_gv_baobao_jinting)
    GridView mGvBaobao;

    @BindView(R.id.fa_home_gv_shaonian_bolan)
    GridView mGvShaoNian;

    @BindView(R.id.fa_home_gv_doukou_nianhua)
    GridView mGvDoukou;

    @BindView(R.id.fa_home_fg_shunfeng_ScrollView)
    TranslucentScrollView mScrollView;
    @BindView(R.id.actionbar)
    TranslucentActionBar mActionbar;
    @BindView(R.id.upview1)
    UPMarqueeView mUPMarqueeView;
    @BindView(R.id.fa_home_img_chaoshihui)
    ImageView fa_home_img_chaoshihui;
    @BindView(R.id.fa_home_text_chaoshihui)
    TextView fa_home_text_chaoshihui;
    @BindView(R.id.fa_home_img_zuiaiting)
    ImageView fa_home_img_zuiaiting;
    @BindView(R.id.fa_home_text_zuiaiting)
    TextView fa_home_text_zuiaiting;
    @BindView(R.id.fa_home_img_youhaoshu)
    ImageView fa_home_img_youhaoshu;
    @BindView(R.id.fa_home_text_youhaoshu)
    TextView fa_home_text_youhaoshu;
    @BindView(R.id.fa_home_img_douzaikan)
    ImageView fa_home_img_douzaikan;
    @BindView(R.id.fa_home_text_douzaikan)
    TextView fa_home_text_douzaikan;
    @BindView(R.id.refreshLayout)
    MyRefreshLayout refreshLayout;


    private PopWinMenu pop;
    private BannerHelp bannerHelp;//轮播图
    private AMapLocationClient mLocationClient;


    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onInit() {
        mScrollView.setVerticalScrollBarEnabled(false);
        bannerHelp = new BannerHelp(convenientBanner);//广告轮播
        intAdapter();//初始化adapter
        netWork();//网络请求
        intTopBar();
        setUPmarqueeView();
        initLocation();
        initRefreshLayout();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        //设置默认滚动到顶部
        mScrollView.post(new Runnable() {
            @Override
            public void run() {
                mScrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }


    /**
     * 初始化下拉刷新控件
     */
    private void initRefreshLayout() {
        refreshLayout.disableWhenHorizontalMove(true);
        refreshLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {


                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mScrollView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                netWork();//网络请求
            }
        });
        refreshLayout.addPtrUIHandler(new PtrUIHandler() {
            @Override
            public void onUIReset(PtrFrameLayout frame) {
            }

            @Override
            public void onUIRefreshPrepare(PtrFrameLayout frame) {

            }

            @Override
            public void onUIRefreshBegin(PtrFrameLayout frame) {
            }

            @Override
            public void onUIRefreshComplete(PtrFrameLayout frame) {
            }

            @Override
            public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
                if(status==1){
                    mActionbar.setVisibility(View.VISIBLE);
                }else{
                    mActionbar.setVisibility(View.GONE);
                }

            }
        });
    }

    /**
     * 初始化头部
     */
    private void intTopBar() {
        PermissionReq.with(getActivity())
                .permissions(Manifest.permission.ACCESS_COARSE_LOCATION)
                .permissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .result(new PermissionResultTask(Manifest.permission.ACCESS_FINE_LOCATION, getActivity()) {
                    @Override
                    public void onGranted() {
                        initLocation();//获取权限定位
                    }
                })
                .request();
        pop = new PopWinMenu(getActivity());//初始化popwinMenu
        mActionbar.setData(new TranslucentActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                PermissionReq.with(getActivity())
                        .permissions(Manifest.permission.ACCESS_COARSE_LOCATION)
                        .permissions(Manifest.permission.ACCESS_FINE_LOCATION)
                        .result(new PermissionResultTask(Manifest.permission.ACCESS_FINE_LOCATION, getActivity()) {
                            @Override
                            public void onGranted() {
                                initLocation();//获取权限定位
                                UtilIntent.intentFadeActivity(getActivity(), SearchLibActivity.class);//获取权限定位,跳转到搜索页
                            }
                        })
                        .request();

            }


            @Override
            public void onPopClick(View view) {
                pop.showPopupWindow(view);
            }

            @Override
            public void onSearchClick() {
                UtilIntent.intentDIYLeftToRight(getActivity(),
                        SearchResourcesActivity.class,
                        android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        mScrollView.setTranslucentChangedListener(new TranslucentScrollView.TranslucentChangedListener() {
            @Override
            public void onTranslucentChanged(int transAlpha) {
                mActionbar.setNeedTranslucent(transAlpha);
            }

        });

    }
    /**
     * 中间四张图片的点击事件
     *
     * @param imgBeans
     */
    public void loadFourClassify(List<ApiOneBean.MaxImageBean> imgBeans) {
        for (int i = 0; i < imgBeans.size(); i++) {
            switch (imgBeans.get(i).getType()) {
                case "最爱听":
                    UtilGlide.loadRoundImage(getActivity(), imgBeans.get(i).getImg(), fa_home_img_zuiaiting);
                    fa_home_text_zuiaiting.setText("" + imgBeans.get(i).getType());
                    break;
                case "有好书":
                    UtilGlide.loadRoundImage(getActivity(), imgBeans.get(i).getImg(), fa_home_img_youhaoshu);
                    fa_home_text_youhaoshu.setText("" + imgBeans.get(i).getType());
                    break;
                case "超实惠":
                    UtilGlide.loadRoundImage(getActivity(), imgBeans.get(i).getImg(), fa_home_img_chaoshihui);
                    fa_home_text_chaoshihui.setText("" + imgBeans.get(i).getType());
                    break;
                case "都在看":
                    UtilGlide.loadRoundImage(getActivity(), imgBeans.get(i).getImg(), fa_home_img_douzaikan);
                    fa_home_text_douzaikan.setText("" + imgBeans.get(i).getType());
                    break;
            }
        }

    }

    /**
     * 公告滚动栏
     */
    private void setUPmarqueeView() {
        final List<String> strList = new ArrayList<>();
        strList.add("读联体上线啦,全部资源免费阅读啦");
        strList.add("豆蔻年华");
        strList.add("免费专区");
        mUPMarqueeView.addData(strList, new UPMarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                UtilToast.showText(strList.get(position));
            }
        });//广告垂直滚动
    }


    private GvZXSXAdapter gvBaobaodapter;
    private GvZXSXAdapter gvShaoNianAdapter;
    private GvZXSXAdapter gvDoukouAdapter;

    private LsvERBAdapter lsvMeiTianAdapter;
    private LsvMainFeiAdapter lsvMianFeiAdapter;
    /**
     * adapter使用的集合
     */
    private List<ApiOneBean.BdqdBean> listShaoNian;
    private List<ApiOneBean.BdqdBean> listBaobao;
    private List<ApiOneBean.BdqdBean> listDouKou;
    private List<ApiOneBean.EveryDayBean> listMeitian;
    private List<ApiOneBean.FreeBean> listMianFei;

    /**
     * 初始化Adapter
     */
    private void intAdapter() {
        listShaoNian = new ArrayList();
        listBaobao = new ArrayList();
        listDouKou = new ArrayList();
        listMeitian = new ArrayList();
        listMianFei = new ArrayList();

        gvDoukouAdapter = new GvZXSXAdapter(getActivity(), listDouKou);//豆蔻年华
        gvShaoNianAdapter = new GvZXSXAdapter(getActivity(), listShaoNian);//少年博览
        gvBaobaodapter = new GvZXSXAdapter(getActivity(), listBaobao);//宝宝静听
        lsvMeiTianAdapter = new LsvERBAdapter(getActivity(), null);//每天读本书
        lsvMianFeiAdapter = new LsvMainFeiAdapter(getActivity(), listMianFei);//免费专区

        mGvBaobao.setAdapter(gvBaobaodapter);
        mGvShaoNian.setAdapter(gvShaoNianAdapter);
        mGvDoukou.setAdapter(gvDoukouAdapter);

        mLsvMeiTian.setAdapter(lsvMeiTianAdapter);
        mLsvMianFei.setAdapter(lsvMianFeiAdapter);

        mGvBaobao.setOnItemClickListener(new AdapterViewOnitmClickListener(listBaobao));
        mGvShaoNian.setOnItemClickListener(new AdapterViewOnitmClickListener(listShaoNian));
        mGvDoukou.setOnItemClickListener(new AdapterViewOnitmClickListener(listDouKou));
        mLsvMianFei.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listMianFei.size() > 0)
                    toDetailActivity(listMianFei.get(position).getResource_id());
            }
        });
    }


    /**
     * 赋值
     *
     * @param bean
     */
    private void initData(final ApiOneBean bean) {
        bannerHelp.addData(bean.getBanner());
        bannerHelp.start();
        listMeitian.clear();
        listMeitian.remove(listMeitian);
        listMeitian.add(bean.getEveryDay());
        lsvMeiTianAdapter.addList(listMeitian);//每天读本书
        lsvMianFeiAdapter.addList(bean.getFree());//免费专区

        gvBaobaodapter.addList(bean.getBbjd());//宝宝静听
        gvShaoNianAdapter.addList(bean.getSnbl());//少年博览
        gvDoukouAdapter.addList(bean.getDknh());//豆蔻年华
        loadFourClassify(bean.getMaxImage());
    }


    @OnClick({
            R.id.fa_home_tag_baobao_jinting,
            R.id.fa_home_tag_shaonian_bolan,
            R.id.fa_home_tag_doukou_nianhua,
            R.id.fa_ll_zui_ai_ting,
            R.id.fa_ll_you_hao_shu,
            R.id.fa_ll_chao_shi_hui,
            R.id.fa_ll_dou_zai_kan,
            R.id.classification_zhuang_ti,
            R.id.classification_du_hui_ben,
            R.id.classification_jiang_gu_shi,
            R.id.classification_fen_lei,
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.classification_zhuang_ti:
                UtilToast.showText("暂未开放此功能");
                break;
            case R.id.classification_jiang_gu_shi:
                UtilToast.showText("暂未开放此功能");
                break;
            case R.id.classification_fen_lei:
                ((MainActivity) getActivity()).goToFragment(1);
                break;
            case R.id.classification_du_hui_ben:
                launch(WebHomeActivity.class, 0);
                break;
            case R.id.fa_ll_zui_ai_ting:
                launch(WebHomeActivity.class, 1);
                break;
            case R.id.fa_ll_you_hao_shu:
                launch(WebHomeActivity.class, 2);
                break;
            case R.id.fa_ll_chao_shi_hui:
                launch(WebHomeActivity.class, 3);
                break;
            case R.id.fa_ll_dou_zai_kan:
                launch(WebHomeActivity.class, 4);
                break;
            case R.id.fa_home_tag_baobao_jinting:
                launch(WebHomeActivity.class, 5);
                break;
            case R.id.fa_home_tag_shaonian_bolan:
                launch(WebHomeActivity.class, 6);
                break;
            case R.id.fa_home_tag_doukou_nianhua:
                launch(WebHomeActivity.class, 7);
                break;

        }
    }

    public void tagIntent(String keyWord, Class<?> classes) {
        launch(classes, keyWord);
    }


    public class AdapterViewOnitmClickListener implements AdapterView.OnItemClickListener {
        List<ApiOneBean.BdqdBean> list;

        public AdapterViewOnitmClickListener(List<ApiOneBean.BdqdBean> bdqdBeanList) {
            this.list = bdqdBeanList;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (list.size() > 0) toDetailActivity(list.get(position).getResource_id());
        }
    }

    private void toDetailActivity(String id) {
        launch(ResourceDetailActivity.class, id);
    }

    /**
     * 网络请求
     */
    private void netWork() {
        HttpAdapter.getService().getHome1().enqueue(new OnResponseListener<BaseResultModel<ApiOneBean>>(getActivity()) {
            @Override
            protected void onSuccess(BaseResultModel<ApiOneBean> resultModel) {
                refreshLayout.refreshComplete();
                ApiOneBean bean = resultModel.getData();
                initData(bean);
            }

            @Override
            protected void onRequestFailure() {
                refreshLayout.refreshComplete();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    /**
     * 是否定位成功
     */
    private boolean isSuccLoc;

    /**
     * 初始化定位
     */
    private void initLocation() {
        if (isSuccLoc) return;
        mActionbar.setCity(getActivity().getString(R.string.cp_locating));
        mLocationClient = new AMapLocationClient(getActivity());
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        mLocationClient.stopLocation();
                        String city = aMapLocation.getCity();
                        String district = aMapLocation.getDistrict();
                        String location = StringUtils.extractLocation(city, district);
                        mActionbar.setCity(location);
                        isSuccLoc = true;
                    } else {
                        //定位失败
                        mActionbar.setCity(getActivity().getString(R.string.cp_located_failed));
                    }
                }
            }
        });
        mLocationClient.startLocation();
    }


}
