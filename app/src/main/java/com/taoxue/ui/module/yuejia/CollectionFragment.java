package com.taoxue.ui.module.yuejia;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.common.base.basecommon.BaseAdapter.Base.RvViewHolder;
import com.common.base.basecommon.BaseAdapter.RvComAdapter;
import com.common.base.basecommon.BaseAdapter.listener.InitViewCallBack;
import com.common.base.basecommon.BaseAdapter.listener.OnItemAdapterClickListener;
import com.taoxue.R;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.BasePageModel;
import com.taoxue.ui.model.BookInfoBean;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.glide.UtilGlide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by User on 2018/2/1.
 */

public class CollectionFragment extends BaseResourceFragment {
    @BindView(R.id.reader_down)
    ImageView readerDown;
    @BindView(R.id.rv_book_info)
    RecyclerView rvBookInfo;
    Unbinder unbinder;
    @BindView(R.id.iv_default_bg)
    ImageView ivDefaultBg;

    @Override
    protected int getLayout() {
        return R.layout.book_info_item;
    }

    public void setAdapter(final List<BookInfoBean> beans) {
        RvComAdapter rvComAdapter = new RvComAdapter.Builder<>(getActivity(), R.layout.book_info_item_rv_item, beans)
                .setLayoutManagerType(RvComAdapter.LINEARLAYOUTMANAGER)
                .setOnItemAdapterClickListener(new OnItemAdapterClickListener() {
                    @Override
                    public void onItemClick(View view, RvViewHolder rvViewHolder, int i, int i1) {
                        launch(ResourceDetailActivity.class, beans.get(i).getResource_id());
                    }
                })
                .into(rvBookInfo, new InitViewCallBack<BookInfoBean>() {
                    @Override
                    public void convert(RvViewHolder holder, BookInfoBean bookInfoBean, int i) {
                        holder.setText(R.id.tv_book_title, bookInfoBean.getResource_name());
                        holder.setText(R.id.tv_book_author, bookInfoBean.getAuthor());
                        holder.setText(R.id.tv_book_decription, bookInfoBean.getDescription());
                        holder.setText(R.id.tv_book_yuedu_num, "阅读量:" + bookInfoBean.getRead_num());
                        holder.setText(R.id.tv_book_collction_num, "收藏量:" + bookInfoBean.getCollection_num());
                        UtilGlide.loadRoundImage(getActivity(), bookInfoBean.getResource_picture(), (ImageView) holder.getView(R.id.iv_book_info_pic), 5);
                    }
                });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_recently_pay:
                updateData();
                hiddenDialog();
                break;
            case R.id.order_recently_word:
                updateData();
                hiddenDialog();
                break;
            case R.id.order_recently_read:
                updateData();
                hiddenDialog();
                break;
        }
    }


    @Override
    protected void onInit() {
        updateData();
        readerDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
    }

    public void updateData() {
        ivDefaultBg.setVisibility(View.VISIBLE);
        rvBookInfo.setVisibility(View.GONE);
        HttpAdapter.getService().getCollections().enqueue(new OnResponseNoDialogListener<BasePageModel<BookInfoBean>>() {
            @Override
            protected void onSuccess(BasePageModel<BookInfoBean> bookInfoBeanBasePageModel) {
                LogUtils.D("model---->" + bookInfoBeanBasePageModel.toString());
                if (bookInfoBeanBasePageModel.getPage()!=null&&bookInfoBeanBasePageModel.getPage().getResult()!=null&&bookInfoBeanBasePageModel.getPage().getResult().size() > 0) {
                    ivDefaultBg.setVisibility(View.GONE);
                    rvBookInfo.setVisibility(View.VISIBLE);
                    setAdapter(bookInfoBeanBasePageModel.getPage().getResult());
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
