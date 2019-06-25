package com.taoxue.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 2017/8/3.
 */

public class BaseItemModel<Data extends Serializable> extends BaseModel{
    private int code;//1;
    private String msg;// "success",
    private List<Data> item;
    private IsSeclectModel data;

    @Override
    public String toString() {
        return "BaseItemModel{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", item=" + item +
                ", data=" + data +
                '}';
    }

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

    public List<Data> getItem() {
        return item;
    }

    public void setItem(List<Data> item) {
        this.item = item;
    }

    public IsSeclectModel getData() {
        return data;
    }

    public void setData(IsSeclectModel data) {
        this.data = data;
    }
}
