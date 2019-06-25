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
 * <p>
 * 每天读本书  首页HomeFragment
 * Popularbooks
 */
public class LsvMainFeiAdapter extends BaseAdapter {
    private Context mContext;

    public LsvMainFeiAdapter(Context mContext, List<ApiOneBean.FreeBean> list) {
        this.mContext = mContext;
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList();
        }
    }


    public void addList(List<ApiOneBean.FreeBean> listBean) {
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
        ApiOneBean.FreeBean bean = (ApiOneBean.FreeBean) list.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_layout_free, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        UtilGlide.loadImgForIvHead(mContext, bean.getResource_picture(), holder.vhIv);
        holder.vhTvTitle.setText(bean.getResource_name());

        switch (bean.getMaterial().size()) {
            case 0:
                holder.vhTvNum1.setVisibility(View.GONE);
                holder.vhTvNum2.setVisibility(View.GONE);
                break;
            case 1:
                holder.vhTvNum1.setText(bean.getMaterial().get(0).getFile_name());
                holder.vhTvNum2.setVisibility(View.GONE);
                break;
            default:
                holder.vhTvNum1.setVisibility(View.VISIBLE);
                holder.vhTvNum2.setVisibility(View.VISIBLE);
                holder.vhTvNum1.setText(bean.getMaterial().get(0).getFile_name());
                holder.vhTvNum2.setText(bean.getMaterial().get(1).getFile_name());

        }

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.lsv_free_iv)
        ImageView vhIv;
        @BindView(R.id.lsv_free_tv_title)
        TextView vhTvTitle;
        @BindView(R.id.lsv_free_tv_num1)
        TextView vhTvNum1;
        @BindView(R.id.lsv_free_tv_num2)
        TextView vhTvNum2;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
