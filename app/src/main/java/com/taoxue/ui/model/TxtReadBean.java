package com.taoxue.ui.model;

import java.util.List;

/**
 * Created by User on 2018/2/3.
 */

public class TxtReadBean extends BaseModel {
    /**
     * file_type : doc
     * volumn : [{"order":1,"resource_name":"合肥市志","url":"http://117.71.57.47:10000/resource/uploadFiles/2018/02/03/eddc7d95-460c-4217-82c9-2f811e5d05e2.txt"}]
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


    public static class VolumnBean extends BaseModel {
        /**
         * order : 1
         * resource_name : 合肥市志
         * url : http://117.71.57.47:10000/resource/uploadFiles/2018/02/03/eddc7d95-460c-4217-82c9-2f811e5d05e2.txt
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
