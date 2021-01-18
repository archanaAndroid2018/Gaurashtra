package com.gaurashtra.app.model.bean.ProductDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    @SerializedName("postcodCheckMessage")        // only use in product details
    @Expose
    private String availabilityMsg;
    @SerializedName("offerText")
    @Expose
    private OfferText offerText;
    @SerializedName("product")
    @Expose
    private Product product;
    @SerializedName("relatedProductData")
    @Expose
    private List<Data> relatedProductData = null;
    @SerializedName("staticContentData")
    @Expose
    private List<StaticContentDatum> staticContentData = null;
    @SerializedName("recentlyPurchasedData")
    @Expose
    private List<Data> recentlyPurchasedData = null;

    public String getAvailabilityMsg() {
        return availabilityMsg;
    }

    public void setAvailabilityMsg(String availabilityMsg) {
        this.availabilityMsg = availabilityMsg;
    }

    public OfferText getOfferText() {
        return offerText;
    }

    public void setOfferText(OfferText offerText) {
        this.offerText = offerText;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }



    public List<StaticContentDatum> getStaticContentData() {
        return staticContentData;
    }

    public void setStaticContentData(List<StaticContentDatum> staticContentData) {
        this.staticContentData = staticContentData;
    }

    public List<Data> getRelatedProductData() {
        return relatedProductData;
    }

    public void setRelatedProductData(List<Data> relatedProductData) {
        this.relatedProductData = relatedProductData;
    }

    public List<Data> getRecentlyPurchasedData() {
        return recentlyPurchasedData;
    }

    public void setRecentlyPurchasedData(List<Data> recentlyPurchasedData) {
        this.recentlyPurchasedData = recentlyPurchasedData;
    }
}
