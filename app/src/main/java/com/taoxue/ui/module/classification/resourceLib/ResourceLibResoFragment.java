package com.taoxue.ui.module.classification.resourceLib;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.base.basecommon.BaseAdapter.Base.RvViewHolder;
import com.common.base.basecommon.BaseAdapter.RvComAdapter;
import com.common.base.basecommon.BaseAdapter.listener.InitViewCallBack;
import com.common.base.basecommon.BaseAdapter.listener.OnItemAdapterClickListener;
import com.common.base.basecommon.BaseAdapter.listener.OnLoadMoreListener;
import com.taoxue.R;
import com.taoxue.ui.model.LibResourceModel;
import com.taoxue.ui.model.LibResultModel;
import com.taoxue.ui.module.classification.BookIntroductionActivity;
import com.taoxue.ui.module.classification.vpFragment.BaseVpFragment;
import com.taoxue.utils.glide.UtilGlide;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by User on 2017/6/21.
 */

public class ResourceLibResoFragment extends BaseVpFragment {

    @BindView(R.id.commit_view)
    RecyclerView commitView;
    @BindView(R.id.no_content_tv)
    TextView noContentTv;
    @BindView(R.id.no_content_ll)
    RelativeLayout noContentLl;
    Unbinder unbinder;
    LibResourceModel model;
    @BindView(R.id.no_content_iv)
    ImageView noContentIv;

    @Override
    protected void getActiArguments(Serializable arguments) {
        model = (LibResourceModel) arguments;
    }

    @Override
    protected void onInit(View view) {
        if (null != model.getResult() && !("[]").equals(model.getResult() + "") && model.getResult().size() > 0) {
            setRecycleViewAdapter();
        } else {
            noContentLl.setVisibility(View.VISIBLE);
            noContentIv.setVisibility(View.GONE);
            noContentTv.setVisibility(View.VISIBLE);

            commitView.setVisibility(View.GONE);
        }
    }



    private void setRecycleViewAdapter() {
        RvComAdapter rvCommonAdapter = new RvComAdapter.Builder<>(getActivity(), R.layout.resource_lib_res_item, model.getResult())
                .setOnLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore(RvViewHolder rvViewHolder) {

                    }
                })
                .setOnItemAdapterClickListener(new OnItemAdapterClickListener() {
                    @Override
                    public void onItemClick(View view, RvViewHolder rvViewHolder, int i, int i1) {
                        launch(BookIntroductionActivity.class, model.getResult().get(i).getResource_id());
                    }
                })
                .into(commitView, new InitViewCallBack<LibResultModel>() {
                    @Override
                    public void convert(RvViewHolder holder, LibResultModel libResultModel, int i) {
                        UtilGlide.loadImg(getActivity(), libResultModel.getResource_picture(), (ImageView) holder.getView(R.id.lib_res_item_iv));
//                holder.setImageUrl(R.id.lib_res_item_iv,drResourceModel.getResource_picture());
                        holder.setText(R.id.lib_res_item_title_tv, libResultModel.getResource_name());
                        holder.setText(R.id.lib_res_item_content_tv, libResultModel.getDescription());


                    }
                });
    }


    @Override
    protected int getLayout() {
        return R.layout.vp_book_commit;
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
