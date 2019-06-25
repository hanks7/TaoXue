package com.taoxue.ui.model;

/**
 * Created by User on 2017/4/25.
 */

public class VolumnBean extends BaseModel {

    /**
     * order : 1
     * resource_name : 小破孩-爱在深秋
     * url : http://117.71.57.47:10000/resource/uploadFiles/2017/07/21/1500608521709030565.mp4
     */

    private String order;
    private String resource_name;
    private String url;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getResource_name() {
        return resource_name;
    }

    public void setResource_name(String resource_name) {
        this.resource_name = resource_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
