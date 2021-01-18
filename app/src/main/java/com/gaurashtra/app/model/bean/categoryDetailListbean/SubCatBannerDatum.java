package com.gaurashtra.app.model.bean.categoryDetailListbean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCatBannerDatum {

    @SerializedName("bannerId")
    @Expose
    private String bannerId;
    @SerializedName("bannerImage")
    @Expose
    private String bannerImage;
    @SerializedName("bannerAlt")
    @Expose
    private String bannerAlt;
    @SerializedName("linkId")
    @Expose
    private String linkId;
    @SerializedName("bannerPosition")
    @Expose
    private String bannerPosition;

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getBannerAlt() {
        return bannerAlt;
    }

    public void setBannerAlt(String bannerAlt) {
        this.bannerAlt = bannerAlt;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getBannerPosition() {
        return bannerPosition;
    }

    public void setBannerPosition(String bannerPosition) {
        this.bannerPosition = bannerPosition;
    }
}
