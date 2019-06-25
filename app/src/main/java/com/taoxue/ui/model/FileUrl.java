package com.taoxue.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 2017/4/25.
 */

public class FileUrl extends BaseModel {
        /**
         * catalog : 关键词:才智

         * comment_num : 0
         * is_collection : 0
         * is_comment : 0
         * is_praise : 0
         * item : [{"file_type":"video","volumn":[{"order":1,"resource_name":"story2","url":"http://117.71.57.47:10000/resource/uploadFiles/2017/09/22/1506059126123023355.mp4"},{"order":2,"resource_name":"story1","url":"http://117.71.57.47:10000/resource/uploadFiles/2017/09/18/1505724137035027463.mp4"}]}]
         */

        private String catalog;
        private int comment_num;
        private String is_collection;
        private String is_comment;
        private String is_praise;
        private List<ItemBean> item;

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

        public List<ItemBean> getItem() {
            return item;
        }

        public void setItem(List<ItemBean> item) {
            this.item = item;
        }

        public static class ItemBean implements Serializable{
            /**
             * file_type : video
             * volumn : [{"order":1,"resource_name":"story2","url":"http://117.71.57.47:10000/resource/uploadFiles/2017/09/22/1506059126123023355.mp4"},{"order":2,"resource_name":"story1","url":"http://117.71.57.47:10000/resource/uploadFiles/2017/09/18/1505724137035027463.mp4"}]
             */

            private String file_type;
            private List<VolumnBean> volumn;

            public String getFile_type() {
                return file_type;
            }

            public void setFile_type(String file_type) {
                this.file_type = file_type;
            }

            public List<VolumnBean> getVolumn() {
                return volumn;
            }

            public void setVolumn(List<VolumnBean> volumn) {
                this.volumn = volumn;
            }

        }
}
