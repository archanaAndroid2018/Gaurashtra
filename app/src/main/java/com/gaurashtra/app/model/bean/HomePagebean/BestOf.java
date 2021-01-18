package com.gaurashtra.app.model.bean.HomePagebean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BestOf {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("data")
    @Expose
    private List<TopSellingDatum> data = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TopSellingDatum> getData() {
        return data;
    }

    public void setData(List<TopSellingDatum> data) {
        this.data = data;
    }
}
