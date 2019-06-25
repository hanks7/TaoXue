package com.taoxue.ui.module.search.adapter;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.ui.model.ResourceBean;
import com.taoxue.ui.module.classification.BookIntroductionActivity;
import com.taoxue.ui.view.FootHolder;
import com.taoxue.utils.glide.UtilGlide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2017/6/5.
 */

public class SupplierAllResourcesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ResourceBean> datas;
    private Activity mContext;
    private int normalType = 0;
    private int footType = 1;
    public boolean hasMore = true;

    public SupplierAllResourcesAdapter(Activity context, List<ResourceBean> datas, boolean hasMore) {
        if(datas==null){
            this.datas=new ArrayList<>();
        }else{
            this.datas = datas;
        }
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
    public void updateList(List<ResourceBean> newDatas, boolean hasMore) {
        this.hasMore = hasMore;
        datas.removeAll(datas);
        datas.clear();
        datas.addAll(newDatas);
        datas.add(new ResourceBean());
        notifyDataSetChanged();
    }

    /**
     * 添加更多
     *
     * @param addlist
     * @param hasMore
     */
    public void addAllData(List<ResourceBean> addlist, boolean hasMore) {
        this.hasMore = hasMore;
        datas.remove(getItemCount() - 1);
        datas.addAll(addlist);
        datas.add(new ResourceBean());
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == normalType) {
            return new NormalHolder(LayoutInflater.from(mContext).inflate(R.layout.item_fa_gv_search_home, null));
        } else {
            return new FootHolder(LayoutInflater.from(mContext).inflate(R.layout.footview, null));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (datas.size() == 0) return;
        ResourceBean bean = datas.get(position);
        if (holder instanceof NormalHolder) {
            intData((NormalHolder) holder, bean);
        } else {
            ((com.taoxue.ui.view.FootHolder) holder).hideView(hasMore);
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
     * 每行设置几个单元格
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        //如果是title就占据3个单元格(重点)
        GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (getItemViewType(position)==footType) {
                    return 3;
                }
                return 1;
            }
        });
    }

    /**
     * 赋值
     * @param holder
     * @param bean
     */
    private void intData(NormalHolder holder, ResourceBean bean) {
        holder.iTvDescription.setText(bean.getDescription()+"");
        holder.iTvTitle.setText(bean.getResource_name() + "");
        UtilGlide.loadImgNomal(mContext, bean.getResource_picture(), holder.iIv);
    }

    /**
     * 普通布局的viewholder
     */
    class NormalHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iIv;
        TextView iTvTitle;
        TextView iTvDescription;
        public NormalHolder(View itemView) {
            super(itemView);
            iIv = (ImageView) itemView.findViewById(R.id.item_fa_home_gv_new_3_iv);
            iTvTitle = (TextView) itemView.findViewById(R.id.item_fa_home_gv_new_3_tv_title);
            iTvDescription = (TextView) itemView.findViewById(R.id.item_fa_home_gv_new_3_tv_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ResourceBean  bean =  datas.get(getAdapterPosition());
            ((BaseActivity)mContext).launch(BookIntroductionActivity.class, bean.getResource_id());
        }
    }


}
