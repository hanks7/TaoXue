package com.taoxue.ui.model;

/**
 * Created by User on 2018/2/1.
 */

public class BookInfoBean extends BaseModel {
             private String  author;
            private String  collection_num;
            private String  description;
            private String  pay_time;
            private String  pay_type;
            private String  gys_id;
            private String  read_num;
            private String  resource_id;
            private String  resource_name;
            private String  resource_picture;
            private String  total_fee;

    public String getGys_id() {
        return gys_id;
    }

    public void setGys_id(String gys_id) {
        this.gys_id = gys_id;
    }

    @Override
    public String toString() {
        return "BookInfoBean{" +
                "author='" + author + '\'' +
                ", collection_num='" + collection_num + '\'' +
                ", description='" + description + '\'' +
                ", pay_time='" + pay_time + '\'' +
                ", pay_type='" + pay_type + '\'' +
                ", read_num='" + read_num + '\'' +
                ", resource_id='" + resource_id + '\'' +
                ", resource_name='" + resource_name + '\'' +
                ", resource_picture='" + resource_picture + '\'' +
                ", total_fee='" + total_fee + '\'' +
                '}';
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCollection_num() {
        return collection_num;
    }

    public void setCollection_num(String collection_num) {
        this.collection_num = collection_num;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getRead_num() {
        return read_num;
    }

    public void setRead_num(String read_num) {
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

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }
}
