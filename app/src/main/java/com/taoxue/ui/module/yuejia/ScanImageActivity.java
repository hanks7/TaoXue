package com.taoxue.ui.module.yuejia;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.AudioResourceBean;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.FlagCodeBean;
import com.taoxue.ui.model.ReadInfoBean;
import com.taoxue.ui.model.VolumnBean;
import com.taoxue.ui.module.classification.CommentActivity;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.Utilshare;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScanImageActivity extends BaseActivity {

    @BindView(R.id.introduction_topbar)
    TopBar introductionTopbar;
    @BindView(R.id.vp_photo)
    ViewPager vpPhoto;
    @BindView(R.id.rv_photo_horizontal)
    RecyclerView rvHorizontal;
    Context context = this;
    @BindView(R.id.photo_pinglun)
    LinearLayout photoPinglun;
    @BindView(R.id.photo_dianzan)
    LinearLayout photoDianzan;
    @BindView(R.id.photo_shoucang)
    LinearLayout photoShoucang;
    @BindView(R.id.photo_fenxiang)
    LinearLayout photoFenxiang;
    @BindView(R.id.iv_dianzan)
    ImageView ivDianzan;
    @BindView(R.id.iv_shoucang)
    ImageView ivShoucang;
    @BindView(R.id.text_dianzan)
    TextView textDianzan;
    @BindView(R.id.text_collection)
    TextView textCollection;


    private List<Fragment> fragmentList;
    String resource_id;
    String cgs_id;
    public static int imageTranstion = 255;//表明imageview透明
    public static int banTouming = 76;//表明imageview部分透明
    ImageAdapter adapter1;
    private boolean isItemClick = false;
    String gys_id;

    ReadInfoBean infoBean;
    Utilshare utilshare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_image);
        ButterKnife.bind(this);

        infoBean = (ReadInfoBean) getIntentKey1();
        utilshare = new Utilshare(this);
//        resource_id="402883d26033f0020160340897d80013";
        onHttp();
    }

    public void onHttp() {
        resource_id=infoBean.getResource_id();
        gys_id=infoBean.getGys_id();
        if (infoBean.getItem().get(0).getVolumn().size() > 0) {
            onInitView(infoBean.getItem().get(0).getVolumn());
            setCollect(Integer.parseInt(infoBean.getIs_collection()));
            setDianZan(Integer.parseInt(infoBean.getIs_praise()));
        }

    }

    int preImageIndex = 0;

    public void onInitView(List<VolumnBean> beans) {

        fragmentList = new ArrayList<>();
        for (int i = 0; i < beans.size(); i++) {
            fragmentList.add(ImageFragment.newInstance(beans.get(i).getUrl()));
        }
        ImageFragmentStatePagerAdapter adapter = new ImageFragmentStatePagerAdapter(getSupportFragmentManager(), fragmentList);
        vpPhoto.setAdapter(adapter);
        vpPhoto.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (!isItemClick){
                ((LinearLayoutManager) rvHorizontal.getLayoutManager()).scrollToPositionWithOffset(position, 0);
                }else {
                    isItemClick=false;
                }
                adapter1.setCurrentIndex(position);
                if (position != preImageIndex) {
                    adapter1.getImageItemView(position).setImageAlpha(imageTranstion);
                    adapter1.getImageItemView(preImageIndex).setImageAlpha(banTouming);
                    preImageIndex = position;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//
//         int position= vpPhoto.getCurrentItem();

            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvHorizontal.setLayoutManager(linearLayoutManager);
        adapter1 = new ImageAdapter(this, beans);
        rvHorizontal.setAdapter(adapter1);
        adapter1.setOnItemAdapterClickListener(new OnImageItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                isItemClick=true;
                vpPhoto.setCurrentItem(position);
            }
        });

    }

    /**
     * 0:取消点赞
     *
     * @param value
     */
    public void setDianZan(int value) {
        switch (value) {
            case 0:
                ivDianzan.setImageResource(R.mipmap.photodianzan);
                textDianzan.setTextColor(Color.WHITE);
                break;
            case 1:
                ivDianzan.setImageResource(R.mipmap.dianzan_selected);
                textDianzan.setTextColor(Color.parseColor("#05a2e9"));
                break;
        }
    }

    /**
     * 0:取消收藏
     *
     * @param value
     */
    public void setCollect(int value) {
        switch (value) {
            case 0:
                ivShoucang.setImageResource(R.mipmap.photoshoucang);
                textCollection.setTextColor(Color.WHITE);
                break;
            case 1:
                ivShoucang.setImageResource(R.mipmap.collection_seclected);
                textCollection.setTextColor(Color.parseColor("#05a2e9"));
                break;
        }
    }


    @OnClick({R.id.photo_pinglun, R.id.photo_dianzan, R.id.photo_shoucang, R.id.photo_fenxiang})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.photo_pinglun:
                if (infoBean != null) {
                    AudioResourceBean audioResourceBean = new AudioResourceBean();
                    audioResourceBean.setGys_id(gys_id);
                    audioResourceBean.setResource_id(resource_id);
                    launch(CommentActivity.class, audioResourceBean);
                } else {
                    showToast("未获取到数据");
                }

                break;
            case R.id.photo_dianzan:
                HttpAdapter.getService().saveOrDelResourcePraise(resource_id, gys_id).enqueue(new OnResponseNoDialogListener<BaseResultModel<FlagCodeBean>>() {
                    @Override
                    protected void onSuccess(BaseResultModel<FlagCodeBean> model) {
                        int value = Integer.valueOf(model.getData().getFlag_code());
                        setDianZan(value);
                    }
                });
                break;
            case R.id.photo_shoucang:
                HttpAdapter.getService().saveOrDelUserCollection(resource_id, gys_id).enqueue(new OnResponseNoDialogListener<BaseResultModel<FlagCodeBean>>() {
                    @Override
                    protected void onSuccess(BaseResultModel<FlagCodeBean> model) {
                        int value = Integer.valueOf(model.getData().getFlag_code());
                        setCollect(value);
                    }
                });
                break;
            case R.id.photo_fenxiang:
                utilshare.show();
                break;
        }
    }
}
