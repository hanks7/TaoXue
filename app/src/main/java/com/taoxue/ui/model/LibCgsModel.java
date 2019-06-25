package com.taoxue.ui.model;

/**
 * Created by User on 2017/7/31.
 */

public class LibCgsModel extends BaseModel {
           private String cgs_id;
           private  String logo;
           private  String  name;

    @Override
    public String toString() {
        return "LibCgsModel{" +
                "cgs_id='" + cgs_id + '\'' +
                ", logo='" + logo + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getCgs_id() {
        return cgs_id;
    }

    public void setCgs_id(String cgs_id) {
        this.cgs_id = cgs_id;
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
}
