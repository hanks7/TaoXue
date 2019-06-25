package com.taoxue.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taoxue.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 2017/4/19.
 */

public class TestView extends LinearLayout {

    private  String mTitle;
    private Drawable mDrawable;
    @BindView(R.id.iv_title_pic)
    ImageView ivTitlePic;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;

    public TestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public TestView(Context context) {
        this(context, null);
    }
    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.test_view, this);
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.test_view);
        ButterKnife.bind(this, view);
        mTitle = array.getString(R.styleable.test_view_layoutText);
        mDrawable = array.getDrawable(R.styleable.test_view_layoutTextBackground);
        tvTitleName.setText(mTitle);
        ivTitlePic.setImageDrawable(mDrawable);
    }

}
