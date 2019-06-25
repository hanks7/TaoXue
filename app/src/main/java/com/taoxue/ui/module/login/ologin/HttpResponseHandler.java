package com.taoxue.ui.module.login.ologin;

/**
 * Created by User on 2017/9/11.
 */

public interface HttpResponseHandler {
    void onSuccess(String response);
    void onError(String msg);

}
