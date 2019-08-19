package com.taoxue.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.danikula.videocache.HttpProxyCacheServer;
import com.taoxue.app.BaseApplication;
import com.taoxue.ui.module.login.LoginActivity;

import static com.taoxue.app.Constants.FORMAL_URL;

/**
 * Created by User on 2017/4/17.
 */

public class UtilTools {
    public static Context context = BaseApplication.get();

    /**
     * 如果没登录直接跳转到登录界面
     *
     * @param activity
     * @return
     */
    public static boolean judgeIsLogin(Activity activity) {
        if (!BaseApplication.get().isLogin()) {
            UtilIntent.intentDIYLeftToRight(activity, LoginActivity.class);
        }
        return BaseApplication.get().isLogin();
    }

    public static String getResourcesString(int id) {
        return context.getResources().getString(id);
    }

    public static float getResourcesDimension(int id) {
        return context.getResources().getDimension(id);
    }

    /**
     * 沉浸 全透明实现
     * <p>
     * http://blog.csdn.net/qq_15807167/article/details/51800596  4.4 全透明 还是无法解决   有本事你把它解决. 并联系作者  邮箱474664736@qq.com
     */
    public static void solveLollipopStatebarProblem(Activity activity) {
        if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {//4.4 全透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);//calculateStatusColor(Color.WHITE, (int) alphaValue)
        }
    }

    /**
     * 设置textview 的左边的图片
     *
     * @param tv
     * @param mipmapID
     */
    public static void setTvDrawable(Context context, TextView tv, int mipmapID) {
        Drawable drawable = context.getResources().getDrawable(mipmapID);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawablePadding(11);//设置图片和text之间的间距
        tv.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 带 X 图标的 输入框 有文字 显示x 没文字 x消失
     *
     * @param sEdtInput
     * @param sIvClear
     */
    public static void clearEditText(final EditText sEdtInput, final ImageView sIvClear) {

        sIvClear.setVisibility(View.INVISIBLE);
        sEdtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    sIvClear.setVisibility(View.VISIBLE);
                } else {
                    sIvClear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sIvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sEdtInput.setText("");
            }
        });
    }

    static int i = 0;

    /**
     * 补全图片地址
     *
     * @param url
     * @return
     */
    @NonNull
    public static String getStringEND(String url) {
        switch ((i++) % 10) {
            case 0:
                url = "https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2564176338.jpg";
                break;
            case 1:
                url = "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2564170314.webp";
                break;
            case 2:
                url = "https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2562934437.webp";
                break;
            case 3:
                url = "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2564458983.webp";
                break;
            case 4:
                url = "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2549177902.jpg";
                break;
            case 5:
                url = "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2564368261.jpg";
                break;
            case 6:
                url = "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2562953341.jpg";
                break;
            case 7:
                url = "https://c-ssl.duitang.com/uploads/item/201406/30/20140630185226_xniwt.jpeg";
                break;
            case 8:
                url = "https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2560975437.jpg";
                break;
            case 9:
                url = "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2562677270.jpg";
                break;
            case 10:
                url = "https://img1.doubanio.com/view/photo/l/public/p2564990647.webp";
                break;
            case 11:
                url = "https://img3.doubanio.com/view/photo/l/public/p2564396154.webp";
                break;
            case 12:
                url = "https://img3.doubanio.com/view/photo/l/public/p2564396175.webp";
                break;
            case 13:
                url = "https://img3.doubanio.com/view/photo/l/public/p2564990643.webp";
                break;

            default:
                url = "https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2541901817.jpg";
        }
        if (TextUtils.isEmpty(url)) {
            url = "";
        }
        if (!url.startsWith("http")) {
            url = FORMAL_URL + url;
        }
        return url;
    }

    /**
     * 播放音频或者视频的缓存路径.
     *
     * @param url
     * @return
     */
    public static String getProxyUrl(String url) {
        HttpProxyCacheServer proxy = BaseApplication.getProxy();
        LogUtils.i("url", url);
        return proxy.getProxyUrl(url);
    }

    //activity 跳转参数
    public static String getActivityKey1(Class clazz) {
        return "key:" + clazz.getSimpleName() + ":one";
    }

    public static String getActivityKey2(Class clazz) {
        return "key:" + clazz.getSimpleName() + ":two";
    }
}
