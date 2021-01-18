package com.gaurashtra.app.model.bean.categoryDetailListbean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("cateProductList")
    @Expose
    private CateProductList cateProductList;

    @SerializedName("totalPage")
    @Expose
    private Integer totalPage;

    @SerializedName("currentPage")
    @Expose
    private Integer currentPage;

    public CateProductList getCateProductList() {
        return cateProductList;
    }

    public void setCateProductList(CateProductList cateProductList) {
        this.cateProductList = cateProductList;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
