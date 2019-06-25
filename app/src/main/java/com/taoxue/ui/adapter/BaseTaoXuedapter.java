package com.taoxue.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.taoxue.ui.model.ResourceBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanks7 on 2017/4/5.
 * <p>
 * 每天读本书  首页HomeFragment
 * Popularbooks
 */
public class BaseTaoXuedapter<T> extends BaseAdapter {
    private Context mContext;
    private List list;
    public BaseTaoXuedapter(Context mContext, List<T> list) {
        this.mContext = mContext;
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList();
        }
    }


    public void addList(List<T> listBean){
        if(listBean==null) return;
        list.clear();
        list.remove(list);
        list.addAll(listBean);
        notifyDataSetChanged();
    }

    /**
     * 刷新数据
     *
     * @param newDatas
     * @param hasMore
     */
    public void updateList(List<ResourceBean> newDatas, boolean hasMore) {
        list.removeAll(list);
        list.clear();
        list.addAll(newDatas);
        list.add(new ResourceBean());
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
        return null;
    }

}
