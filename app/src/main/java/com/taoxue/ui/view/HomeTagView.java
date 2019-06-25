package com.taoxue.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
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

public class HomeTagView extends LinearLayout {

    private final boolean isGone;
    @BindView(R.id.include_home_tag_icon_entry)
    ImageView iconEntry;
    private String mTitle;

    @BindView(R.id.include_home_tag_tv_title)
    TextView tvTitleName;

    public HomeTagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeTagView(Context context) {
        this(context, null);
    }

    public HomeTagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.include_home_tag, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.test_view);
        ButterKnife.bind(this, view);
        mTitle = array.getString(R.styleable.test_view_layoutText);
        isGone = array.getBoolean(R.styleable.test_view_isGone, false);
        if(isGone){
            iconEntry.setVisibility(GONE);
        }
        tvTitleName.setText(mTitle);

    }

}
