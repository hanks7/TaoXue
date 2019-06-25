package com.taoxue.ui.module.search.CopyMeiTuanCityStyle.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.model.City;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * author zaaach on 2016/1/26.
 */
public class ResultListAdapter extends BaseAdapter {
    private Activity mContext;
    private List<City> mCities;

    public ResultListAdapter(Activity mContext, List<City> mCities) {
        this.mCities = mCities;
        this.mContext = mContext;
    }

    public void changeData(List<City> list) {
        if (mCities == null) {
            mCities = list;
        } else {
            mCities.clear();
            mCities.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mCities == null ? 0 : mCities.size();
    }

    @Override
    public City getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.cp_item_search_result_listview, parent, false);
            holder = new ViewHolder(view);
            holder.name = (TextView) view.findViewById(R.id.tv_item_result_listview_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(mCities.get(position).getName());
        return view;
    }


    static class ViewHolder  {
        @BindView(R.id.tv_item_result_listview_name)
        TextView name;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
