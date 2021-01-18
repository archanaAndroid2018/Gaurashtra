package com.gaurashtra.app.model.bean.HomePagebean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AfterShopByDatum {
    @SerializedName("bannerId")
    @Expose
    private String bannerId;
    @SerializedName("bannerImage")
    @Expose
    private String bannerImage;
    @SerializedName("bannerAlt")
    @Expose
    private String bannerAlt;
    @SerializedName("linkType")
    @Expose
    private String linkType;
    @SerializedName("linkId")
    @Expose
    private String linkId;

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

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }
}
