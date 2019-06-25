package com.taoxue.ui.model;

import java.util.List;

/**
 * Created by User on 2017/7/24.
 */

public class CgsHomeInfoBean extends BaseModel {


    /**
     * cgs_info : {"address":"合肥市包河区芜湖路74号","addtime":"2017-04-05 16:09:37","cgs_code":"anhui","cgs_id":"1","city":"","linkman":"纪先生","logo":"http://117.71.57.47:9000/DRIS_manager_V1.0.1/upload/2017/04/27/402883bc5bae50f5015bae52d8e50004.png","name":"安徽图书馆","phone":"18066666666","province":"","reader_num":"6","short_name":"省图","status":"1","url":"http://www.ahlib.com/index.html"}
     * commend_resource : [{"description":"处处留心皆学问，惩罚也是一种教育","resource_id":"402883d25d595e22015d595e22dd0000","resource_name":"惩罚也是一种教育","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/19/1500457512067019661_150.jpg"},{"description":"舍得，舍得，有舍才有得，学会舍得","resource_id":"402883d25d5a16b0015d5a38448b0010","resource_name":"学会舍得","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/19/1500457438280061750_150.jpg"},{"description":"小破孩系列之爱在深秋","resource_id":"402883d25d6334d1015d633cf2710002","resource_name":"小破孩-爱在深秋","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/21/1500608501224056327_150.png"}]
     * hot_resource : [{"description":"古诗学堂两部曲","resource_id":"402883d25d4f34be015d4f401e790002","resource_name":"古诗学堂第一部2","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/17/1500274330465028110_150.png"},{"description":"林砺儒先生生平介绍","resource_id":"402883d25d5a16b0015d5a3a1d260011","resource_name":"林砺儒先生","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/19/1500457342482036143_150.jpg"},{"description":"小破孩系列之包","resource_id":"402883d25d6334d1015d6341b9660004","resource_name":"小破孩-包","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/21/1500608815125067989_150.png"}]
     * like_resource : [{"description":"古诗学堂两部曲","resource_id":"402883d25d4f34be015d4f401e790002","resource_name":"古诗学堂第一部2","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/17/1500274330465028110_150.png"},{"description":"舍得，舍得，有舍才有得，学会舍得","resource_id":"402883d25d5a16b0015d5a38448b0010","resource_name":"学会舍得","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/19/1500457438280061750_150.jpg"},{"description":"小破孩系列之半截烟囱","resource_id":"402883d25d6334d1015d633e6afc0003","resource_name":"小破孩-半截烟囱","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/21/1500608618964008883_150.png"}]
     */

    private CgsInfoBean cgs_info;
    private List<ResourceBean> commend_resource;
    private List<ResourceBean> hot_resource;
    private List<ResourceBean> like_resource;

    public CgsInfoBean getCgs_info() {
        return cgs_info;
    }

    public void setCgs_info(CgsInfoBean cgs_info) {
        this.cgs_info = cgs_info;
    }

    public List<ResourceBean> getCommend_resource() {
        return commend_resource;
    }

    public void setCommend_resource(List<ResourceBean> commend_resource) {
        this.commend_resource = commend_resource;
    }

    public List<ResourceBean> getHot_resource() {
        return hot_resource;
    }

    public void setHot_resource(List<ResourceBean> hot_resource) {
        this.hot_resource = hot_resource;
    }

    public List<ResourceBean> getLike_resource() {
        return like_resource;
    }

    public void setLike_resource(List<ResourceBean> like_resource) {
        this.like_resource = like_resource;
    }

    public static class CgsInfoBean {
        /**
         * address : 合肥市包河区芜湖路74号
         * addtime : 2017-04-05 16:09:37
         * cgs_code : anhui
         * cgs_id : 1
         * city : 
         * linkman : 纪先生
         * logo : http://117.71.57.47:9000/DRIS_manager_V1.0.1/upload/2017/04/27/402883bc5bae50f5015bae52d8e50004.png
         * name : 安徽图书馆
         * phone : 18066666666
         * province : 
         * reader_num : 6
         * short_name : 省图
         * status : 1
         * url : http://www.ahlib.com/index.html
         */

        private String address;
        private String addtime;
        private String cgs_code;
        private String cgs_id;
        private String city;
        private String linkman;
        private String logo;
        private String name;
        private String phone;
        private String province;
        private String reader_num;
        private String short_name;
        private String status;
        private String url;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getCgs_code() {
            return cgs_code;
        }

        public void setCgs_code(String cgs_code) {
            this.cgs_code = cgs_code;
        }

        public String getCgs_id() {
            return cgs_id;
        }

        public void setCgs_id(String cgs_id) {
            this.cgs_id = cgs_id;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getReader_num() {
            return reader_num;
        }

        public void setReader_num(String reader_num) {
            this.reader_num = reader_num;
        }

        public String getShort_name() {
            return short_name;
        }

        public void setShort_name(String short_name) {
            this.short_name = short_name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
