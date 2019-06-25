package com.taoxue.ui.model;

import java.util.List;

/**
 * Created by User on 2017/7/27.
 */

public class GysDrdataBean extends  BaseResultModel {

    /**
     * page : {"autoCount":true,"first":1,"hasNext":false,"hasPre":false,"lastIndex":10,"nextPage":1,"orderBySetted":false,"pageNo":1,"pageSize":10,"prePage":1,"result":[{"description":"\"小破孩\"和\"小丫\"两个胖乎乎的卡通人物,造型简洁流畅,具有典型的中国风格.故事的取材和表现非常多样","gys_id":"402883a95ce25928015ce27375bd0002","gys_name":"澳通大连科技发展有限公司","id":"402883d25d73f0f8015d73f1f1070001","logo":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/24/1500888756757059711_150.jpg","name":"小破孩希系列"}],"totalCount":1,"totalPages":1}
     */

    private PageBean page;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public static class PageBean {
        /**
         * autoCount : true
         * first : 1
         * hasNext : false
         * hasPre : false
         * lastIndex : 10
         * nextPage : 1
         * orderBySetted : false
         * pageNo : 1
         * pageSize : 10
         * prePage : 1
         * result : [{"description":"\"小破孩\"和\"小丫\"两个胖乎乎的卡通人物,造型简洁流畅,具有典型的中国风格.故事的取材和表现非常多样","gys_id":"402883a95ce25928015ce27375bd0002","gys_name":"澳通大连科技发展有限公司","id":"402883d25d73f0f8015d73f1f1070001","logo":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/24/1500888756757059711_150.jpg","name":"小破孩希系列"}]
         * totalCount : 1
         * totalPages : 1
         */

        private boolean autoCount;
        private int first;
        private boolean hasNext;
        private boolean hasPre;
        private int lastIndex;
        private int nextPage;
        private boolean orderBySetted;
        private int pageNo;
        private int pageSize;
        private int prePage;
        private int totalCount;
        private int totalPages;
        private List<ResultBean> result;

        public boolean isAutoCount() {
            return autoCount;
        }

        public void setAutoCount(boolean autoCount) {
            this.autoCount = autoCount;
        }

        public int getFirst() {
            return first;
        }

        public void setFirst(int first) {
            this.first = first;
        }

        public boolean isHasNext() {
            return hasNext;
        }

        public void setHasNext(boolean hasNext) {
            this.hasNext = hasNext;
        }

        public boolean isHasPre() {
            return hasPre;
        }

        public void setHasPre(boolean hasPre) {
            this.hasPre = hasPre;
        }

        public int getLastIndex() {
            return lastIndex;
        }

        public void setLastIndex(int lastIndex) {
            this.lastIndex = lastIndex;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public boolean isOrderBySetted() {
            return orderBySetted;
        }

        public void setOrderBySetted(boolean orderBySetted) {
            this.orderBySetted = orderBySetted;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public List<ResultBean> getResult() {
            return result;
        }

        public void setResult(List<ResultBean> result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * description : "小破孩"和"小丫"两个胖乎乎的卡通人物,造型简洁流畅,具有典型的中国风格.故事的取材和表现非常多样
             * gys_id : 402883a95ce25928015ce27375bd0002
             * gys_name : 澳通大连科技发展有限公司
             * id : 402883d25d73f0f8015d73f1f1070001
             * logo : http://117.71.57.47:10000/resource/uploadFiles/2017/07/24/1500888756757059711_150.jpg
             * name : 小破孩希系列
             */

            private String description;
            private String gys_id;
            private String gys_name;
            private String id;
            private String logo;
            private String name;

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getGys_id() {
                return gys_id;
            }

            public void setGys_id(String gys_id) {
                this.gys_id = gys_id;
            }

            public String getGys_name() {
                return gys_name;
            }

            public void setGys_name(String gys_name) {
                this.gys_name = gys_name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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
}
