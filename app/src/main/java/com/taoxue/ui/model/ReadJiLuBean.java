package com.taoxue.ui.model;

import java.util.List;

/**
 * Created by User on 2018/2/1.
 */

public class ReadJiLuBean extends BaseModel {
 private   String date;
 private   List<RecordBean>resource_record;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<RecordBean> getResource_record() {
        return resource_record;
    }

    public void setResource_record(List<RecordBean> resource_record) {
        this.resource_record = resource_record;
    }

    @Override
    public String toString() {
        return "ReadJiLuBean{" +
                "date='" + date + '\'' +
                ", resource_record=" + resource_record +
                '}';
    }
}
