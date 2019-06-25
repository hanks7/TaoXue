package com.taoxue.ui.module.login.ologin;

/**
 * Created by User on 2017/9/11.
 */

public class QQAgreeInfo {
    String openID;
    String accessToken;
    String expires;

    @Override
    public String toString() {
        return "QQAgreeInfo{" +
                "openID='" + openID + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", expires='" + expires + '\'' +
                '}';
    }

    public String getOpenID() {
        return openID;
    }

    public void setOpenID(String openID) {
        this.openID = openID;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }
}
