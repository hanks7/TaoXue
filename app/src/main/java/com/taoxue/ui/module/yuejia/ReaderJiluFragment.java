package com.taoxue.ui.module.yuejia;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.common.base.basecommon.BaseAdapter.Base.RvViewHolder;
import com.common.base.basecommon.BaseAdapter.Decoration.DefaltDividerItemDecoration;
import com.common.base.basecommon.BaseAdapter.RvComAdapter;
import com.common.base.basecommon.BaseAdapter.listener.InitViewCallBack;
import com.common.base.basecommon.BaseAdapter.listener.OnItemAdapterClickListener;
import com.taoxue.R;
import com.taoxue.base.BaseFragment;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.BaseListModel;
import com.taoxue.ui.model.ReadJiLuBean;
import com.taoxue.ui.model.RecordBean;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.glide.UtilGlide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.common.base.basecommon.BaseAdapter.RvComAdapter.GRIDLAYOUTMANAGER;

/**
 * 阅读记录
 * Created by User on 2018/2/1.
 */

public class ReaderJiluFragment extends BaseFragment {
    @BindView(R.id.divider)
    View divider;
    @BindView(R.id.rv_jilu)
    RecyclerView rvJilu;

    Unbinder unbinder;
    @BindView(R.id.iv_default_bg)
    ImageView ivDefaultBg;

    @Override
    protected int getLayout() {
        return R.layout.fragment_read_lu;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtils.D("更新数据 hidden----》" + hidden);
    }

    @Override
    protected void onInit() {
        ivDefaultBg.setVisibility(View.VISIBLE);
        rvJilu.setVisibility(View.GONE);
        HttpAdapter.getService().getReaderJiLus().enqueue(new OnResponseNoDialogListener<BaseListModel<ReadJiLuBean>>() {
            @Override
            protected void onSuccess(BaseListModel<ReadJiLuBean> readJiLuBeanBaseListModel) {
                if (readJiLuBeanBaseListModel.getItem()!=null&&readJiLuBeanBaseListModel.getItem().size() > 0) {
                    ivDefaultBg.setVisibility(View.GONE);
                    rvJilu.setVisibility(View.VISIBLE);
                    setAdapter(readJiLuBeanBaseListModel.getItem());
                }
            }
        });

    }


    public void setAdapter(List<ReadJiLuBean> beans) {
        RvComAdapter rvCommonAdapter = new RvComAdapter.Builder<ReadJiLuBean>(getActivity(), R.layout.jilu_item, beans)
                .setDefaltDecoration(DefaltDividerItemDecoration.HORIZONTAL, R.drawable.jilu_shape_divider)
                .into(rvJilu, new InitViewCallBack<ReadJiLuBean>() {
                    @Override
                    public void convert(RvViewHolder holder, ReadJiLuBean readJiLuBean, int i) {
                        holder.setText(R.id.tv_read_date, readJiLuBean.getDate());
                        RecyclerView recyclerView = holder.getView(R.id.rv_read_info);
                        recyclerView.setNestedScrollingEnabled(false);
                        if (readJiLuBean.getResource_record().size() > 0) {
                            RvComAdapter rv1 = new RvComAdapter.Builder<RecordBean>(getActivity(), R.layout.jilu_item_rv_item, readJiLuBean.getResource_record())
                                    .setLayoutManagerType(GRIDLAYOUTMANAGER, 3)
                                    .setOnItemAdapterClickListener(new OnItemAdapterClickListener() {
                                        @Override
                                        public void onItemClick(View view, RvViewHolder rvViewHolder, int i, int i1) {
                                            launch(ResourceDetailActivity.class, (String) view.getTag());
                                        }
                                    })
                                    .into(recyclerView, new InitViewCallBack<RecordBean>() {
                                        @Override
                                        public void convert(RvViewHolder holder, RecordBean recordBean, int position) {
                                            holder.setText(R.id.tv_info_yuedu_num, "阅读量:" + recordBean.getRead_num());

                                            holder.setText(R.id.tv_info_title, recordBean.getResource_name());
                                            UtilGlide.loadRoundImage(getActivity(), recordBean.getResource_picture(), (ImageView) holder.getView(R.id.iv_jilu_info), 5);
                                            holder.getConvertView().setTag(recordBean.getResource_id());

                                        }
                                    });
                        } else {
                            recyclerView.setVisibility(View.GONE);
                            holder.getView(R.id.tv_no_read).setVisibility(View.VISIBLE);
                        }
                    }
                });
//           rvJilu.setNestedScrollingEnabled(false);

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

    @OnClick(R.id.iv_default_bg)
    public void onClick() {
    }
}
