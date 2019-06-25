package com.taoxue.ui.model;

import java.util.List;

/**
 * Created by User on 2017/7/28.
 */

public class GysResourceBean extends BaseResultModel {

    /**
     * page : {"autoCount":true,"first":1,"hasNext":true,"hasPre":false,"lastIndex":10,"nextPage":2,"orderBySetted":false,"pageNo":1,"pageSize":10,"prePage":1,"result":[{"resource_id":"402883d25d5a16b0015d5a16b0330000","resource_name":"大主宰","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/19/1500455009018097208_150.jpg"},{"resource_id":"402883d25d4f34be015d4f3a17b20001","resource_name":"古诗学堂第一部1","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/17/1500283164032084680_150.png"},{"resource_id":"402883d25d4f34be015d4f401e790002","resource_name":"古诗学堂第一部2","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/17/1500274330465028110_150.png"},{"resource_id":"402883d25d7ca410015d7cafa1d50009","resource_name":"美国公立中小学教师绩效工资改革新模式探析","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/26/1501035465723050178_150.jpg"},{"resource_id":"402883d25d7d448f015d7db063340043","resource_name":"新加坡小学生礼仪教育的特点及启示","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/26/1501052296958083492_150.jpg"},{"resource_id":"402883d25d7d448f015d7dae631e0040","resource_name":"扬起素质教育风帆 走可持续发展道路","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/26/1501052165379068344_150.jpg"},{"resource_id":"402883d25d7d448f015d7dab9bff0036","resource_name":"学会舍得","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/26/1501051946567067320_150.jpg"},{"resource_id":"402883d25d73f0f8015d78ee8f590032","resource_name":"小破孩系列-保护环境","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/25/1500972450458036838_150.png"},{"resource_id":"402883d25d595e22015d595e22dd0000","resource_name":"惩罚也是一种教育","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/19/1500457512067019661_150.jpg"},{"resource_id":"402883d25d5a16b0015d5a38448b0010","resource_name":"学会舍得","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/19/1500457438280061750_150.jpg"}],"totalCount":15,"totalPages":2}
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
         * hasNext : true
         * hasPre : false
         * lastIndex : 10
         * nextPage : 2
         * orderBySetted : false
         * pageNo : 1
         * pageSize : 10
         * prePage : 1
         * result : [{"resource_id":"402883d25d5a16b0015d5a16b0330000","resource_name":"大主宰","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/19/1500455009018097208_150.jpg"},{"resource_id":"402883d25d4f34be015d4f3a17b20001","resource_name":"古诗学堂第一部1","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/17/1500283164032084680_150.png"},{"resource_id":"402883d25d4f34be015d4f401e790002","resource_name":"古诗学堂第一部2","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/17/1500274330465028110_150.png"},{"resource_id":"402883d25d7ca410015d7cafa1d50009","resource_name":"美国公立中小学教师绩效工资改革新模式探析","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/26/1501035465723050178_150.jpg"},{"resource_id":"402883d25d7d448f015d7db063340043","resource_name":"新加坡小学生礼仪教育的特点及启示","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/26/1501052296958083492_150.jpg"},{"resource_id":"402883d25d7d448f015d7dae631e0040","resource_name":"扬起素质教育风帆 走可持续发展道路","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/26/1501052165379068344_150.jpg"},{"resource_id":"402883d25d7d448f015d7dab9bff0036","resource_name":"学会舍得","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/26/1501051946567067320_150.jpg"},{"resource_id":"402883d25d73f0f8015d78ee8f590032","resource_name":"小破孩系列-保护环境","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/25/1500972450458036838_150.png"},{"resource_id":"402883d25d595e22015d595e22dd0000","resource_name":"惩罚也是一种教育","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/19/1500457512067019661_150.jpg"},{"resource_id":"402883d25d5a16b0015d5a38448b0010","resource_name":"学会舍得","resource_picture":"http://117.71.57.47:10000/resource/uploadFiles/2017/07/19/1500457438280061750_150.jpg"}]
         * totalCount : 15
         * totalPages : 2
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
        private List<ResourceBean> result;

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

        public List<ResourceBean> getResult() {
            return result;
        }

        public void setResult(List<ResourceBean> result) {
            this.result = result;
        }

    }
}
