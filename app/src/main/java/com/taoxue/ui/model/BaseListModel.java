package com.taoxue.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 2017/4/5.
 */

public class BaseListModel<Data extends Serializable> extends BaseModel{

    private List<Data> item;


    public List<Data> getItem() {
        return item;
    }

    public void setItem(List<Data> item) {
        this.item = item;
    }

}
