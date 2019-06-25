package com.taoxue.ui.model;

import java.util.List;

/**
 * Created by User on 2017/7/31.
 */

public class LibInfoModel extends BaseModel {
    private    List<LibCgsModel>cgs;
    private     DrDataInfoModel dr_data_info;
    private     LibResourceModel resource;

    @Override
    public String toString() {
        return "LibInfoModel{" +
                "cgs=" + cgs +
                ", dr_data_info=" + dr_data_info +
                ", resource=" + resource +
                '}';
    }

    public List<LibCgsModel> getCgs() {
        return cgs;
    }

    public void setCgs(List<LibCgsModel> cgs) {
        this.cgs = cgs;
    }

    public DrDataInfoModel getDr_data_info() {
        return dr_data_info;
    }

    public void setDr_data_info(DrDataInfoModel dr_data_info) {
        this.dr_data_info = dr_data_info;
    }

    public LibResourceModel getResource() {
        return resource;
    }

    public void setResource(LibResourceModel resource) {
        this.resource = resource;
    }
}
