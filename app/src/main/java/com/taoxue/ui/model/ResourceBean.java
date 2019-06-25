package com.taoxue.ui.model;

/**
 * Created by User on 2017/7/27.
 */

public  class ResourceBean {
    /**
     * description : 处处留心皆学问，惩罚也是一种教育
     * resource_id : 402883d25d595e22015d595e22dd0000
     * resource_name : 惩罚也是一种教育
     * resource_picture : http://117.71.57.47:10000/resource/uploadFiles/2017/07/19/1500457512067019661_150.jpg
     */

    private String description;
    private String resource_id;
    private String resource_name;
    private String resource_picture;
    private int flag;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
