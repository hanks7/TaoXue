package com.taoxue.ui.model;

/**
 * Created by User on 2017/8/3.
 */

public class IsSeclectModel extends BaseModel {
           private String is_collection;
            private String is_comment;
            private String is_praise;

    @Override
    public String toString() {
        return "IsSeclectModel{" +
                "is_collection='" + is_collection + '\'' +
                ", is_comment='" + is_comment + '\'' +
                ", is_praise='" + is_praise + '\'' +
                '}';
    }

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
}
