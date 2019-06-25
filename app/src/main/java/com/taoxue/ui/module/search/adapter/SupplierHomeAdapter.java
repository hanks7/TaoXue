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
import com.taoxue.utils.glide.UtilGlide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2017/6/5.
 */
public class SupplierHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private List<ResourceBean> list ;

    public SupplierHomeAdapter(Activity activity, List<ResourceBean> list) {
        this.activity = activity;
        if(list==null){
            this.list=new ArrayList<>();
        }else{
            this.list = list;
        }
    }

    public void addList(List<ResourceBean> listBean) {
        if (listBean == null) return;

        list.addAll(listBean);
        notifyDataSetChanged();
    }

    /**
     * 添加指定item和 viewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType!=0) {
            View v = LayoutInflater.from(activity).inflate(R.layout.item_fa_gv_title_search_home, parent, false);
            TitleViewHolder titleViewHolder = new TitleViewHolder(v);
            return titleViewHolder;

        } else {
            View v = LayoutInflater.from(activity).inflate(R.layout.item_fa_gv_search_home, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(v);
            return myViewHolder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) ==1) {
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            titleViewHolder.iv.setImageResource(R.mipmap.supplier_remeng_tuijian);
            titleViewHolder.iline.setVisibility(View.GONE);
            return;
        }
        if (getItemViewType(position) ==2) {
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            titleViewHolder.iv.setImageResource(R.mipmap.supplier_remeng_paihang);
            titleViewHolder.iline.setVisibility(View.VISIBLE);
            return;
        }
        if (getItemViewType(position) ==3) {
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            titleViewHolder.iv.setImageResource(R.mipmap.supplier_guess_you_like);
            titleViewHolder.iline.setVisibility(View.VISIBLE);
            return;
        }
        ResourceBean  bean =  list.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.iTvDescription.setText(bean.getDescription()+"");
        myViewHolder.iTvTitle.setText(bean.getResource_name() + "");
        UtilGlide.loadImgNomal(activity, bean.getResource_picture(), myViewHolder.iIv);
    }

    /**
     * 总共多少个item
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return (list.size());
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
                if (getItemViewType(position)!=0) {
                    return 3;
                }
                return 1;
            }
        });
    }


    @Override
    public int getItemViewType(int position) {
        int type;
        switch (list.get(position).getFlag()) {
            case 1:
                type=1;break;
            case 2:
                type=2;break;
            case 3:
                type=3;break;
            default:
                type=0;break;
        }
        return type;
    }

    /**
     * 普通布局的viewholder
     */
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iIv;
        TextView iTvTitle;
        TextView iTvDescription;
        public MyViewHolder(View itemView) {
            super(itemView);
            iIv = (ImageView) itemView.findViewById(R.id.item_fa_home_gv_new_3_iv);
            iTvTitle = (TextView) itemView.findViewById(R.id.item_fa_home_gv_new_3_tv_title);
            iTvDescription = (TextView) itemView.findViewById(R.id.item_fa_home_gv_new_3_tv_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ResourceBean  bean =  list.get(getAdapterPosition());
            ((BaseActivity)activity).launch(BookIntroductionActivity.class, bean.getResource_id());
        }
    }

    /**
     * 标题布局的viewholder
     */
    class TitleViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        View iline;

        public TitleViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.item_fa_home_gv_title);
            iline = itemView.findViewById(R.id.item_fa_home_gv_line);

        }
    }


}
