package com.taoxue.ui.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.taoxue.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 2017/8/3.
 */

public class FootHolder extends RecyclerView.ViewHolder {
    private View view;
    @BindView(R.id.pg_footer)
    public ProgressBar pgFooter;
    @BindView(R.id.tips)
    public TextView tips;
    @BindView(R.id.footview)
    public View footview;

    public FootHolder(View view) {
        super(view);
        this.view = view;
        ButterKnife.bind(this, view);
    }

    public void hideView(boolean hasMore) {
        tips.setVisibility(View.VISIBLE);
        if (hasMore == true) {
            tips.setText("正在加载更多...");
            pgFooter.setVisibility(View.VISIBLE);
        } else {
            this.view.setVisibility(View.GONE);
            pgFooter.setVisibility(View.GONE);
            tips.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }
    }
}
