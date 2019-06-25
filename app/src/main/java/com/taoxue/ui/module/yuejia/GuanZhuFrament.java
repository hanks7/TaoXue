package com.taoxue.ui.module.yuejia;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.base.basecommon.BaseAdapter.Base.RvViewHolder;
import com.common.base.basecommon.BaseAdapter.RvComAdapter;
import com.common.base.basecommon.BaseAdapter.listener.InitViewCallBack;
import com.common.base.basecommon.BaseAdapter.listener.OnItemAdapterClickListener;
import com.taoxue.R;
import com.taoxue.base.BaseFragment;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.BaseListModel;
import com.taoxue.ui.model.GuanzhuLibBean;
import com.taoxue.ui.model.GuanzhuResourceBean;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.glide.UtilGlide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.common.base.basecommon.BaseAdapter.RvComAdapter.GRIDLAYOUTMANAGER;

/**
 * Created by User on 2018/2/1.
 */

public class GuanZhuFrament extends BaseFragment {

    @BindView(R.id.tushuguan)
    TextView tushuguan;
    @BindView(R.id.ziyuanshang)
    TextView ziyuanshang;
    @BindView(R.id.dv)
    View dv;
    @BindView(R.id.ev)
    View ev;
    @BindView(R.id.rv_tushuguan)
    RecyclerView rvTushuguan;
    @BindView(R.id.rv_ziyuanshang)
    RecyclerView rvZiyuanshang;
    Unbinder unbinder;
    @BindView(R.id.iv_default_bg)
    ImageView ivDefaultBg;


    @Override
    protected int getLayout() {
        return R.layout.fragment_guanzhu;
    }

    public void setAdapter(final List<GuanzhuLibBean> beans, RecyclerView rv) {
        if (rv == null) {
            LogUtils.D("rv---->null");
            return;
        }
        RvComAdapter rvComAdapter = new RvComAdapter.Builder<>(getActivity(), R.layout.guanzhu_item, beans)
                .setLayoutManagerType(RvComAdapter.LINEARLAYOUTMANAGER)
                .into(rv, new InitViewCallBack<GuanzhuLibBean>() {
                    @Override
                    public void convert(RvViewHolder rvViewHolder, GuanzhuLibBean guanzhuLibBean, int i) {
                        rvViewHolder.setText(R.id.tv_guanzhu_title, guanzhuLibBean.getName());
                        UtilGlide.loadImg(getActivity(), guanzhuLibBean.getLogo(), (ImageView) rvViewHolder.getView(R.id.iv_guanzhu_head));
                        RecyclerView recyclerView = rvViewHolder.getView(R.id.rv_guanzhu_info);

                        if (guanzhuLibBean.getResource()!=null) {
                            RvComAdapter rv1 = new RvComAdapter.Builder<GuanzhuResourceBean>(getActivity(), R.layout.jilu_item_rv_item, guanzhuLibBean.getResource())
                                    .setLayoutManagerType(GRIDLAYOUTMANAGER, 3)
                                    .setEmptyLayoutId(R.layout.empty_layout)
                                    .setOnItemAdapterClickListener(new OnItemAdapterClickListener() {
                                        @Override
                                        public void onItemClick(View view, RvViewHolder rvViewHolder, int i, int i1) {
                                            launch(ResourceDetailActivity.class, (String) view.getTag());
                                        }
                                    })
                                    .into(recyclerView, new InitViewCallBack<GuanzhuResourceBean>() {
                                        @Override
                                        public void convert(RvViewHolder holder, GuanzhuResourceBean recordBean, int position) {
                                            holder.setText(R.id.tv_info_yuedu_num, "阅读量:" + recordBean.getRead_num());
                                            holder.setText(R.id.tv_info_title, recordBean.getResource_name());
                                            UtilGlide.loadRoundImage(getActivity(), recordBean.getResource_picture(), (ImageView) holder.getView(R.id.iv_jilu_info), 5);
                                            holder.getConvertView().setTag(recordBean.getResource_id());
                                        }
                                    });
                        }
                    }
                });
    }

    @Override
    protected void onInit() {
        tushuguan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivDefaultBg.setVisibility(View.GONE);
                tushuguan.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                dv.setVisibility(View.VISIBLE);
                ev.setVisibility(View.INVISIBLE);
                ziyuanshang.setTextColor(getActivity().getResources().getColor(R.color.appTextGray));
                rvTushuguan.setVisibility(View.VISIBLE);
                rvZiyuanshang.setVisibility(View.GONE);
            }
        });
        ziyuanshang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivDefaultBg.setVisibility(View.GONE);
                tushuguan.setTextColor(getActivity().getResources().getColor(R.color.appTextGray));
                dv.setVisibility(View.INVISIBLE);
                ev.setVisibility(View.VISIBLE);
                ziyuanshang.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                rvTushuguan.setVisibility(View.GONE);
                rvZiyuanshang.setVisibility(View.VISIBLE);
            }
        });


        HttpAdapter.getService().getGuanzhuLibs().enqueue(new OnResponseNoDialogListener<BaseListModel<GuanzhuLibBean>>() {
            @Override
            protected void onSuccess(BaseListModel<GuanzhuLibBean> guanzhuLibBeanBaseListModel) {
                if (guanzhuLibBeanBaseListModel.getItem() != null && guanzhuLibBeanBaseListModel.getItem().size() > 0) {
                    setAdapter(guanzhuLibBeanBaseListModel.getItem(), rvTushuguan);
                } else {
                    ivDefaultBg.setVisibility(View.VISIBLE);
                    dv.setVisibility(View.GONE);
                }
            }
        });


        HttpAdapter.getService().getGuanzhuSuppliers().enqueue(new OnResponseNoDialogListener<BaseListModel<GuanzhuLibBean>>() {
            @Override
            protected void onSuccess(BaseListModel<GuanzhuLibBean> guanzhuLibBeanBaseListModel) {
                LogUtils.D("MODEL--->" + guanzhuLibBeanBaseListModel.toString());
                if (guanzhuLibBeanBaseListModel.getItem() != null &&guanzhuLibBeanBaseListModel.getItem().size() > 0) {
                    setAdapter(guanzhuLibBeanBaseListModel.getItem(), rvZiyuanshang);
                }else{
                    ivDefaultBg.setVisibility(View.VISIBLE);
                    dv.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
