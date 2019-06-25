package com.taoxue.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.taoxue.R;
import com.taoxue.ui.model.homefrag.ApiOneBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanks7 on 2017/4/5.
 * <p>
 * 热门好书
 * Popularbooks
 */
public class LsvRmhsAdapter extends BaseAdapter {
    private Context mContext;

    public LsvRmhsAdapter(Context mContext, List<ApiOneBean.BdqdBean> list) {
        this.mContext = mContext;
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList();
        }
    }

    public void addList(List<ApiOneBean.BdqdBean> listBean) {
        if (listBean == null) return;
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
        LsvRmhsAdapterViewHold holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fa_home_lsv_history_read, null);
            holder = new LsvRmhsAdapterViewHold(convertView);
            convertView.setTag(holder);
        } else {
            holder = (LsvRmhsAdapterViewHold) convertView.getTag();
        }
        holder.setView(mContext,(ApiOneBean.BdqdBean) list.get(position));
        return convertView;
    }

}
