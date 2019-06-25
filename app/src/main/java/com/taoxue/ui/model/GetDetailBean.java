package com.taoxue.ui.model;

import java.io.Serializable;

/**
 * @author 侯建军
 * @data on 2018/2/6 15:01
 * @org www.hopshine.com
 * @function 请填写
 * @email 474664736@qq.com
 */

public class GetDetailBean implements Serializable {
    /**
     * collection_num : 0
     * description : 哪吒打死龙王三太子，为民除害的故事。
     * read_num : 3
     * resource_id : 402883d26033f0020160341d40c9002a
     * resource_name : 哪吒闹海
     * resource_picture : http://117.71.57.47:10000/resource/uploadFiles/2017/12/08/1512702817240041079_150.png
     */

    private int collection_num;
    private String description;
    private int read_num;
    private String resource_id;
    private String resource_name;
    private String resource_picture;

    public int getCollection_num() {
        return collection_num;
    }

    public void setCollection_num(int collection_num) {
        this.collection_num = collection_num;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRead_num() {
        return read_num;
    }

    public void setRead_num(int read_num) {
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

    public String getResource_picture() {
        return resource_picture;
    }

    public void setResource_picture(String resource_picture) {
        this.resource_picture = resource_picture;
    }
}