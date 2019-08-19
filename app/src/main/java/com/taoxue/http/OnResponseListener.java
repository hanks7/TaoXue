package com.taoxue.http;

import android.text.TextUtils;

import com.taoxue.app.DialogInterface;
import com.taoxue.ui.model.BaseModel;
import com.taoxue.utils.LogUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CC on 2016/5/28.
 */
public abstract class OnResponseListener<T> implements Callback<T> {

    DialogInterface infa;

    /**
     * @param infa
     */
    public OnResponseListener(DialogInterface infa) {
        this.infa = infa;
        show();
    }

    /**
     * dialog隐藏
     */
    public void dismiss() {
        if (infa != null) {
            infa.dismissDialog();
        }
    }

    /**
     * dialog显示
     */
    public void show() {
        if (infa != null) {
            infa.showDialog();
        }
    }


    @Override
    public void onResponse(Call<T> call, Response<T> response) {


        if (response.code() == 200) {
            if (response.body() instanceof BaseModel) {

                BaseModel commonBean = (BaseModel) response.body();
                if (commonBean.getCode() == 1) {
                    onSuccess((T) commonBean);
                } else {
                    onError(commonBean.getCode(), commonBean.getMsg() + " 请联系管理员");
                }
            } else {
                onSuccess(response.body());
            }
        } else {
            String strError = "";
            try {
                strError += response.errorBody().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            onError(response.code(), strError);
        }
        dismiss();
    }


    @Override
    public void onFailure(Call<T> call, Throwable t) {
        LogUtils.E(t.getMessage());
        dismiss();
        String msg = t.getMessage();
        if (TextUtils.isEmpty(msg))
            msg = "请求异常";
//        UtilToast.showText(msg);
        onError(505, msg);
    }


    /**
     * 强制重写
     *
     * @param t
     */
    protected abstract void onSuccess(T t);

    /**
     * 选择重写
     */
    protected void onError(int code, String msg) {

    }
}