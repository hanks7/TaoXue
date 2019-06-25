package com.taoxue.ui.model;

/**
 * Created by User on 2017/4/14.
 */

import com.taoxue.ui.model.BaseModel;

/**
 * avg_score : 2
 * catalog : 123123
 * catalog_standard : video
 * charge_value : 0.05
 * collection_num : 0
 * comment_num : 1
 * description : 破孩动画具有浓郁的中国特色，风格多样，有些幽默搞笑，又有一定讽刺意味；有些温馨浪漫，崇尚温情；有些童气十足，老少皆宜。小破孩动画注重娱乐性、思想性、艺术性的结合，动画内容取材广泛，不局限于中国传统文化素材，也可以是现代的、国外的、即时的素材，各种题材又融入了许多现代、时尚的元素，更适广大观众的口味。
 * discription : 小破孩系列-保护环境...
 * file_type : video
 * gys_id : 402883a95ce25928015ce27375bd0002
 * gys_name : 乐儿
 * is_collection : 0
 * is_comment : 1
 * praise_num : 0
 * resource_id : 402883d25d73f0f8015d78ee8f590032
 * resource_name : 小破孩系列-保护环境
 * resource_picture : http://117.71.57.47:10000/resource/uploadFiles/2017/08/04/1501825093352018131_150.png
 * score_num : 0
 * visit_num : 43
 */

public class ResourceModel extends BaseModel {
    private String avg_score;
    private String catalog;
    private String catalog_standard;
    private String collection_num;
    private String comment_num;
    private String discription;
    private String file_format;
    private String file_type;
    private String gys_id;
    private String gys_name;
    private String is_collection;
    private String is_praise;
    private String is_comment;
    private String praise_num;
    private String read_num;
    private String resource_id;
    private String resource_name;
    private String resource_picture;
    private String score_num;
    private String description;
    private String visit_num;
    private String charge_value;

    public String getIs_praise() {
        return is_praise;
    }

    public void setIs_praise(String is_praise) {
        this.is_praise = is_praise;
    }

    public String getCharge_value() {
        return charge_value;
    }

    public void setCharge_value(String charge_value) {
        this.charge_value = charge_value;
    }

    public String getAvg_score() {
        return avg_score;
    }

    public void setAvg_score(String avg_score) {
        this.avg_score = avg_score;
    }

    public String getVisit_num() {
        return visit_num;
    }

    public void setVisit_num(String visit_num) {
        this.visit_num = visit_num;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCatalog_standard() {
        return catalog_standard;
    }

    public void setCatalog_standard(String catalog_standard) {
        this.catalog_standard = catalog_standard;
    }

    public String getIs_comment() {
        return is_comment;
    }

    public void setIs_comment(String is_comment) {
        this.is_comment = is_comment;
    }

    @Override
    public String toString() {
        return "ResourceModel{" +
                "catalog='" + catalog + '\'' +
                ", collection_num='" + collection_num + '\'' +
                ", comment_num='" + comment_num + '\'' +
                ", discription='" + discription + '\'' +
                ", file_format='" + file_format + '\'' +
                ", file_type='" + file_type + '\'' +
                ", gys_id='" + gys_id + '\'' +
                ", gys_name='" + gys_name + '\'' +
                ", is_collection='" + is_collection + '\'' +
                ", praise_num='" + praise_num + '\'' +
                ", read_num='" + read_num + '\'' +
                ", resource_id='" + resource_id + '\'' +
                ", resource_name='" + resource_name + '\'' +
                ", resource_picture='" + resource_picture + '\'' +
                ", score_num='" + score_num + '\'' +
                '}';
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getCollection_num() {
        return collection_num;
    }

    public void setCollection_num(String collection_num) {
        this.collection_num = collection_num;
    }

    public String getComment_num() {
        return comment_num;
    }

    public void setComment_num(String comment_num) {
        this.comment_num = comment_num;
    }

    public String getFile_format() {
        return file_format;
    }

    public void setFile_format(String file_format) {
        this.file_format = file_format;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getGys_id() {
        return gys_id;
    }

    public void setGys_id(String gys_id) {
        this.gys_id = gys_id;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getIs_collection() {
        return is_collection;
    }

    public void setIs_collection(String is_collection) {
        this.is_collection = is_collection;
    }

    public String getGys_name() {
        return gys_name;
    }

    public void setGys_name(String gys_name) {
        this.gys_name = gys_name;
    }

    public String getRead_num() {
        return read_num;
    }

    public void setRead_num(String read_num) {
        this.read_num = read_num;
    }

    public String getPraise_num() {
        return praise_num;
    }

    public void setPraise_num(String praise_num) {
        this.praise_num = praise_num;
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

    public String getScore_num() {
        return score_num;
    }

    public void setScore_num(String score_num) {
        this.score_num = score_num;
    }
}
