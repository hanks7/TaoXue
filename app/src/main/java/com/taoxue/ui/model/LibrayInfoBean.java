package com.taoxue.ui.model;

/**
 * Created by User on 2018/2/8.
 */

public class LibrayInfoBean extends BaseModel {


    /**
     * cgs_id : 1
     * city : 合肥市
     * logo : http://117.71.57.47:10000/resource/uploadFiles/2017/08/04/1501830504555037068_150.jpg
     * name : 安徽图书馆
     * province : 安徽
     * short_name : 省图
     */

    private String cgs_id;
    private String city;
    private String logo;
    private String name;
    private String province;
    private String short_name;

    public String getCgs_id() {
        return cgs_id;
    }

    public void setCgs_id(String cgs_id) {
        this.cgs_id = cgs_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }
}
