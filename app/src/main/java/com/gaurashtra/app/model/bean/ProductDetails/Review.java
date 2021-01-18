package com.gaurashtra.app.model.bean.ProductDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Review {
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("average")
    @Expose
    private String average;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }
}
