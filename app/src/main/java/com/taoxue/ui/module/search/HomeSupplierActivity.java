package com.taoxue.ui.module.search;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.base.BaseFragment;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.GysHomeInfoBean;
import com.taoxue.ui.module.home.ZXingActivity;
import com.taoxue.ui.module.search.fragment.SupplierAllResourcesFragment;
import com.taoxue.ui.module.search.fragment.SupplierHomeFragment;
import com.taoxue.ui.module.search.fragment.SupplierKUFragment;
import com.taoxue.ui.view.CircleImageView;
import com.taoxue.ui.view.LibPopWinMenu;
import com.taoxue.ui.view.PopWinSupplierMenu;
import com.taoxue.utils.UtilIntent;
import com.taoxue.utils.glide.UtilGlide;
import com.taoxue.utils.permission.PermissionReq;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.taoxue.R.id.viewpager;

/**
 * 供应商首页
 */
public class HomeSupplierActivity extends BaseActivity {

    @BindView(R.id.et_search)
    EditText mEdtSearch;//搜索
    @BindView(R.id.fa_home_edt_search_selector)
    TextView edt_search_selector;//下拉选择
    @BindView(R.id.ac_sr_home_tv_name)
    TextView mTvName;//下拉选择
    @BindView(R.id.ac_sr_home_tv_description)
    TextView mTvDescription;//下拉选择
    @BindView(R.id.fa_home_iv_zxing)
    ImageView mIvZxing;//跳转二维码
    @BindView(R.id.fa_home_iv_more)
    ImageView mIvMore;//点击更多
    @BindView(viewpager)
    ViewPager mViewPager;
    @BindView(R.id.ac_my_iv_head)
    CircleImageView mIvHead;//头像
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.tablayout)
    SlidingTabLayout mTablayout;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    public static final String[] mTitles = {"首页", "资源库", "全部资源"};
    private ArrayList<Fragment> mFragments;
    private String gys_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_app_bar);
        ButterKnife.bind(this);
        gys_id = getIntent().getExtras().getString("ids");
        netWork();
        new PopWinSupplierMenu(this, edt_search_selector,gys_id);//初始化popwinMenu
        new LibPopWinMenu(this, mIvMore);//初始化popwinMenu
        initFragment();
        initView();

    }

    private void initView() {
        mEdtSearch.setFocusable(false);
        edt_search_selector.setText("资源");
        mEdtSearch.setHint("请输入资源名称");
    }

    /**
     * 网络请求
     */
    private void netWork() {
        HttpAdapter.getService().getGysHomeInfo(gys_id)
                .enqueue(new OnResponseListener<BaseResultModel<GysHomeInfoBean>>(this) {
                    @Override
                    protected void onSuccess(BaseResultModel<GysHomeInfoBean> bean) {
                        GysHomeInfoBean.GysInfoBean gysInfoBean = bean.getData().getGys_info();
                        mTvName.setText(gysInfoBean.getName() + "");

                        Drawable drawable= getResources().getDrawable(R.mipmap.lib_home_vi_gong);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        mTvName.setCompoundDrawablePadding(11);//设置图片和text之间的间距
                        mTvName.setCompoundDrawables(drawable,null,null,null);

                        mTvDescription.setText(gysInfoBean.getDescription());
                        UtilGlide.loadImgForIvHead(mActivity, gysInfoBean.getLogo(), mIvHead);
                    }
                });
    }

    private void initFragment() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }
        setSupportActionBar(mToolBar);
        BaseFragment fragment1 = new SupplierHomeFragment();
        BaseFragment fragment2 = new SupplierKUFragment();
        BaseFragment fragment3 = new SupplierAllResourcesFragment();
        setFragmentArgs(fragment1);
        setFragmentArgs(fragment2);
        setFragmentArgs(fragment3);
        mFragments = new ArrayList<>();
        mFragments.add(fragment1);
        mFragments.add(fragment2);
        mFragments.add(fragment3);

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mFragments.get(arg0);
            }
        };
        mViewPager.setOffscreenPageLimit(3);// 防止ViewPager中的Fragment被销毁
        mViewPager.setAdapter(myPagerAdapter);
        mViewPager.setCurrentItem(0);
        mTablayout.setViewPager(mViewPager, mTitles, this, mFragments);

    }

    /**
     * 向fragment传入参数;
     *
     * @param fragment1
     */
    private void setFragmentArgs(BaseFragment fragment1) {
        Bundle bundle = new Bundle();
        bundle.putString("ids", gys_id);
        fragment1.setArguments(bundle);
    }

    @OnClick({R.id.fa_home_iv_head,
            R.id.et_search,
            R.id.fa_home_iv_zxing,
            R.id.fa_home_iv_more,
            R.id.ac_my_iv_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fa_home_iv_head:
                onBackPressed();
                break;
            case R.id.et_search:
                UtilIntent.intentDIYLeftToRight(this,
                        SearchResourcesActivity.class,
                        android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.fa_home_iv_zxing:
                if (!PermissionReq.judgePermisson(this, Manifest.permission.CAMERA))
                    break;
                Intent intent = new Intent(this, ZXingActivity.class);
                this.startActivityForResult(intent, 103);
                break;
            case R.id.ac_my_iv_head:
                break;
        }
    }

    /**
     * 自定义fragment适配器
     */
    class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        /**
         * 覆盖destroyItem方法可阻止销毁Fragment
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }


    /**
     * 扫尾二维码回调放回
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        showToast(resultCode + "");

    }


}
