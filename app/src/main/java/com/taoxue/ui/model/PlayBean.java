package com.taoxue.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 2017/4/25.
 */

public class PlayBean<DataBean extends Serializable> extends BaseResultModel {

    /**
     * data : {"catalog":"时长：00：00\n题名：小破孩-爱在深秋\n关键词：小破孩，爱\n描述：小破孩\n出版者：无\n","comment_num":0,"is_collection":"0","is_comment":"1","is_praise":"0"}
     * item : [{"order":"1","resource_name":"小破孩-爱在深秋","url":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/21/1500608521709030565.mp4"}]
     */

    private List<ItemBean> item;


    public List<ItemBean> getItem() {
        return item;
    }

    public void setItem(List<ItemBean> item) {
        this.item = item;
    }

    public static class DataBean implements Serializable{
        /**
         * catalog : 时长：00：00
         题名：小破孩-爱在深秋
         关键词：小破孩，爱
         描述：小破孩
         出版者：无

         * comment_num : 0
         * is_collection : 0
         * is_comment : 1
         * is_praise : 0
         */

        private String catalog;
        private int comment_num;
        private String is_collection;
        private String is_comment;
        private String is_praise;

        public String getCatalog() {
            return catalog;
        }

        public void setCatalog(String catalog) {
            this.catalog = catalog;
        }

        public int getComment_num() {
            return comment_num;
        }

        public void setComment_num(int comment_num) {
            this.comment_num = comment_num;
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

    public static class ItemBean {
        /**
         * order : 1
         * resource_name : 小破孩-爱在深秋
         * url : http://117.71.57.47:10000/resource/uploadFiles/2017/07/21/1500608521709030565.mp4
         */

        private String order;
        private String resource_name;
        private String url;

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getResource_name() {
            return resource_name;
        }

        public void setResource_name(String resource_name) {
            this.resource_name = resource_name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
