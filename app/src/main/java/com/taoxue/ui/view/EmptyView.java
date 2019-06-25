package com.taoxue.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.utils.Ulog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanks7 on 2018/2/25.
 */

public class EmptyView extends LinearLayout {

    public static final String TAG = "emptyView";



    @BindView(R.id.empty_view_iv)
    ImageView iv;
    @BindView(R.id.empty_view_tv)
    TextView tv;

    private int mipmapID ;//中间的图片
    private String str;//中间的文字

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_empty_view, this);
        ButterKnife.bind(this, view);
        this.setVisibility(View.GONE);
        mipmapID = R.mipmap.no_data;
        str=getResources().getString(R.string.tempare_no_data);
    }
    /**
     * 修改默认图片
     */
    public void setErrorPic(int mipmapID, String str) {
        this.mipmapID=mipmapID;
        this.str=str;
    }
    /**
     * 设置无数据
     */
    public void setNodata() {
        this.setVisibility(View.VISIBLE);
        iv.setImageDrawable(getContext().getResources().getDrawable(mipmapID));
        tv.setText(str);
        this.setEnabled(false);
    }



    /**
     * 设置无数据
     */
    public void setErrordata(final Runnable runnable) {
        this.setVisibility(View.VISIBLE);
        iv.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.default_bg));
        tv.setText(R.string.string_error_data);
        iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                runnable.run();
                Ulog.i(TAG, "setErrordata");
            }
        });
        this.setEnabled(true);
    }

    /**
     * 设置有数据
     */
    public void setHasdata() {
        this.setVisibility(View.GONE);
        this.setEnabled(false);
    }

}

