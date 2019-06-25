package com.taoxue.ui.module.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taoxue.R;
import com.taoxue.ui.adapter.LsvViewHold;
import com.taoxue.ui.model.QryMyCollectionBean;
import com.taoxue.utils.glide.UtilGlide;

import java.util.List;


/**
 * 资源搜索结果adapter
 */
public class MyCollectAdapter extends BaseMyAdapter {

    public MyCollectAdapter(Context mContext, List list) {
        super(mContext, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LsvViewHold holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fa_home_lsv_history_read, null);
            holder = new LsvViewHold(convertView);
            convertView.setTag(holder);
        } else {
            holder = (LsvViewHold) convertView.getTag();
        }
        setView(mContext,holder, (QryMyCollectionBean) list.get(position));
        return convertView;
    }
    public void setView(Context context, LsvViewHold holder , QryMyCollectionBean bean) {
        UtilGlide.loadImg(context, bean.getResource_picture(), holder.iIv);
        holder.iTvTitle.setText(bean.getResource_name() + "");
        holder.iTvContent.setText(bean.getDescription() + "");
        holder.iTvCollectionNum.setText(bean.getCollection_num() + "");
        holder.iTvReadNum.setText(bean.getRead_num() + "");
    }
}
