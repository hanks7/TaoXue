package com.taoxue.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.taoxue.ui.adapter.BaseTaoXuedapter;

/**
 * Created by User on 2017/11/23.
 */
public class MyListView extends ListView {
    private static final String TAG = "MyListView";
    ListViewFootView footView;

    public MyListView(Context context) {
        this(context, null);
    }

    public MyListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        footView = new ListViewFootView(getContext());
    }


    //    @Override
//    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//        int defaultsize = measureHight(Integer.MAX_VALUE >> 2, heightMeasureSpec);
//        int expandSpec = MeasureSpec.makeMeasureSpec(defaultsize, MeasureSpec.AT_MOST);
//
//        super.onMeasure(widthMeasureSpec, expandSpec);
//    }
    BaseTaoXuedapter adapter;

    public void setMyAdapter(BaseTaoXuedapter adapter) {
        this.adapter = adapter;
        setAdapter(adapter);
    }

    private int measureHight(int size, int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            Log.i(TAG, "exactly");

            result = specSize;
        } else {

            result = size;//最小值是200px ，自己设定
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
            Log.i(TAG, "specMode:" + specMode + "--result:" + result);
        }
        return result;
    }

    /**
     * 改listview在顶端
     *
     * @return
     */
    public boolean isTop() {
//        int firstVisibleItem = getFirstVisiblePosition();
//        if (firstVisibleItem == 0) {
        View firstVisibleItemView = getChildAt(0);
        if (firstVisibleItemView != null && firstVisibleItemView.getTop() == 0) {
            Log.i(TAG, "##### 滚动到顶部 ######");
            return true;
        }
//        }
        return false;
    }

    /**
     * 改listview滑到底端了
     *
     * @return
     */
    public boolean isBottom() {
//        int firstVisibleItem = getFirstVisiblePosition();//屏幕上显示的第一条是list中的第几条
//        int childcount = getChildCount();//屏幕上显示多少条item
//        int totalItemCount = getCount();//一共有多少条
//        if ((firstVisibleItem + childcount) >= totalItemCount) {
        View lastVisibleItemView = getChildAt(getChildCount() - 1);
        if (lastVisibleItemView != null && lastVisibleItemView.getBottom() == getHeight()) {
            Log.i(TAG, "##### 滚动到底部 ######");
            return true;
        }
//        }
        return false;
    }


    float down = 0;
    float y;
    boolean isFootViewShow;

    public void startMore(boolean isStartMore) {
        isFootViewShow = isStartMore;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                down = event.getRawY();

                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                y = event.getRawY();
                Log.i(TAG, "dispatchTouchEvent: " + y);
                if (isTop()) {
                    if (checkTopOrBottomClick != null) {
                        checkTopOrBottomClick.getTop();
                    }
                    if (y - down > 1) {
//                        到顶端,向下滑动 把事件教给父类
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        //                        到顶端,向上滑动 把事件拦截 由自己处理
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }

                if (isBottom()) {
                    if (checkTopOrBottomClick != null) {
                        checkTopOrBottomClick.getBottom();
                    }
                    if (isFootViewShow) {
                        addFooterView(footView);
                    } else {
                        removeFooterView(footView);
                    }
                    if (y - down > 1) {
//                        到底端,向下滑动 把事件拦截 由自己处理
                        getParent().requestDisallowInterceptTouchEvent(true);
                    } else {
//                        到底端,向上滑动 把事件教给父类
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    private CheckTopOrBottomClick checkTopOrBottomClick;

    public void setTopBottom(CheckTopOrBottomClick click) {
        checkTopOrBottomClick = click;
    }

    interface CheckTopOrBottomClick {
        void getTop();

        void getBottom();
    }

}
