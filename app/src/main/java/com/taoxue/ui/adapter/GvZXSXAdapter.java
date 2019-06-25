package com.taoxue.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.ui.model.homefrag.ApiOneBean;
import com.taoxue.utils.glide.UtilGlide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanks7 on 2017/4/5.
 * 最新上线
 */
public class GvZXSXAdapter extends BaseAdapter {
    private Context mContext;

    public GvZXSXAdapter(Context mContext, List<ApiOneBean.BdqdBean> list) {
        this.mContext = mContext;
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList();
        }

    }
    public void addList(List<ApiOneBean.BdqdBean> listBean){
        if(listBean==null) return;
        list.clear();
        list.remove(list);
        list.addAll(listBean);
        notifyDataSetChanged();
    }
    private List list;

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
        ApiOneBean.BdqdBean  bean = (ApiOneBean.BdqdBean) list.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fa_home_gv_new_release_three, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.iTvTitle.setText(bean.getResource_name()+"");
        holder.iTvContent.setText("阅读数 "+bean.getRead_num());
        UtilGlide.loadRoundImage(mContext,bean.getResource_picture(),holder.iIv,5);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.item_fa_home_gv_new_3_iv)
        ImageView iIv;
        @BindView(R.id.item_fa_home_gv_new_3_tv_title)
        TextView iTvTitle;
        @BindView(R.id.item_fa_home_gv_new_3_tv_content)
        TextView iTvContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
