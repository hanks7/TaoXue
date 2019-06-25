package com.taoxue.ui.model;

import java.io.Serializable;

/**
 * Created by CC on 2016/12/11.
 */

public class BaseBean implements Serializable {
    private int code;//200;
    private String msg;// "success",

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
