package com.gaurashtra.app.model.bean.categoryListbean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    @SerializedName("categoryData")
    @Expose
    private List<CategoryDatum> categoryData = null;

    public List<CategoryDatum> getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(List<CategoryDatum> categoryData) {
        this.categoryData = categoryData;
    }
}
