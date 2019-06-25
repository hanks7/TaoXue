package com.taoxue.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.ui.model.homefrag.ApiOneBean;
import com.taoxue.ui.module.classification.resourceLib.ResourceLibraryActivity;
import com.taoxue.utils.glide.UtilGlide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanks7 on 2017/4/5.
 * 馆藏推荐
 */
public class GvGctjAdapter extends BaseAdapter {
    private Context mContext;

    public GvGctjAdapter(Context mContext, List<ApiOneBean.BdqdBean> list) {
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

    private List<ApiOneBean.BdqdBean> list;

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
        final ApiOneBean.BdqdBean bean=  list.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fa_home_gv_new_release_one, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        UtilGlide.loadRoundImage(mContext,list.get(position).getResource_picture(),holder.iIv);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity)mContext).launch(ResourceLibraryActivity.class,bean.getResource_id());
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.item_fa_home_gv_new_iv)
        ImageView iIv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
