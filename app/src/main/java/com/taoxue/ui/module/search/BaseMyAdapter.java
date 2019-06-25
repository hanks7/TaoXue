package com.taoxue.ui.module.search;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 资源搜索结果adapter
 */
public class BaseMyAdapter<Data> extends BaseAdapter {
    public Context mContext;

    public BaseMyAdapter(Context mContext, List<Data> list) {
        this.mContext = mContext;
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList();
        }
    }

    public void addAllData(List<Data> listBean) {
        if (listBean == null) return;
        list.addAll(listBean);
        notifyDataSetChanged();
    }

    public void updateList(List<Data> listBean) {
        if (listBean == null) return;
        list.clear();
        list.remove(list);
        list.addAll(listBean);
        notifyDataSetChanged();
    }

    public  List list;

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
        return null;
    }

}
