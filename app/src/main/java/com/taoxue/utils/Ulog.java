package com.taoxue.utils;

import android.util.Log;

import static com.taoxue.app.Constants.IS_LOG;


/**
 * Created by CC on 2016/6/4.
 */

public class Ulog {
    public static final String TAG = "-CC-";

    public static void D(String msg) {
        if (IS_LOG)
            Log.d(TAG, String.valueOf(msg));
    }

    public static void V(String msg) {
        if (IS_LOG)
            Log.v(TAG, String.valueOf(msg));
    }

    public static void I(String msg) {
        if (IS_LOG)
            Log.i(TAG, String.valueOf(msg));
    }
    public static void i(Object str,Object msg) {
        if (IS_LOG)
            Log.i(TAG+str, "  "+msg);
    }
    public static void i(Object msg) {
        if (IS_LOG)
            Log.i(TAG+"hj", "  "+msg);
    }

    public static void E(String msg) {
        if (IS_LOG)
            Log.e(TAG, String.valueOf(msg));
    }
    public static void E(Object str,Object msg) {
        if (IS_LOG)
            Log.e(TAG+str, String.valueOf(msg));
    }


}
