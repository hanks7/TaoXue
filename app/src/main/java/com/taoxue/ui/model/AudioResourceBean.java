package com.taoxue.ui.model;

/**
 * Created by User on 2018/2/5.
 */

public class AudioResourceBean extends BaseModel {
    private String resource_id;
    private String resource_name;
    private String resource_picture;
    private String gys_id;

    @Override
    public String toString() {
        return "AudioResourceBean{" +
                "resource_id='" + resource_id + '\'' +
                ", resource_name='" + resource_name + '\'' +
                ", resource_picture='" + resource_picture + '\'' +
                ", gys_id='" + gys_id + '\'' +
                '}';
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

    public String getResource_picture() {
        return resource_picture;
    }

    public void setResource_picture(String resource_picture) {
        this.resource_picture = resource_picture;
    }

    public String getGys_id() {
        return gys_id;
    }

    public void setGys_id(String gys_id) {
        this.gys_id = gys_id;
    }
}
