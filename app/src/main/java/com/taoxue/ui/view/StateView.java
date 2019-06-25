package com.taoxue.ui.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.FlagCodeBean;
import com.taoxue.ui.module.search.WebSupplierHomeActivity;
import com.taoxue.utils.UtilTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by User on 2017/4/19.
 *
 * 网络请求的地址
 */
public class StateView extends LinearLayout {

    @BindView(R.id.play_bottom_iv_2)
    ImageView mIv2;
    @BindView(R.id.play_bottom_iv_3)
    ImageView mIv3;
    @BindView(R.id.play_bottom_iv_4)
    ImageView mIv4;

    public StateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateView(Context context) {
        this(context, null);
    }

    public StateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_play_activity_bottom, this);
        ButterKnife.bind(this, view);

    }

    private String resource_id;
    private String gys_id;

    public void setResource_id(String resource_id, String gys_id) {
        this.resource_id = resource_id;
        this.gys_id = gys_id;
    }

    @OnClick({
            R.id.play_bottom_1,
            R.id.play_bottom_2,
            R.id.play_bottom_3,
            R.id.play_bottom_4
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.play_bottom_1:
                ((BaseActivity)getContext()).launch(WebSupplierHomeActivity.class, gys_id);
                break;
            case R.id.play_bottom_2:
                if (!UtilTools.judgeIsLogin((Activity)getContext())) break;
                HttpAdapter.getService().saveOrDelResourcePraise(resource_id, gys_id).enqueue(new OnResponseNoDialogListener<BaseResultModel<FlagCodeBean>>() {
                    @Override
                    protected void onSuccess(BaseResultModel<FlagCodeBean> model) {
                        int value = Integer.valueOf(model.getData().getFlag_code());
                        setDianZan(value);
                    }
                });
                break;
            case R.id.play_bottom_3:
                if (!UtilTools.judgeIsLogin((Activity)getContext())) break;
                HttpAdapter.getService().saveOrDelUserCollection(resource_id, gys_id).enqueue(new OnResponseNoDialogListener<BaseResultModel<FlagCodeBean>>() {
                    @Override
                    protected void onSuccess(BaseResultModel<FlagCodeBean> model) {
                        int value = Integer.valueOf(model.getData().getFlag_code());
                        setCollect(value);
                    }
                });
                break;
            case R.id.play_bottom_4:
                break;
        }
    }

    /**
     * 0:取消收藏
     * @param value
     */
    public void setCollect(int value) {
        switch (value) {
            case 0:
                mIv3.setImageResource(R.mipmap.shoucang);
                break;
            case 1:
                mIv3.setImageResource(R.mipmap.shoucang_true);
                break;
        }
    }

    /**
     * 0:取消点赞
     * @param value
     */
    public void setDianZan(int value) {
        switch (value) {
            case 0:
                mIv2.setImageResource(R.mipmap.dianzan);
                break;
            case 1:
                mIv2.setImageResource(R.mipmap.dianzan_true);
                break;
        }
    }
}
