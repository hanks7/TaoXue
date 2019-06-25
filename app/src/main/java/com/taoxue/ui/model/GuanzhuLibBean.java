package com.taoxue.ui.model;

import java.util.List;

/**
 * Created by User on 2018/2/1.
 */

public class GuanzhuLibBean extends BaseModel {
              private String  cgs_id;
              private String  logo;
              private String  name;
              private String  reader_num;
              private List<GuanzhuResourceBean> resource;

    @Override
    public String toString() {
        return "GuanzhuLibBean{" +
                "cgs_id='" + cgs_id + '\'' +
                ", logo='" + logo + '\'' +
                ", name='" + name + '\'' +
                ", reader_num='" + reader_num + '\'' +
                ", resource=" + resource +
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

    public String getReader_num() {
        return reader_num;
    }

    public void setReader_num(String reader_num) {
        this.reader_num = reader_num;
    }

    public List<GuanzhuResourceBean> getResource() {
        return resource;
    }

    public void setResource(List<GuanzhuResourceBean> resource) {
        this.resource = resource;
    }
}
