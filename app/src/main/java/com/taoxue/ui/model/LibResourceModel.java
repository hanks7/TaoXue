package com.taoxue.ui.model;

import java.util.List;

/**
 * Created by User on 2017/7/31.
 */

public class LibResourceModel extends BaseModel {
           private String autoCount;
            private String first;
            private String hasNext;
            private String hasPre;
            private String lastIndex;
            private String nextPage;
            private String orderBySetted;
            private String pageNo;
            private String pageSize;
            private String prePage;
            private List<LibResultModel> result;
            private String totalCount;
            private String totalPages;

    @Override
    public String toString() {
        return "LibResourceModel{" +
                "autoCount='" + autoCount + '\'' +
                ", first='" + first + '\'' +
                ", hasNext='" + hasNext + '\'' +
                ", hasPre='" + hasPre + '\'' +
                ", lastIndex='" + lastIndex + '\'' +
                ", nextPage='" + nextPage + '\'' +
                ", orderBySetted='" + orderBySetted + '\'' +
                ", pageNo='" + pageNo + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", prePage='" + prePage + '\'' +
                ", result=" + result +
                ", totalCount='" + totalCount + '\'' +
                ", totalPages='" + totalPages + '\'' +
                '}';
    }

    public String getAutoCount() {
        return autoCount;
    }

    public void setAutoCount(String autoCount) {
        this.autoCount = autoCount;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getHasNext() {
        return hasNext;
    }

    public void setHasNext(String hasNext) {
        this.hasNext = hasNext;
    }

    public String getHasPre() {
        return hasPre;
    }

    public void setHasPre(String hasPre) {
        this.hasPre = hasPre;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public String getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(String lastIndex) {
        this.lastIndex = lastIndex;
    }

    public String getOrderBySetted() {
        return orderBySetted;
    }

    public void setOrderBySetted(String orderBySetted) {
        this.orderBySetted = orderBySetted;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPrePage() {
        return prePage;
    }

    public void setPrePage(String prePage) {
        this.prePage = prePage;
    }

    public List<LibResultModel> getResult() {
        return result;
    }

    public void setResult(List<LibResultModel> result) {
        this.result = result;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }
}
