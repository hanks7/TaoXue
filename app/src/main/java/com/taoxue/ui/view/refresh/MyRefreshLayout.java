package com.taoxue.ui.view.refresh;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrFrameLayout;


/**
 *仿京东下拉刷新
 */
public class MyRefreshLayout extends PtrFrameLayout {
    /**
     * HeaderView
     */
    private MyRefreshHeader mHeaderView;

    public MyRefreshLayout(Context context) {
        this(context, null);
    }

    public MyRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initView();
    }


    /**
     * 初始化HeaderView
     */
    private void initView() {
        mHeaderView = new MyRefreshHeader(getContext());
        setHeaderView(mHeaderView);
        addPtrUIHandler(mHeaderView);
    }
}
