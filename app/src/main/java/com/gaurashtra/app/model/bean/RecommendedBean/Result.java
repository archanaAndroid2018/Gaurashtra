package com.gaurashtra.app.model.bean.RecommendedBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.gaurashtra.app.model.bean.HomePagebean.TopSellingDatum;

public class Result {
    @SerializedName("recommendedData")
    @Expose
    private List<TopSellingDatum> recommendedData = null;

    public List<TopSellingDatum> getRecommendedData() {
        return recommendedData;
    }

    public void setRecommendedData(List<TopSellingDatum> recommendedData) {
        this.recommendedData = recommendedData;
    }

}
