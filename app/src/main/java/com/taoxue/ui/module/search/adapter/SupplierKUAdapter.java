package com.taoxue.ui.module.search.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.ui.model.GysDrdataBean;
import com.taoxue.ui.module.classification.resourceLib.ResourceLibraryActivity;
import com.taoxue.ui.view.FootHolder;
import com.taoxue.utils.glide.UtilGlide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 2017/6/5.
 */

public class SupplierKUAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<GysDrdataBean.PageBean.ResultBean> datas;
    private Activity mContext;
    private int normalType = 0;
    private int footType = 1;
    public boolean hasMore = true;

    public SupplierKUAdapter(Activity context, List<GysDrdataBean.PageBean.ResultBean> datas, boolean hasMore) {
        this.datas = datas;
        this.mContext = context;
        this.hasMore = hasMore;
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * 刷新数据
     *
     * @param newDatas
     * @param hasMore
     */
    public void updateList(List<GysDrdataBean.PageBean.ResultBean> newDatas, boolean hasMore) {
        this.hasMore = hasMore;
        datas.removeAll(datas);
        datas.clear();
        datas.addAll(newDatas);
        datas.add(new GysDrdataBean.PageBean.ResultBean());
        notifyDataSetChanged();
    }

    /**
     * 添加更多
     *
     * @param addlist
     * @param hasMore
     */
    public void addAllData(List<GysDrdataBean.PageBean.ResultBean> addlist, boolean hasMore) {
        this.hasMore = hasMore;
        datas.remove(getItemCount() - 1);
        datas.addAll(addlist);
        datas.add(new GysDrdataBean.PageBean.ResultBean());
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == normalType) {
            return new NormalHolder(LayoutInflater.from(mContext).inflate(R.layout.ui_frag_search_result, null));
        } else {
            return new FootHolder(LayoutInflater.from(mContext).inflate(R.layout.footview, null));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (datas.size() == 0) return;
        GysDrdataBean.PageBean.ResultBean bean = datas.get(position);
        if (holder instanceof NormalHolder) {
            intData((NormalHolder) holder, bean);
        } else {
            ((FootHolder) holder).hideView(hasMore);
        }
    }




    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return footType;
        } else {
            return normalType;
        }
    }

    /**
     * 赋值
     * @param holder
     * @param bean
     */
    private void intData(NormalHolder holder, GysDrdataBean.PageBean.ResultBean bean) {
        UtilGlide.loadRoundImage(mContext, bean.getLogo(), holder.iIvPic);
        holder.iTvName.setText(bean.getName() );
        holder.iTvSupplierName.setText(bean.getDescription());
    }

    /**
     * 正常布局的ViewHolder
     */
    class NormalHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.frg_search_result_iv_pic)
        ImageView iIvPic;
        @BindView(R.id.frg_search_result_tv_name)
        TextView iTvName;
        @BindView(R.id.frg_search_result_tv_supplier_name)
        TextView iTvSupplierName;

        NormalHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            GysDrdataBean.PageBean.ResultBean bean = datas.get(getAdapterPosition());
            ((BaseActivity)mContext).launch(ResourceLibraryActivity.class,bean.getId());
        }
    }

}
