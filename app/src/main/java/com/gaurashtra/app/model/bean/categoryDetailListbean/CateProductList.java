package com.gaurashtra.app.model.bean.categoryDetailListbean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CateProductList {
    @SerializedName("mainCat")
    @Expose
    private MainCat mainCat;
    @SerializedName("subCatData")
    @Expose
    private List<SubCatDatum> subCatData = null;
    @SerializedName("subCatBannerData")
    @Expose
    private List<SubCatBannerDatum> subCatBannerData = null;
    @SerializedName("productData")
    @Expose
    private List<ProductDatum> productData = null;
    @SerializedName("totalPage")
    @Expose
    private int totalPage;
    @SerializedName("currentPage")
    @Expose
    private int currentPage;
    public MainCat getMainCat() {
        return mainCat;
    }

    public void setMainCat(MainCat mainCat) {
        this.mainCat = mainCat;
    }

    public List<SubCatDatum> getSubCatData() {
        return subCatData;
    }

    public void setSubCatData(List<SubCatDatum> subCatData) {
        this.subCatData = subCatData;
    }

    public List<SubCatBannerDatum> getSubCatBannerData() {
        return subCatBannerData;
    }

    public void setSubCatBannerData(List<SubCatBannerDatum> subCatBannerData) {
        this.subCatBannerData = subCatBannerData;
    }

    public List<ProductDatum> getProductData() {
        return productData;
    }

    public void setProductData(List<ProductDatum> productData) {
        this.productData = productData;
    }

}
