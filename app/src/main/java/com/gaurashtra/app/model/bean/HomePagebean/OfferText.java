package com.gaurashtra.app.model.bean.HomePagebean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OfferText implements Serializable {
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("linkType")
    @Expose
    private String linkType;
    @SerializedName("linkId")
    @Expose
    private String linkId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
