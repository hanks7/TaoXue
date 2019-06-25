package com.taoxue.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 2018/1/31.
 */

public class BaseItemListModel<Data extends Serializable> extends BaseModel {
   private   List<Data>item;


    public List<Data> getItem() {
        return item;
    }

    public void setItem(List<Data> item) {
        this.item = item;
    }

}
