package com.taoxue.ui.model;

/**
 * Created by User on 2017/7/31.
 */

public class DrDataInfoModel extends BaseModel {
    private String description;
            private String gys_name;
            private String logo;
            private String name;
            private String resource_num;
            private    String visit_num;

    public String getVisit_num() {
        return visit_num;
    }

    public void setVisit_num(String visit_num) {
        this.visit_num = visit_num;
    }

    @Override
    public String toString() {
        return "DrDataInfoModel{" +
                "description='" + description + '\'' +
                ", gys_name='" + gys_name + '\'' +
                ", logo='" + logo + '\'' +
                ", name='" + name + '\'' +
                ", resource_num='" + resource_num + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGys_name() {
        return gys_name;
    }

    public void setGys_name(String gys_name) {
        this.gys_name = gys_name;
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

    public String getResource_num() {
        return resource_num;
    }

    public void setResource_num(String resource_num) {
        this.resource_num = resource_num;
    }
}
