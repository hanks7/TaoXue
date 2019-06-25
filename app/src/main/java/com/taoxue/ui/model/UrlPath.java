package com.taoxue.ui.model;

import java.util.List;

/**
 * Created by User on 2017/5/2.
 */

public class UrlPath extends BaseModel {
    private List<FileUrl.ItemBean> urlModel;
    private String is_collection;
    private String is_comment;
    private String is_praise;

    public String getIs_collection() {
        return is_collection;
    }

    public void setIs_collection(String is_collection) {
        this.is_collection = is_collection;
    }

    public String getIs_comment() {
        return is_comment;
    }

    public void setIs_comment(String is_comment) {
        this.is_comment = is_comment;
    }

    public String getIs_praise() {
        return is_praise;
    }

    public void setIs_praise(String is_praise) {
        this.is_praise = is_praise;
    }

    public List<FileUrl.ItemBean> getUrlModel() {
        return urlModel;
    }

    public void setUrlModel(List<FileUrl.ItemBean> urlModel) {
        this.urlModel = urlModel;
    }

    @Override
    public String toString() {
        return "UrlPath{" +
                "urlModel=" + urlModel +
                '}';
    }
}
