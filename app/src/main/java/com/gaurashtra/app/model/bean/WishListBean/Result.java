package com.gaurashtra.app.model.bean.WishListBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    @SerializedName("wishlistData")
    @Expose
    private List<WishlistDatum> wishlistData = null;

    public List<WishlistDatum> getWishlistData() {
        return wishlistData;
    }

    public void setWishlistData(List<WishlistDatum> wishlistData) {
        this.wishlistData = wishlistData;
    }
}
