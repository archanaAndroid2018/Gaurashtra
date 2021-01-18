package com.gaurashtra.app.model.bean.BestOfBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("bestOf")
    @Expose
    private BestOf bestOf;
    @SerializedName("totalPage")
    @Expose
    private Integer totalPage;
    @SerializedName("currentPage")
    @Expose
    private Integer currentPage;

    public BestOf getBestOf() {
        return bestOf;
    }

    public void setBestOf(BestOf bestOf) {
        this.bestOf = bestOf;
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
