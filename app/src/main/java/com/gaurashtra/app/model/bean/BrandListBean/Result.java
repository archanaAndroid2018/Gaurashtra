package com.gaurashtra.app.model.bean.BrandListBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.gaurashtra.app.model.bean.HomePagebean.ShopByBrandDatum;

public class Result {
    @SerializedName("shopByBrandData")
    @Expose
    private List<ShopByBrandDatum> shopByBrandData = null;

    public List<ShopByBrandDatum> getShopByBrandData() {
        return shopByBrandData;
    }

    public void setShopByBrandData(List<ShopByBrandDatum> shopByBrandData) {
        this.shopByBrandData = shopByBrandData;
    }
}
