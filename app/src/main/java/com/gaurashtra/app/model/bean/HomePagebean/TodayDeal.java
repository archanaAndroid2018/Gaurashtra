package com.gaurashtra.app.model.bean.HomePagebean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TodayDeal {

    @SerializedName("dealDate")
    @Expose
    private String dealDate;
    @SerializedName("dealStartTime")
    @Expose
    private String dealStartTime;
    @SerializedName("dealEndTime")
    @Expose
    private String dealEndTime;
    @SerializedName("background")
    @Expose
    private Background_ background;

    @SerializedName("available")
    @Expose
    private String available;

    @SerializedName("data")
    @Expose
    private List<TodayProduct> data = null;

    public String getDealDate() {
        return dealDate;
    }

    public void setDealDate(String dealDate) {
        this.dealDate = dealDate;
    }

    public String getDealStartTime() {
        return dealStartTime;
    }

    public void setDealStartTime(String dealStartTime) {
        this.dealStartTime = dealStartTime;
    }

    public String getDealEndTime() {
        return dealEndTime;
    }

    public void setDealEndTime(String dealEndTime) {
        this.dealEndTime = dealEndTime;
    }

    public Background_ getBackground() {
        return background;
    }

    public void setBackground(Background_ background) {
        this.background = background;
    }

    public List<TodayProduct> getData() {
        return data;
    }

    public void setData(List<TodayProduct> data) {
        this.data = data;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }
}
