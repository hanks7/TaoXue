package com.taoxue.ui.module.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.base.BaseListFragment;
import com.taoxue.base.BaseRecyclerAdapter;
import com.taoxue.base.BaseViewHolder;
import com.taoxue.http.OnRefreshListResponseListener;
import com.taoxue.ui.model.QryMyCollectionBean;
import com.taoxue.ui.module.yuejia.ResourceDetailActivity;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.glide.UtilGlide;

import butterknife.BindView;

/**
 * Created by hopshine on 2017/1/5.PlayRecordFragment
 */

public class MyCollectionFragment extends BaseListFragment<MyCollectionFragment.ExampleHolder, QryMyCollectionBean> {

    @Override
    protected void onInitTopBar(TopBar topBar) {
        topBar.setTitle(R.string.my_collection);
    }

    @Override
    protected void loadData(int page, int pageSize) {
        getServer().collection2( page, pageSize)
                .enqueue(new OnRefreshListResponseListener<QryMyCollectionBean>(getActivity(),getRecyclerView(), getPageInfoModel()));
    }

    protected void bindAdapterItemView(MyCollectionFragment.ExampleHolder holder, final QryMyCollectionBean bean, int position) {
        holder.iTvBookName.setText(bean.getResource_name()+"");
        holder.iTvContent.setText(bean.getDescription()+"");
        holder.iTvFormatName.setText("阅读数："+bean.getVisit_num()+"    收藏数: "+bean.getCollection_num());
        holder.iTvTime.setVisibility(View.GONE);
        UtilGlide.loadImg(getActivity(),bean.getResource_picture(),holder.iIvPic);
    }


    @Override
    protected void onInit() {
        super.onInit();
        setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                launch(ResourceDetailActivity.class,getAdapter().getItem(position).getResource_id()+"");
            }
        });
    }


    @Override
    protected BaseRecyclerAdapter<ExampleHolder, QryMyCollectionBean> createAdapter() {
        return new DiaryListAdapter();
    }

    private class DiaryListAdapter extends BaseRecyclerAdapter<MyCollectionFragment.ExampleHolder, QryMyCollectionBean> {

        @Override
        protected int getItemLayout() {
            return R.layout.ui_frag_my_collection;
        }

        @Override
        protected void bindView(MyCollectionFragment.ExampleHolder holder, QryMyCollectionBean bean, int position) {
            bindAdapterItemView(holder, bean, position);
        }

        @Override
        protected MyCollectionFragment.ExampleHolder getHolder(View itemView) {
            return new MyCollectionFragment.ExampleHolder(itemView);
        }
    }



    public class ExampleHolder extends BaseViewHolder {

        @BindView(R.id.frag_collection_iv_pic)
        ImageView iIvPic;
        @BindView(R.id.frag_collection_tv_book_name)
        TextView iTvBookName;
        @BindView(R.id.frag_collection_tv_format_name)
        TextView iTvFormatName;
        @BindView(R.id.frag_collection_tv_time)
        TextView iTvTime;
        @BindView(R.id.frag_collection_tv_content)
        TextView iTvContent;

        public ExampleHolder(View itemView) {
            super(itemView);
        }
    }




}
