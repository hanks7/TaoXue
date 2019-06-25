package com.taoxue.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.ui.model.homefrag.ApiOneBean;
import com.taoxue.utils.glide.UtilGlide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 2017/8/28.
 */

public class LsvRmhsAdapterViewHold {
    @BindView(R.id.item_fa_home_history_iv)
    ImageView iIv;
    @BindView(R.id.item_fa_home_history_tv_title)
    TextView iTvTitle;
    @BindView(R.id.item_fa_home_history_tv_content)
    TextView iTvContent;
    @BindView(R.id.item_fa_home_tv_collection_num)
    TextView iTvCollectionNum;
    @BindView(R.id.item_fa_home_tv_read_num)
    TextView iTvVisitNum;

    public LsvRmhsAdapterViewHold(View view) {
        ButterKnife.bind(this, view);
    }

    public void setView(Context context, ApiOneBean.BdqdBean bean) {
        UtilGlide.loadImg(context, bean.getResource_picture(), iIv);
        iTvTitle.setText(bean.getResource_name() + "");
        iTvContent.setText(bean.getDescription() + "");
        iTvCollectionNum.setText(bean.getRead_num() + "");
        iTvVisitNum.setText(bean.getRead_num() + "");
    }
}
