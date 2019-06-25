package com.taoxue.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.taoxue.R;
import com.taoxue.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by User on 2017/8/28.
 * 仿淘宝垂直滚动
 */
public class UPMarqueeView extends ViewFlipper {

    private Context mContext;
    private boolean isSetAnimDuration = false;
    private int interval = 2000;


    /**
     * 动画时间
     */
    private int animDuration = 500;


    public UPMarqueeView(Context context) {
        this(context, null);
    }

    public UPMarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        this.mContext = context;
        setFlipInterval(interval);
        Animation animIn = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_in);
        if (isSetAnimDuration) animIn.setDuration(animDuration);
        setInAnimation(animIn);
        Animation animOut = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_out);
        if (isSetAnimDuration) animOut.setDuration(animDuration);
        setOutAnimation(animOut);
    }
    /**
     * 添加数据并滚动
     */
    public void addData(List<String> data,OnItemClickListener onItemClickListener) {
        setViews(addData(data));
        setOnItemClickListener(onItemClickListener);
    }

    /**
     * 设置循环滚动的View数组
     *
     * @param views
     */
    public void setViews(final List<View> views) {

        if (views == null || views.size() == 0) return;
        removeAllViews();
        LogUtils.i("setViews",views.size());
        for (int i = 0; i < views.size(); i++) {
            final int position = i;

            views.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
            ViewGroup viewGroup = (ViewGroup) views.get(i).getParent();
            if (viewGroup != null) {
                viewGroup.removeAllViews();
            }
            addView(views.get(i));
        }
        startFlipping();
    }

    /**
     * 点击
     */
    private OnItemClickListener onItemClickListener;

    /**
     * 设置监听接口
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * item_view的接口
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }



    /**
     * 初始化需要循环的View
     * 为了灵活的使用滚动的View，所以把滚动的内容让用户自定义
     * 假如滚动的是三条或者一条，或者是其他，只需要把对应的布局，和这个方法稍微改改就可以了，
     */
    private List<View> addData(List<String> data) {
        List<View> views = new ArrayList<>();
        for (int i = 0; i < data.size(); i ++) {
            //设置滚动的单个布局
            View moreView = LayoutInflater.from(getContext()).inflate(R.layout.item_ui_view, null);
            ViewHold holder = new ViewHold(moreView);
            holder.setView(data.get(i));
            //添加到循环滚动数组里面去
            views.add(moreView);
        }
        return views;
    }

    public static class ViewHold {
        @BindView(R.id.item_ui_content)
        TextView iTvContent;

        public ViewHold(View view) {
            ButterKnife.bind(this, view);
        }

        public void setView( String str) {
            iTvContent.setText(str);
        }
    }


}
