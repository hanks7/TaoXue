package com.taoxue.ui.model;

import java.io.Serializable;

/**
 * Created by CC on 2016/12/11.
 */

public class BaseResultModel<Data extends Serializable> extends BaseModel implements Serializable {
    private Data data;// {


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
