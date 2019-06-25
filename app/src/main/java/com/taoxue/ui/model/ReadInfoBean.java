package com.taoxue.ui.model;

import java.util.List;

/**
 * Created by User on 2018/2/7.
 */

public class ReadInfoBean extends BaseModel {


    /**
     * avg_score : 0
     * collection_num : 0
     * comment_num : 0
     * description : 有一只100万年也不死的猫，它死了100万次，又活了100万次。
     * is_collection : 0
     * is_comment : 0
     * is_praise : 0
     * praise_num : 0
     * resource_name : 活了一百万次的猫
     */

    private String avg_score;
    private int collection_num;
    private int comment_num;
    private String description;
    private String is_collection;
    private String is_comment;
    private String is_praise;
    private int praise_num;
    private String resource_name;
    private List<FileUrl.ItemBean>item;

    private String gys_id;  //增加的
    private String resource_id; //增加的

    public String getGys_id() {
        return gys_id;
    }

    public void setGys_id(String gys_id) {
        this.gys_id = gys_id;
    }

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public List<FileUrl.ItemBean> getItem() {
        return item;
    }

    public void setItem(List<FileUrl.ItemBean> item) {
        this.item = item;
    }

    public String getAvg_score() {
        return avg_score;
    }

    public void setAvg_score(String avg_score) {
        this.avg_score = avg_score;
    }

    public int getCollection_num() {
        return collection_num;
    }

    public void setCollection_num(int collection_num) {
        this.collection_num = collection_num;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getPraise_num() {
        return praise_num;
    }

    public void setPraise_num(int praise_num) {
        this.praise_num = praise_num;
    }

    public String getResource_name() {
        return resource_name;
    }

    public void setResource_name(String resource_name) {
        this.resource_name = resource_name;
    }
}
