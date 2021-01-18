package com.gaurashtra.app.model.bean.HomePagebean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SliderDatum implements Serializable {
    @SerializedName("sliderId")
    @Expose
    private String sliderId;
    @SerializedName("sliderImage")
    @Expose
    private String sliderImage;
    @SerializedName("sliderAlt")
    @Expose
    private String sliderAlt;
    @SerializedName("linkType")
    @Expose
    private String linkType;
    @SerializedName("linkId")
    @Expose
    private String linkId;

    public String getSliderId() {
        return sliderId;
    }

    public void setSliderId(String sliderId) {
        this.sliderId = sliderId;
    }

    public String getSliderImage() {
        return sliderImage;
    }


    public void setSliderImage(String sliderImage) {
        this.sliderImage = sliderImage;
    }

    public String getSliderAlt() {
        return sliderAlt;
    }

    public void setSliderAlt(String sliderAlt) {
        this.sliderAlt = sliderAlt;
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
