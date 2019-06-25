package com.taoxue.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.taoxue.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanks7 on 2018/2/24.
 */
public class LsvViewHold {
    @BindView(R.id.item_fa_home_history_iv)
    public ImageView iIv;
    @BindView(R.id.item_fa_home_history_tv_title)
    public TextView iTvTitle;
    @BindView(R.id.item_fa_home_history_tv_content)
    public TextView iTvContent;
    @BindView(R.id.item_fa_home_tv_collection_num)
    public TextView iTvCollectionNum;
    @BindView(R.id.item_fa_home_tv_read_num)
    public TextView iTvReadNum;

    public LsvViewHold(View view) {
        ButterKnife.bind(this, view);
    }
}
