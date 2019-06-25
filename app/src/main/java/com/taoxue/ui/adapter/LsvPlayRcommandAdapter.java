package com.taoxue.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taoxue.R;
import com.taoxue.ui.model.RecommendForYouBean;
import com.taoxue.ui.module.search.BaseMyAdapter;
import com.taoxue.utils.glide.UtilGlide;

import java.util.List;

/**
 * Created by hanks7 on 2017/4/5.
 * <p>
 * 热门好书
 * Popularbooks
 */
public class LsvPlayRcommandAdapter extends BaseMyAdapter {


    public LsvPlayRcommandAdapter(Context mContext, List list) {
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
        setView(mContext,holder, (RecommendForYouBean) list.get(position));
        return convertView;
    }
    public void setView(Context context, LsvViewHold holder , RecommendForYouBean bean) {
        UtilGlide.loadImg(context, bean.getResource_picture(), holder.iIv);
        holder.iTvTitle.setText(bean.getResource_name() + "");
        holder.iTvContent.setText(bean.getDescription() + "");
        holder.iTvCollectionNum.setText(bean.getCollection_num() + "");
        holder.iTvReadNum.setText(bean.getRead_num() + "");
    }
}
