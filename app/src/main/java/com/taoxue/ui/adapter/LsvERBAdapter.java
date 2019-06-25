package com.taoxue.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.ui.model.homefrag.ApiOneBean;
import com.taoxue.ui.module.yuejia.ResourceDetailActivity;
import com.taoxue.utils.glide.UtilGlide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanks7 on 2017/4/5.
 * <p>
 * 每天读本书  首页HomeFragment
 * Popularbooks
 */
public class LsvERBAdapter extends BaseAdapter {
    private Context mContext;
    private List list;
    public LsvERBAdapter(Context mContext, List<ApiOneBean.EveryDayBean> list) {
        this.mContext = mContext;
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList();
        }
    }


    public void addList(List<ApiOneBean.EveryDayBean> listBean){
        if (listBean == null) return;
        list.clear();
        list.remove(list);
        list.addAll(listBean);
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ApiOneBean.EveryDayBean bean = (ApiOneBean.EveryDayBean) list.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_layout_everyday_read_book, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        UtilGlide.loadRoundImage(mContext, bean.getResource_picture(),holder.vhIv);
        holder.vhTvTitle.setText(bean.getResource_name());
        holder.vhTvContent.setText(bean.getDescription());
        holder.vhBtnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity)mContext).launch(ResourceDetailActivity.class, bean.getResource_id());
            }
        });

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.lsv_erb_iv)
        ImageView vhIv;
        @BindView(R.id.lsv_erb_tv_title)
        TextView vhTvTitle;
        @BindView(R.id.lsv_erb_tv_content)
        TextView vhTvContent;
        @BindView(R.id.lsv_erb_btn_read)
        View vhBtnRead;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
