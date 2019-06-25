package com.taoxue.utils.update;


import com.taoxue.ui.model.BaseModel;

/**
 * Created by Administrator on 2016/8/4.
 */
public class IsUpdateBean extends BaseModel {

    /**
     * download_apk_url : http://117.71.57.47:11000/frontend/common/download/apk.do
     * remark : 1、更新说明；2、修改部分BUG
     * version_num : 1.22
     */

    private String download_apk_url;
    private String remark;
    private String version_num;
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDownload_apk_url() {
        return download_apk_url;
    }

    public void setDownload_apk_url(String download_apk_url) {
        this.download_apk_url = download_apk_url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getVersion_num() {
        return version_num;
    }

    public void setVersion_num(String version_num) {
        this.version_num = version_num;
    }
}
