package com.taoxue.ui.model;

/**
 * Created by User on 2018/2/1.
 */

public class RecordBean extends BaseModel {
            private String  read_num;
           private String  resource_id;
           private String  resource_name;
           private String  resource_picture;

    @Override
    public String toString() {
        return "RecordBean{" +
                "read_num='" + read_num + '\'' +
                ", resource_id='" + resource_id + '\'' +
                ", resource_name='" + resource_name + '\'' +
                ", resource_picture='" + resource_picture + '\'' +
                '}';
    }

    public String getResource_picture() {
        return resource_picture;
    }

    public void setResource_picture(String resource_picture) {
        this.resource_picture = resource_picture;
    }

    public String getRead_num() {
        return read_num;
    }

    public void setRead_num(String read_num) {
        this.read_num = read_num;
    }

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public String getResource_name() {
        return resource_name;
    }

    public void setResource_name(String resource_name) {
        this.resource_name = resource_name;
    }
}
