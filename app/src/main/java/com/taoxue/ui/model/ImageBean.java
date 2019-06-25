package com.taoxue.ui.model;

import java.util.List;

/**
 * Created by User on 2018/2/2.
 */

public class ImageBean extends BaseModel {


    @Override
    public String toString() {
        return "ImageBean{" +
                "file_type='" + file_type + '\'' +
                ", volumn=" + volumn +
                '}';
    }

    /**
     * file_type : image
     * volumn : [{"order":1,"resource_name":"u=2433551470,481573101&fm=27&gp=0","url":"http://117.71.57.47:10000/resource/uploadFiles/2017/12/08/1512700957037086290.jpg"},{"order":2,"resource_name":"timg8","url":"http://117.71.57.47:10000/resource/uploadFiles/2017/12/08/1512700956850092058.jpg"},{"order":3,"resource_name":"timg","url":"http://117.71.57.47:10000/resource/uploadFiles/2017/12/08/1512700956756040305.jpg"},{"order":4,"resource_name":"timg9","url":"http://117.71.57.47:10000/resource/uploadFiles/2017/12/08/1512700956881029050.jpg"},{"order":5,"resource_name":"timg3","url":"http://117.71.57.47:10000/resource/uploadFiles/2017/12/08/1512700956818033096.jpg"},{"order":6,"resource_name":"u=3603469643,2868357511&fm=27&gp=0","url":"http://117.71.57.47:10000/resource/uploadFiles/2017/12/08/1512700957068020210.jpg"},{"order":7,"resource_name":"timg13","url":"http://117.71.57.47:10000/resource/uploadFiles/2017/12/08/1512700956928048486.jpg"},{"order":8,"resource_name":"timg1","url":"http://117.71.57.47:10000/resource/uploadFiles/2017/12/08/1512700956787013748.jpg"}]
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

    public static class VolumnBean {
        /**
         * order : 1
         * resource_name : u=2433551470,481573101&fm=27&gp=0
         * url : http://117.71.57.47:10000/resource/uploadFiles/2017/12/08/1512700957037086290.jpg
         */

        private int order;
        private String resource_name;
        private String url;

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
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
