package com.gaurashtra.app.model.bean.TopSellingBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.gaurashtra.app.model.bean.HomePagebean.TodayDeal;
import com.gaurashtra.app.model.bean.HomePagebean.TopSellingDatum;

public class Result {
    @SerializedName("topSellingData")
    @Expose
    private List<TopSellingDatum> topSellingData = null;

    public List<TopSellingDatum> getTopSellingData() {
        return topSellingData;
    }

    public void setTopSellingData(List<TopSellingDatum> topSellingData) {
        this.topSellingData = topSellingData;
    }
    @SerializedName("recommendedData")
    @Expose
    private List<TopSellingDatum> recommendedData = null;

    public List<TopSellingDatum> getRecommendedData() {
        return recommendedData;
    }

    public void setRecommendedData(List<TopSellingDatum> recommendedData) {
        this.recommendedData = recommendedData;
    }
    @SerializedName("recentlyViewData")
    @Expose
    private List<TopSellingDatum> recentlyViewData = null;

    public List<TopSellingDatum> getRecentlyViewData() {
        return recentlyViewData;
    }

    public void setRecentlyViewData(List<TopSellingDatum> recentlyViewData) {
        this.recentlyViewData = recentlyViewData;
    }

    @SerializedName("todayDeal")
    @Expose
      private TodayDeal todayDeal;

    public TodayDeal getTodayDeal() {
        return todayDeal;
    }

    public void setTodayDeal(TodayDeal todayDeal) {
        this.todayDeal = todayDeal;
    }

    @SerializedName("totalPage")
    @Expose
    private Integer totalPage;
    @SerializedName("currentPage")
    @Expose
    private Integer currentPage;

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

