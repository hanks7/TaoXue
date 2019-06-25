package com.taoxue.ui.module.classification;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.taoxue.R;
import com.taoxue.utils.LogUtils;

/**
 * Created by User on 2017/8/2.
 */

public class PaddingDecoration extends RecyclerView.ItemDecoration {
    private int divider;
    boolean isTargetItem=false;

    int firstItem=0;
    public PaddingDecoration(Context context) {
        //即你要设置的分割线的宽度 --这里设为10dp
        divider = context.getResources().getDimensionPixelSize(R.dimen.decortionPaddingLeft);
    }





    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int itemPosition=parent.getChildAdapterPosition(view);
        int childCount = parent.getAdapter().getItemCount();
//        LogUtils.D("getSpanCount(RecyclerView parent)--->"+getSpanCount(parent));
        RecyclerView.LayoutManager  layoutManager=parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
             int spanCount=((GridLayoutManager) layoutManager).getSpanCount();
          int spanSize= ((GridLayoutManager) layoutManager).getSpanSizeLookup().getSpanSize(itemPosition);
            LogUtils.D("spanSize--->"+spanSize);
            if (spanSize==1){
                LogUtils.D("divider--->"+divider);
                if (!isTargetItem) {
                    firstItem=itemPosition;
                    outRect.left = divider;  //相当于 设置 bottom padding
                    isTargetItem=true;
                }else {
                    if ((itemPosition - firstItem + 1) % spanCount == 0) {
                        LogUtils.D("右边");
                        outRect.right = divider;
                    } else if ((itemPosition - firstItem) % spanCount == 0) {
                        LogUtils.D("左边");
                        outRect.left = divider;
                    }
                }
            }else{
              isTargetItem=false;
            }
        }


//        outRect.left = divider;  //相当于 设置 left padding
//        outRect.top = divider;   //相当于 设置 top padding
//        outRect.right = divider; //相当于 设置 right padding
//        outRect.bottom = divider;  //相当于 设置 bottom padding
    }

}
