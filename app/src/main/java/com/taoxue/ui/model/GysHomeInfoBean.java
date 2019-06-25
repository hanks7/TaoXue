package com.taoxue.ui.model;

import java.util.List;

/**
 * Created by User on 2017/7/24.
 */

public class GysHomeInfoBean extends BaseModel {


    /**
     * commend_resource : [{"resource_id":"402883d25d595e22015d595e22dd0000","resource_name":"惩罚也是一种教育","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/19/1500457512067019661_150.jpg"},{"resource_id":"402883d25d5a16b0015d5a38448b0010","resource_name":"学会舍得","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/19/1500457438280061750_150.jpg"},{"resource_id":"402883d25d6334d1015d633cf2710002","resource_name":"小破孩-爱在深秋","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/21/1500608501224056327_150.png"}]
     * gys_info : {"description":"一家专注于儿童阅读的数字资源供应商","logo":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/25/1500947648918058711_150.jpg","name":"澳通大连科技发展有限公司"}
     * hot_resource : [{"resource_id":"402883d25d4f34be015d4f401e790002","resource_name":"古诗学堂第一部2","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/17/1500274330465028110_150.png"},{"resource_id":"402883d25d5a16b0015d5a3a1d260011","resource_name":"林砺儒先生","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/19/1500457342482036143_150.jpg"},{"resource_id":"402883d25d6334d1015d6341b9660004","resource_name":"小破孩-包","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/21/1500608815125067989_150.png"}]
     * like_resource : [{"resource_id":"402883d25d4f34be015d4f401e790002","resource_name":"古诗学堂第一部2","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/17/1500274330465028110_150.png"},{"resource_id":"402883d25d5a16b0015d5a38448b0010","resource_name":"学会舍得","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/19/1500457438280061750_150.jpg"},{"resource_id":"402883d25d6334d1015d633e6afc0003","resource_name":"小破孩-半截烟囱","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/21/1500608618964008883_150.png"}]
     */

    private GysInfoBean gys_info;
    private List<ResourceBean> commend_resource;
    private List<ResourceBean> hot_resource;
    private List<ResourceBean> like_resource;

    public GysInfoBean getGys_info() {
        return gys_info;
    }

    public void setGys_info(GysInfoBean gys_info) {
        this.gys_info = gys_info;
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

    public static class GysInfoBean {

        /**
         * description : 一家专注于儿童阅读的数字资源供应商
         * logo : http://117.71.57.47:10000/resource/uploadFiles/2017/07/25/1500947648918058711_150.jpg
         * name : 澳通大连科技发展有限公司
         */

        private String description;
        private String logo;
        private String name;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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
    }



}
