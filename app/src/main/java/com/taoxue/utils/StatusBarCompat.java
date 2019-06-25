package com.taoxue.utils;

import android.app.Activity;
import android.os.Build;

/**
 * 修改状态栏颜色
 */

public class StatusBarCompat {
    /**
     * 得到状态栏高度
     * 并设置沉浸
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Activity context) {

        int result = 0;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return result;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
            StatusbarUtils.enableTranslucentStatusbar(context);
        }
        return result;
    }

}
